package com.back.ad;


import com.back.tag.TagService;
import com.back.user.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;
    private final TagService tagService;

    public AdDto createAd(User seller, String title, String description, List<String> images, List<String> tagNames, boolean showEmail, boolean showPhone) {
        if (images != null && images.size() > 10) {
            throw new IllegalArgumentException("Maximum 10 images allowed");
        }
        Ad ad = new Ad();
        ad.setSeller(seller);
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setImages(images);
        ad.setShowEmail(showEmail);
        ad.setShowPhone(showPhone);
        ad.setPreviewToken(UUID.randomUUID().toString());
        ad.setTags(tagService.processTags(tagNames));
        return adRepository.save(ad).toDto();
    }

    public AdDto updateAd(Long adId, User seller, String title, String description, List<String> images, List<String> tagNames, boolean showEmail, boolean showPhone) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));
        if (!ad.getSeller().getId().equals(seller.getId())) {
            throw new IllegalAccessError("Not authorized to update this ad");
        }
        if (images != null && images.size() > 10) {
            throw new IllegalArgumentException("Maximum 10 images allowed");
        }
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setImages(images);
        ad.setShowEmail(showEmail);
        ad.setShowPhone(showPhone);
        tagService.removeTags(ad.getTags());
        ad.setTags(tagService.processTags(tagNames));
        return adRepository.save(ad).toDto();
    }

    public void deleteAd(Long adId, User seller) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));
        if (!ad.getSeller().getId().equals(seller.getId())) {
            throw new IllegalAccessError("Not authorized to delete this ad");
        }
        tagService.removeTags(ad.getTags());
        adRepository.delete(ad);
    }

    public Ad updateStatus(Long adId, User seller, AdStatus status)
    throws AccessDeniedException {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));
        if (!ad.getSeller().getId().equals(seller.getId())) {
            throw new AccessDeniedException("Not authorized to update this ad");
        }
        ad.setStatus(status);
        return adRepository.save(ad);
    }

    public List<Ad> searchAds(String query, String tag) {
        if (tag != null && !tag.isEmpty()) {
            return adRepository.findByTagsName(tag);
        }
        return adRepository.searchByTitleOrDescription(query);
    }

    public Ad getAdByPreviewToken(String token) {
        return adRepository.findByPreviewToken(token);
    }

    public List<Ad> getAdsBySeller(Long sellerId) {
        return adRepository.findBySellerId(sellerId);
    }


}