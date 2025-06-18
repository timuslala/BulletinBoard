package com.back.ad;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.back.tag.Tag;
import com.back.user.User;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "ad_tags",
        joinColumns = @JoinColumn(name = "ad_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AdStatus status = AdStatus.DRAFT;

    @Column(unique = true)
    private String previewToken = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    private boolean showEmail;
    private boolean showPhone;
    public Ad(User seller, String title, String description, List<String> images, List<Tag> tags, boolean showEmail, boolean showPhone) {
        this.seller = seller;
        this.title = title;
        this.description = description;
        this.images = images != null ? images : new ArrayList<>();
        this.tags = tags != null ? tags : new ArrayList<>();
        this.showEmail = showEmail;
        this.showPhone = showPhone;
    }
    public AdDto toDto() {
        AdDto dto = new AdDto();
        dto.setTitle(this.title);
        dto.setDescription(this.description);
        dto.setImages(new ArrayList<>());
        for (String image : this.images) {
            dto.getImages().add(image);
        }
        dto.setTags(new ArrayList<>());
        for (Tag tag : this.tags) {
            dto.getTags().add(tag.getName());
        }
        dto.setShowEmail(this.showEmail);
        dto.setShowPhone(this.showPhone);
        return dto;
    }
}