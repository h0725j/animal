package com.test.animal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Shelter")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shelterId; // 보호소 id

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



}
