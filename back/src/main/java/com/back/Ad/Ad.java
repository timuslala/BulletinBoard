package com.back.Ad;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.back.Tag.Tag;
import com.back.User.User;

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
    private List<String> photoUrls = new ArrayList<>();

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
}