package com.back.ad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByStatus(AdStatus status);
    Ad findByPreviewToken(String previewToken);
    @Query("SELECT a FROM Ad a WHERE a.status = 'PUBLISHED' AND (a.title LIKE %:query% OR a.description LIKE %:query%)")
    List<Ad> searchByTitleOrDescription(String query);
    List<Ad> findBySellerId(Long sellerId);
    List<Ad> findByTagsName(String tagName);
}