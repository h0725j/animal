package com.test.animal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adoptionId; // 입양id

    private String desertionNo; // 구조번호

    private String happenDt; // 접수일

    private String happenPlace; // 발견장소

    private String upKindNm; // 축종명

    private String upKindCd; // 축종코드

    private String kindNm; // 품종명

    private String kindCd; // 품종코드

    private String colorCd; // 색상

    private String age; // 나이

    private String weight; // 체중

    private String noticeNo; // 공고번호

    private String noticeSdt; // 공고시작일

    @Column(columnDefinition = "TEXT")
    private String popfile1; // 이미지1(텍스트)

    @Column(columnDefinition = "TEXT")
    private String popfile2; // 이미지2(텍스트)

    private String processState; // 상태

    private String sexCd; // 성별(타입)

    private String neuterYn; // 중성화여부(타입)

    private String specialMark; // 특징

    private String updTm; // 수정일

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shelter_id")
    private Care care; // 보호소id(외래키)
}
