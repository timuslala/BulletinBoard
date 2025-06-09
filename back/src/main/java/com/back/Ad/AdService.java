package com.back.Ad;


import com.back.User.User;

import com.back.Tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;
    private final TagService tagService;

    public Ad createAd(User seller, String title, String description, List<MultipartFile> images, List<String> tagNames, boolean showEmail, boolean showPhone) {
        if (images != null && images.size() > 10) {
            throw new IllegalArgumentException("Maximum 10 images allowed");
        }
        Ad ad = new Ad();
        ad.setSeller(seller);
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setImages(convertToImages(images));
        ad.setShowEmail(showEmail);
        ad.setShowPhone(showPhone);
        ad.setPreviewToken(UUID.randomUUID().toString());
        ad.setTags(tagService.processTags(tagNames));
        return adRepository.save(ad);
    }

    public Ad updateAd(Long adId, User seller, String title, String description, List<MultipartFile> images, List<String> tagNames, boolean showEmail, boolean showPhone) {
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
        ad.setImages(convertToImages(images));
        ad.setShowEmail(showEmail);
        ad.setShowPhone(showPhone);
        tagService.removeTags(ad.getTags());
        ad.setTags(tagService.processTags(tagNames));
        return adRepository.save(ad);
    }

    private List<AdImage> convertToImages(List<MultipartFile> images) {
        List<AdImage> photos = new ArrayList<>();
        if (images != null) {
            for (MultipartFile file : images) {
                try {
                    AdImage photo = new AdImage();
                    photo.setData(file.getBytes());
                    photo.setContentType(file.getContentType());
                    photos.add(photo);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to process image", e);
                }
            }
        }
        return photos;
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