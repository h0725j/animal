package com.test.animal.entity;

import com.test.animal.entity.enums.ActiveState;
import com.test.animal.entity.enums.NeuterYn;
import com.test.animal.entity.enums.ProcessState;
import com.test.animal.entity.enums.SexCd;
import com.test.animal.entity.enums.UpKindCd;
import com.test.animal.entity.enums.UpKindNm;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adoptionId; // 입양id

    private String desertionNo; // 구조번호

    private LocalDate happenDt; // 접수일

    private String happenPlace; // 발견장소

    @Enumerated(EnumType.STRING)
    private UpKindNm upKindNm; // 축종명

    @Enumerated(EnumType.STRING)
    private UpKindCd upKindCd; // 축종코드

    private String kindNm; // 품종명

    private String kindCd; // 품종코드

    private String colorCd; // 색상

    private Integer age; // 나이 - Long

    private String weight; // 체중

    private String noticeNo; // 공고번호

    private LocalDate noticeSdt; // 공고시작일

    private LocalDate noticeEdt; // 공고종료일

    @Column(columnDefinition = "TEXT")
    private String popfile1; // 이미지1(텍스트)

    @Column(columnDefinition = "TEXT")
    private String popfile2; // 이미지2(텍스트)

    @Enumerated(EnumType.STRING)
    private ProcessState processState; // 상태

    @Enumerated(EnumType.STRING)
    private ActiveState activeState;

    @Enumerated(EnumType.STRING)
    private SexCd sexCd; // 성별

    @Enumerated(EnumType.STRING)
    private NeuterYn neuterYn; // 중성화여부(타입)

    private String specialMark; // 특징

    private LocalDateTime updTm; // 수정일

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter; // 보호소id(외래키)


}
