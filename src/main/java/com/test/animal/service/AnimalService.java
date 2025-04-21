package com.test.animal.service;

import com.test.animal.dto.AnimalApiResponse;
import com.test.animal.dto.AnimalResponseDto;
import com.test.animal.entity.Animal;
import com.test.animal.entity.Shelter;
import com.test.animal.repository.AnimalRepository;
import com.test.animal.repository.ShelterRepository;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public class AnimalService {
    private final RestTemplate restTemplate;
    private final AnimalRepository animalRepository;
    private final ShelterRepository shelterRepository;
    private final AnimalEsService animalEsService;

    public List<AnimalResponseDto> saveAnimals() {
        List<AnimalResponseDto> allAnimalResponseDtos = new ArrayList<>();
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
                ResponseEntity<AnimalApiResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, AnimalApiResponse.class);
                AnimalApiResponse animalApiResponse = responseEntity.getBody();
                log.info("응답: {}", animalApiResponse);

                if(animalApiResponse != null && animalApiResponse.getResponse() != null &&
                        animalApiResponse.getResponse().getBody() != null &&
                        animalApiResponse.getResponse().getBody().getItems() != null &&
                        animalApiResponse.getResponse().getBody().getItems().getItem() != null &&
                        !animalApiResponse.getResponse().getBody().getItems().getItem().isEmpty()) {

                    List<AnimalApiResponse.Item> items = animalApiResponse.getResponse().getBody().getItems().getItem();
                    List<AnimalResponseDto> animalResponseDtos = new ArrayList<>();

                    for(AnimalApiResponse.Item item : items) {
                        // 날짜 변환
                        LocalDate happenDate = null;
                        LocalDate noticeSdt = null;
                        LocalDate noticeEdt = null;
                        LocalDateTime updTm = null;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");

                        try {
                            if (item.getHappenDt() != null && !item.getHappenDt().isEmpty()) {
                                happenDate = LocalDate.parse(item.getHappenDt(), formatter);
                            }
                            if (item.getNoticeSdt() != null && !item.getNoticeSdt().isEmpty()) {
                                noticeSdt = LocalDate.parse(item.getNoticeSdt(), formatter);
                            }
                            if (item.getNoticeEdt() != null && !item.getNoticeEdt().isEmpty()) {
                                noticeEdt = LocalDate.parse(item.getNoticeEdt(), formatter);
                            }
                            if (item.getUpdTm() != null && !item.getUpdTm().isEmpty()) {
                                updTm = LocalDateTime.parse(item.getUpdTm(), dateTimeFormatter)
                                        .truncatedTo(ChronoUnit.SECONDS);
                            }
                        } catch (DateTimeParseException e) {
                            log.error("날짜 파싱 오류: {}", e.getMessage());
                        }

                        // 품종 정보 처리 - API 응답에 upKindNm과 kindNm이 있으므로 직접 사용

                        AnimalResponseDto animalResponseDto = AnimalResponseDto.builder()
                                .desertionNo(item.getDesertionNo())
                                .happenDt(happenDate)
                                .happenPlace(item.getHappenPlace())
                                .upKindCd(item.getUpKindCd())
                                .upKindNm(item.getUpKindNm())
                                .kindCd(item.getKindCd())
                                .kindNm(item.getKindNm())
                                .colorCd(item.getColorCd())
                                .age(parseInt(item.getAge(), null))
                                .weight(item.getWeight())
                                .noticeNo(item.getNoticeNo())
                                .noticeSdt(noticeSdt)
                                .noticeEdt(noticeEdt)
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
                                .updTm(updTm)
                                .careNm(item.getCareNm())
                                .build();

                        animalResponseDtos.add(animalResponseDto);
                    }

                    // 현재 페이지의 데이터를 전체 결과 목록에 추가
                    allAnimalResponseDtos.addAll(animalResponseDtos);

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

        if (!allAnimalResponseDtos.isEmpty()) {
            List<Animal> animals = new ArrayList<>();

            for (AnimalResponseDto animalResponseDto : allAnimalResponseDtos) {
                // 동물 객체 생성
                Animal animal = animalResponseDto.toEntityanimal();

                Shelter shelter = findShelter(animalResponseDto);

                animal.setShelter(shelter);
                if (shelter != null) {
                } else {
                    log.warn("동물 {} 에 보호소 정보가 없어 null로 설정됨", animal.getDesertionNo());
                }

                animals.add(animal);
            }

            if (!animals.isEmpty()) {
                animalRepository.saveAll(animals);
                log.info("총 {} 건의 데이터를 데이터베이스에 저장했습니다.", animals.size());

                animalEsService.saveBulk(animals);
            } else {
                log.warn("저장할 동물 데이터가 없습니다.");
            }
        }

        return allAnimalResponseDtos;
    }

    private Shelter findShelter(AnimalResponseDto animalResponseDto) {
        Shelter shelter = null;
        String careNm = animalResponseDto.getCareNm();
        String careRegNo = animalResponseDto.getCareRegNo();

        if (careRegNo != null && !careRegNo.isEmpty()) {
            List<Shelter> shelters = shelterRepository.findByCareRegNo(careRegNo);
            if (!shelters.isEmpty()) {
                return shelters.get(0);
            }
        }

        if (careNm != null && !careNm.isEmpty()) {
            List<Shelter> shelters = shelterRepository.findByCareNm(careNm);
            if (!shelters.isEmpty()) {
                return shelters.get(0);
            }
        }

        if ((careRegNo != null && !careRegNo.isEmpty()) || (careNm != null && !careNm.isEmpty())) {
            Shelter newShelter = Shelter.builder()
                    .careRegNo(animalResponseDto.getCareRegNo())
                    .careNm(animalResponseDto.getCareNm())
                    .careTel(animalResponseDto.getCareTel())
                    .careAddr(animalResponseDto.getCareAddr())
                    .orgNm(animalResponseDto.getOrgNm())
                    .build();

            shelter = shelterRepository.save(newShelter);
            log.info("새 보호소 생성 및 저장: {}, 보호소 번호: {}", shelter.getCareNm(), shelter.getCareRegNo());
            return shelter;
        }

        return null;

    }

    private Integer parseInt(String value, Integer defaultValue) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            String age = value.substring(0, 4);
            if (age.isEmpty()) {
                return defaultValue;
            }
            return Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}


