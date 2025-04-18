package com.test.animal.service;

import com.test.animal.dto.ApiResponse;
import com.test.animal.dto.ResponseDto;
import com.test.animal.entity.Animal;
import com.test.animal.entity.Care;
import com.test.animal.repository.AnimalRepository;
import com.test.animal.repository.CareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalService {
    private final RestTemplate restTemplate;
    private final AnimalRepository animalRepository;
    private final CareRepository careRepository;

    public List<ResponseDto> saveAnimals() {
        List<ResponseDto> allResponseDtos = new ArrayList<>();
        int pageNo = 1;
        int numOfRows = 1000;
        boolean hasMoreData = true;

        while (hasMoreData) {
            log.info("데이터 가져오기: 페이지 {}, 페이지당 {} 건", pageNo, numOfRows);

            URI uri = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/1543061/abandonmentPublicService_v2/abandonmentPublic_v2")
                    .queryParam("serviceKey", "0O1KlLSEEGjpWzJOBa8Q9Mxfc3g%2FA%2BPSVTzNt3XSLdZdVuyaUMWYmsgAPe%2FwolWOrEVyUAYuk9rzp4VNwHNBOg%3D%3D")
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("pageNo", pageNo)
                    .queryParam("_type", "json")
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "AppTest")
                    .build(true)
                    .toUri();

            log.info("요청 주소: {}", uri.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, ApiResponse.class);
                ApiResponse apiResponse = responseEntity.getBody();
                log.info("응답: {}", apiResponse);

                if(apiResponse != null && apiResponse.getResponse() != null &&
                        apiResponse.getResponse().getBody() != null &&
                        apiResponse.getResponse().getBody().getItems() != null &&
                        apiResponse.getResponse().getBody().getItems().getItem() != null &&
                        !apiResponse.getResponse().getBody().getItems().getItem().isEmpty()) {

                    List<ApiResponse.Item> items = apiResponse.getResponse().getBody().getItems().getItem();
                    List<ResponseDto> responseDtos = new ArrayList<>();

                    for(ApiResponse.Item item : items) {
                        // 날짜 변환
                        Date happenDate = null;
                        Date noticeDate = null;
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                            if (item.getHappenDt() != null && !item.getHappenDt().isEmpty()) {
                                happenDate = formatter.parse(item.getHappenDt());
                            }
                            if (item.getNoticeSdt() != null && !item.getNoticeSdt().isEmpty()) {
                                noticeDate = formatter.parse(item.getNoticeSdt());
                            }
                        } catch (ParseException e) {
                            log.error("날짜 파싱 오류: {}", e.getMessage());
                        }

                        // 품종 정보 처리 - API 응답에 upKindNm과 kindNm이 있으므로 직접 사용
                        String upKind = item.getUpKindNm();
                        String kind = item.getKindNm();

                        ResponseDto responseDto = ResponseDto.builder()
                                .desertionNo(item.getDesertionNo())
                                .happenDt(item.getHappenDt())
                                .happenPlace(item.getHappenPlace())
                                .upKindNm(item.getUpKindNm())
                                .upKindCd(item.getUpKindCd())
                                .kindCd(item.getKindCd())
                                .kindNm(item.getKindNm())
                                .colorCd(item.getColorCd())
                                .age(item.getAge())
                                .weight(item.getWeight())
                                .noticeNo(item.getNoticeNo())
                                .noticeSdt(item.getNoticeSdt())
                                .noticeEdt(item.getNoticeEdt())
                                .popfile1(item.getPopfile1())
                                .popfile2(item.getPopfile2())
                                .processState(item.getProcessState())
                                .sexCd(item.getSexCd())
                                .neuterYn(item.getNeuterYn())
                                .specialMark(item.getSpecialMark())
                                .careNm(item.getCareNm())
                                .careRegNo(item.getCareRegNo())
                                .careTel(item.getCareTel())
                                .careAddr(item.getCareAddr())
                                .careOwnerNm(item.getCareOwnerNm())
                                .orgNm(item.getOrgNm())
                                .updTm(item.getUpdTm())
                                .build();

                        responseDtos.add(responseDto);
                    }

                    // 현재 페이지의 데이터를 전체 결과 목록에 추가
                    allResponseDtos.addAll(responseDtos);

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

//        // 모든 데이터를 수집한 후 데이터베이스에 저장
//        if (!allResponseDtos.isEmpty()) {
//            List<Animal> animals = new ArrayList<>();
//            List<Care> cares = new ArrayList<>();
//
//            for (ResponseDto responseDto : allResponseDtos) {
//                // Care 엔티티를 먼저 저장
//                Care care = responseDto.toEntitycare();
//                care = careRepository.save(care);
//
//                // Animal 엔티티에 저장된 Care 엔티티 설정
//                Animal animal = responseDto.toEntityanimal();
//                animal.setCare(care);
//                animals.add(animal);
//            }
//
//            animalRepository.saveAll(animals);
//
//            log.info("총 {} 건의 데이터를 데이터베이스에 저장했습니다.", allResponseDtos.size());
//        }
        if (!allResponseDtos.isEmpty()) {
            Map<String, Care> careMap = new HashMap<>();
            List<Animal> animals = new ArrayList<>();

            for (ResponseDto responseDto : allResponseDtos) {
                // Care 고유 키 구성
                String careKey = responseDto.getCareNm() + "|" + responseDto.getCareTel();

                // 중복되지 않는 Care만 Map에 저장
                Care care = careMap.computeIfAbsent(careKey, k -> responseDto.toEntitycare());

                // Animal 생성 및 Care 주입
                Animal animal = responseDto.toEntityanimal();
                animal.setCare(care);
                animals.add(animal);
            }

            // 먼저 중복 제거된 Care 저장
            List<Care> savedCares = careRepository.saveAll(new ArrayList<>(careMap.values()));

            // Animal 저장
            animalRepository.saveAll(animals);

            log.info("총 {} 건의 데이터를 데이터베이스에 저장했습니다.", allResponseDtos.size());
        }


        return allResponseDtos;
    }

}


