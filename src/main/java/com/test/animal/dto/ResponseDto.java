package com.test.animal.dto;

import com.test.animal.entity.Animal;
import com.test.animal.entity.Care;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private Long adoptionId; // 입양id
    private String desertionNo; // 구조번호
    private LocalDate happenDt; // 접수일
    private String happenPlace; // 발견장소
    private String upKindCd; // 축종코드
    private String upKindNm; // 축종명
    private String kindCd; // 품종코드
    private String kindNm; // 품종명
    private String colorCd; // 색상
    private String age; // 나이
    private String weight; // 체중
    private String noticeNo; // 공고번호
    private LocalDate noticeSdt; // 공고시작일
    private LocalDate noticeEdt; // 공고종료일
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

    public Animal toEntityanimal() {
        return Animal.builder()
                .desertionNo(this.desertionNo)
                .happenDt(this.happenDt)
                .happenPlace(this.happenPlace)
                .upKindNm(this.upKindNm)
                .upKindCd(this.upKindCd)
                .kindNm(this.kindNm)
                .kindCd(this.kindCd)
                .colorCd(this.colorCd)
                .age(this.age)
                .weight(this.weight)
                .noticeNo(this.noticeNo)
                .noticeSdt(this.noticeSdt)
                .noticeEdt(this.noticeEdt)
                .popfile1(this.popfile1)
                .popfile2(this.popfile2)
                .processState(this.processState)
                .sexCd(this.sexCd)
                .neuterYn(this.neuterYn)
                .specialMark(this.specialMark)
                .updTm(this.updTm)
                .care(this.toEntitycare())
                .build();
    }

    public Care toEntitycare() {
        return Care.builder()
                .careRegNo(this.careRegNo)
                .careNm(this.careNm)
                .careTel(this.careTel)
                .careAddr(this.careAddr)
                .careOwnerNm(this.careOwnerNm)
                .orgNm(this.orgNm)
                .build();
    }

}
