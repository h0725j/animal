package com.test.animal.es.Document;

import com.test.animal.entity.Animal;
import com.test.animal.entity.enums.NeuterYn;
import com.test.animal.entity.enums.SexCd;
import com.test.animal.entity.enums.UpKindCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Document(indexName = "animals")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDocument {
    @Id
    @Field(type = FieldType.Keyword, name = "adoptionId")
    private Long adoptionId; // 입양id

    @Field(type = FieldType.Keyword, name = "upKindCd")
    private UpKindCd upKindCd; // 축종코드

    @Field(type = FieldType.Text, name = "KindNm", analyzer = "korean")
    private String kindNm; // 품종명

    @Field(type = FieldType.Text, name = "colorCd", analyzer = "korean")
    private String colorCd; // 색상

    @Field(type = FieldType.Integer, name = "age")
    private Integer age; // 나이(년생) Long
    // 사용할때 몇살인지로 전처리

    @Field(type = FieldType.Keyword, name = "sexCd")
    private SexCd sexCd; // 성별

    @Field(type = FieldType.Keyword, name = "neuterYn")
    private NeuterYn neuterYn; // 중성화여부(타입)

    @Field(type = FieldType.Text, name = "specialMark", analyzer = "korean")
    private String specialMark; // 특징

    @Setter
    @Field(type = FieldType.Text, analyzer = "korean")
    private String searchField; // AI가 정제한 동물 정보

    @Setter
    @Field(type = FieldType.Dense_Vector, dims = 1536)
    private float[] embedding; // searchField 임베딩



    public static AnimalDocument fromEntity(Animal animal) {
        return AnimalDocument.builder()
                .adoptionId(animal.getAdoptionId())
                .upKindCd(animal.getUpKindCd())
                .kindNm(animal.getKindNm())
                .colorCd(animal.getColorCd())
                .age(animal.getAge())
                .sexCd(animal.getSexCd())
                .neuterYn(animal.getNeuterYn())
                .specialMark(animal.getSpecialMark())
                .build();
    }
}
