package com.test.animal.service;

import com.test.animal.dto.ShelterApiResponse;
import com.test.animal.dto.ShelterResponseDto;
import com.test.animal.entity.Shelter;
import com.test.animal.repository.ShelterRepository;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final RestTemplate restTemplate;

    public List<ShelterResponseDto> saveShelter() {
        List<ShelterResponseDto> allShelterResponseDtos = new ArrayList<>();
        int pageNo = 1;
        int numOfRows = 1000;
        boolean hasMoreData = true;

        while (hasMoreData) {
            log.info("데이터 가져오기: 페이지 {}, 페이지당 {} 건", pageNo, numOfRows);

            URI uri = UriComponentsBuilder.fromHttpUrl(
                            "https://apis.data.go.kr/1543061/animalShelterSrvc_v2/shelterInfo_v2")
                    .queryParam("serviceKey",
                            "mIh16wSgE8R9SjJMMwvxYwP%2BInJxEi0M5ZLimKlsKz6nIjuGNb6aEPbGyEU2bT4s1ty83mIWB4fW8h5N3u9LCA%3D%3D")
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("pageNo", pageNo)
                    .queryParam("_type", "json")
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "AppTest")
                    .build(true)
                    .toUri();

            log.info("요청 주소: {}", uri.toString());

            org.springframework.http.HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<ShelterApiResponse> responseEntity = restTemplate.exchange(uri,
                        HttpMethod.GET, entity, ShelterApiResponse.class);
                ShelterApiResponse shelterApiResponse = responseEntity.getBody();
                log.info("응답: {}", shelterApiResponse);

                if (shelterApiResponse != null && shelterApiResponse.getResponse() != null &&
                        shelterApiResponse.getResponse().getBody() != null &&
                        shelterApiResponse.getResponse().getBody().getItems() != null &&
                        shelterApiResponse.getResponse().getBody().getItems().getItem() != null &&
                        !shelterApiResponse.getResponse().getBody().getItems().getItem()
                                .isEmpty()) {

                    List<ShelterApiResponse.Item> items = shelterApiResponse.getResponse().getBody()
                            .getItems().getItem();
                    List<ShelterResponseDto> shelterResponseDtos = new ArrayList<>();

                    for (ShelterApiResponse.Item item : items) {
                        Date dsignationDate = null;
                        Date dateStdDt = null;
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            if (item.getDsignationDate() != null && !item.getDsignationDate()
                                    .isEmpty()) {
                                dsignationDate = formatter.parse(item.getDsignationDate());
                            }
                            if (item.getDataStdDt() != null && !item.getDataStdDt().isEmpty()) {
                                dateStdDt = formatter.parse(item.getDataStdDt());
                            }
                        } catch (ParseException e) {
                            log.error("날짜 파싱 오류: {}", e.getMessage());
                        }

                        ShelterResponseDto shelterResponseDto = ShelterResponseDto.builder()
                                .careNm(item.getCareNm())
                                .careRegNo(item.getCareRegNo())
                                .orgNm(item.getOrgNm())
                                .divisionNm(item.getDivisionNm())
                                .saveTrgtAnimal(item.getSaveTrgtAnimal())
                                .careAddr(item.getCareAddr())
                                .jibunAddr(item.getJibunAddr())
                                .lat(item.getLat())
                                .lng(item.getLng())
                                .dsignationDate(dsignationDate)
                                .weekOprStime(item.getWeekOprStime())
                                .weekOprEtime(item.getWeekOprEtime())
                                .closeDay(item.getCloseDay())
                                .vetPersonCnt(item.getVetPersonCnt())
                                .specsPersonCnt(item.getSpecsPersonCnt())
                                .careTel(item.getCareTel())
                                .dataStdDt(dateStdDt)
                                .build();

                        shelterResponseDtos.add(shelterResponseDto);
                    }

                    allShelterResponseDtos.addAll(shelterResponseDtos);

                    // 현재 페이지의 데이터 수가 numOfRows보다 적으면 더 이상 데이터가 없는 것으로 판단
                    if (items.size() < numOfRows) {
                        hasMoreData = false;
                        log.info("마지막 페이지 도달: {} 건의 데이터 검색됨", items.size());
                    } else {
                        // 다음 페이지로 이동
                        pageNo++;

                        // API 호출 간격을 두어 서버 부하 방지 (선택사항)
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } else {
                    // 데이터가 없는 경우
                    hasMoreData = false;
                    log.info("더 이상 데이터가 없습니다.");
                }
            } catch (Exception e) {
                log.error("API 호출 중 오류 발생: {}", e.getMessage(), e);
                hasMoreData = false;
            }
        }

        if (!allShelterResponseDtos.isEmpty()) {
            // 중복 제거를 위한 Map 사용 (careNm을 키로 사용)
            Map<String, Shelter> uniqueSheltersMap = new HashMap<>();
            int duplicateCount = 0;

            for(ShelterResponseDto shelterResponseDto : allShelterResponseDtos) {
                String careNm = shelterResponseDto.getCareNm();

                // 이미 해당 이름의 보호소가 맵에 있는지 확인
                if (!uniqueSheltersMap.containsKey(careNm)) {
                    Shelter shelter = shelterResponseDto.toEntityShelter();
                    uniqueSheltersMap.put(careNm, shelter);
                } else {
                    duplicateCount++;
                    log.info("중복된 보호소 발견: {}", careNm);
                }
            }

            // 중복 제거된 보호소 목록 생성
            List<Shelter> uniqueShelters = new ArrayList<>(uniqueSheltersMap.values());

            // 기존 데이터베이스에 있는 보호소와 중복 제거
            List<Shelter> sheltersToSave = new ArrayList<>();
            for (Shelter shelter : uniqueShelters) {
                String careNm = shelter.getCareNm();
                // 데이터베이스에서 해당 이름의 보호소가 있는지 확인
                List<Shelter> existingShelters = shelterRepository.findByCareNm(careNm);

                if (existingShelters.isEmpty()) {
                    // 데이터베이스에 없는 경우에만 저장 목록에 추가
                    sheltersToSave.add(shelter);
                } else {
                    log.info("데이터베이스에 이미 존재하는 보호소: {}", careNm);
                }
            }

            // 새로운 보호소만 저장
            if (!sheltersToSave.isEmpty()) {
                shelterRepository.saveAll(sheltersToSave);
                log.info("총 {} 건의 새로운 보호소 데이터를 데이터베이스에 저장했습니다.", sheltersToSave.size());
            } else {
                log.info("저장할 새로운 보호소 데이터가 없습니다.");
            }

            log.info("API에서 가져온 총 보호소 수: {}, 중복 제거 후: {}, 중복 수: {}",
                    allShelterResponseDtos.size(), uniqueShelters.size(), duplicateCount);
        }

        return allShelterResponseDtos;
    }
}

