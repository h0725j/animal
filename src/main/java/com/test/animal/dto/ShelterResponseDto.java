package com.test.animal.dto;

import com.test.animal.entity.Shelter;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShelterResponseDto {
    private Long shelterId;
    private String careNm; // 동물보호센터명
    private String careRegNo; // 보호소 번호
    private String orgNm; // 관리 기관명
    private String divisionNm; // 동물보호센터 유형
    private String saveTrgtAnimal; // 구조대상동물
    private String careAddr; // 소재지 도로명 주소
    private String jibunAddr; // 소재지번주소
    private String lat; // 위도
    private String lng; // 경도
    private Date dsignationDate; // 동물보호센터 지정일자
    private String weekOprStime; // 평일 운영시작 시간
    private String weekOprEtime; // 평일 운영종료 시간
    private String closeDay; // 휴무일
    private String vetPersonCnt; // 수의사 인원수
    private String specsPersonCnt; // 사양관리사 인원수
    private String careTel; // 전화번호
    private Date dataStdDt; // 데이터 기준일자

    public Shelter toEntityShelter() {
        Shelter shelter = Shelter.builder()
                .shelterId(this.shelterId)
                .careNm(this.careNm)
                .careRegNo(this.careRegNo)
                .orgNm(this.orgNm)
                .divisionNm(this.divisionNm)
                .saveTrgtAnimal(this.saveTrgtAnimal)
                .careAddr(this.careAddr)
                .jibunAddr(this.jibunAddr)
                .lat(this.lat)
                .lng(this.lng)
                .dsignationDate(this.dsignationDate)
                .weekOprStime(this.weekOprStime)
                .weekOprEtime(this.weekOprEtime)
                .closeDay(this.closeDay)
                .vetPersonCnt(this.vetPersonCnt)
                .specsPersonCnt(this.specsPersonCnt)
                .careTel(this.careTel)
                .dataStdDt(this.dataStdDt)
                .build();

        return shelter;
    }
}
