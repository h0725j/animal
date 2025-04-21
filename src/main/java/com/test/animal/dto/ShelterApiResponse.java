package com.test.animal.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShelterApiResponse {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    @Setter
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @Setter
    public static class Items {
        private List<Item> item;
    }

    @Getter
    @Setter
    public static class Item {
        private String careNm; // 동물보호센터명
        private String careRegNo; // 보호소 번호
        private String orgNm; // 관리 기관명
        private String divisionNm; // 동물보호센터 유형 - (ENUM)
        private String saveTrgtAnimal; // 구조대상동물
        private String careAddr; // 소재지 도로명 주소
        private String jibunAddr; // 소재지번주소
        private String lat; // 위도(double)
        private String lng; // 경도(double)
        private String dsignationDate; // 동물보호센터 지정일자
        private String weekOprStime; // 평일 운영시작 시간
        private String weekOprEtime; // 평일 운영종료 시간
        private String weekCellStime; // 평일 분양시작 시간
        private String weekCellEtime; // 평일 분양종료 시간
        private String weekendOprStime; // 주말 운영시작 시간
        private String weekendOprEtime; // 주말 운영종료 시간
        private String weekendCellStime; // 주말 분양시작 시간
        private String weekendCellEtime; // 주말 분양종료 시간
        private String closeDay; // 휴무일
        private String vetPersonCnt; // 수의사 인원수
        private String specsPersonCnt; // 사양관리사 인원수
        private String medicalCnt; // 진료실 수
        private String breedCnt; // 사육실 수
        private String quarabtineCnt; // 격리실 수
        private String feedCnt; // 사료보관실 수
        private String transCarCnt; // 구조운반용차량보유대 수
        private String careTel; // 전화번호
        private String dataStdDt; // 데이터 기준일자
    }
}
