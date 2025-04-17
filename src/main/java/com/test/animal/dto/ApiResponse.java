package com.test.animal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ApiResponse {
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
        private String desertionNo; // 구조번호
        private String happenDt; // 접수일
        private String happenPlace; // 발견장소
        private String upKindCd; // 축종코드
        private String upKindNm; // 축종명
        private String kindCd; // 품종코드
        private String kindNm; // 품종명
        private String colorCd; // 색상
        private String age; // 나이
        private String weight; // 체중
        private String noticeNo; // 공고번호
        private String noticeSdt; // 공고시작일
        private String popfile1; // 이미지1(텍스트)
        private String popfile2; // 이미지2(텍스트)
        private String processState; // 상태
        private String sexCd; // 성별(타입)
        private String neuterYn; // 중성화여부(타입)
        private String specialMark; // 특징
        private String careRegNo; // 보호소 번호
        private String careNm; // 보호소 이름
        private String careTel; // 보호소 전화번호
        private String careAddr; // 보호 장소
        private String careOwnerNm; // 관할기관
        private String orgNm; // 기관지역명
        private String updTm; // 수정일
    }
}

