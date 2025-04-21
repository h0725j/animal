package com.test.animal.dto;

import com.test.animal.entity.Animal;
import com.test.animal.entity.Shelter;
import com.test.animal.entity.type.ActiveState;
import com.test.animal.entity.type.NeuterYn;
import com.test.animal.entity.type.ProcessState;
import com.test.animal.entity.type.SexCd;
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
public class AnimalResponseDto {
    private Long adoptionId; // 입양id
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
    private String updTm; // 수정일

    // Care 엔티티 정보
    private String careRegNo; // 보호소 번호
    private String careNm; // 보호소 이름
    private String careTel; // 보호소 전화번호
    private String careAddr; // 보호 장소
    private String careOwnerNm; // 관할기관
    private String orgNm; // 기관지역명

    public Animal toEntityanimal() {
        Animal animal = Animal.builder()
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
                .popfile1(this.popfile1)
                .popfile2(this.popfile2)
                .processState(convertToProcessState(this.processState))
                .sexCd(convertToSexCd(this.sexCd))
                .neuterYn(convertToNeuterYn(this.neuterYn))
                .specialMark(this.specialMark)
                .updTm(this.updTm)
                .build();

        if (animal.getProcessState() == ProcessState.PROTECTED) {
            animal.setActiveState(ActiveState.ACTIVE);
        } else {
            animal.setActiveState(ActiveState.INACTIVE);
        }

        return animal;
    }

    private ProcessState convertToProcessState(String processState) {
        if (processState == null) {
            return null;
        }

        for(ProcessState state : ProcessState.values()) {
            if (state.getState().equals(processState) || state.name().equals(processState)) {
                return state;
            }
        }
        return null;
    }

    private SexCd convertToSexCd(String sexCd) {
        if (sexCd == null) {
            return null;
        }

        try {
            return SexCd.valueOf(sexCd);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private NeuterYn convertToNeuterYn(String neuterYn) {
        if (neuterYn == null) {
            return null;
        }

        try {
            return NeuterYn.valueOf(neuterYn);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Shelter toEntityShelter() {
        return Shelter.builder()
                .careRegNo(this.careRegNo)
                .careNm(this.careNm)
                .careTel(this.careTel)
                .careAddr(this.careAddr)
                .orgNm(this.orgNm)
                .build();
    }

}
