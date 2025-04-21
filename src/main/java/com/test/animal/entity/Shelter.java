package com.test.animal.entity;

import com.test.animal.entity.enums.DivisionNm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
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

    private DivisionNm divisionNm; // 동물보호센터 유형 - (ENUM)

    private String saveTrgtAnimal; // 구조대상동물

    private String careAddr; // 소재지 도로명 주소

    private String jibunAddr; // 소재지번주소

    private Double lat; // 위도(double)

    private Double lng; // 경도(double)

    private LocalDate dsignationDate; // 동물보호센터 지정일자

    private String weekOprStime; // 평일 운영시작 시간

    private String weekOprEtime; // 평일 운영종료 시간

    private String weekCellStime; // 평일 분양시작 시간

    private String weekCellEtime; // 평일 분양종료 시간

    private String weekendOprStime; // 주말 운영시작 시간

    private String weekendOprEtime; // 주말 운영종료 시간

    private String weekendCellStime; // 주말 분양시작 시간

    private String weekendCellEtime; // 주말 분양종료 시간

    private String closeDay; // 휴무일

    private Integer vetPersonCnt; // 수의사 인원수

    private Integer specsPersonCnt; // 사양관리사 인원수

    private Integer medicalCnt; // 진료실 수

    private Integer breedCnt; // 사육실 수

    private Integer quarabtineCnt; // 격리실 수

    private Integer feedCnt; // 사료보관실 수

    private Integer transCarCnt; // 구조운반용차량보유대 수

    private String careTel; // 전화번호

    private LocalDate dataStdDt; // 데이터 기준일자



}
