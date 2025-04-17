package com.test.animal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Care")
public class Care {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shelter_id;

    private String careRegNo; // 보호소 번호

    private String careNm; // 보호소 이름

    private String careTel; // 보호소 전화번호

    private String careAddr; // 보호 장소

    private String orgNm; // 기관지역명

    private String careOwnerNm; // 담당자

    private BigDecimal latitude; // 위도

    private BigDecimal longitude; // 경도



}
