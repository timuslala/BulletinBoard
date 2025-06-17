package com.back.tag;


import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private int usageCount;
    public TagDto toDto() {
        TagDto tagDto = new TagDto();
        tagDto.setName(this.name);
        tagDto.setUsageCount(this.usageCount);
        return tagDto;
    }
}