package com.back.Ad;



import com.back.Tag.Tag;
import com.back.User.User;
import com.back.Tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;
    private final TagRepository tagRepository;

    public Ad createAd(User seller, String title, String description, List<String> photoUrls, List<String> tagNames, boolean showEmail, boolean showPhone) {
        Ad ad = new Ad();
        ad.setSeller(seller);
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setPhotoUrls(photoUrls);
        ad.setShowEmail(showEmail);
        ad.setShowPhone(showPhone);
        ad.setPreviewToken(UUID.randomUUID().toString());
        ad.setTags(processTags(tagNames));
        return adRepository.save(ad);
    }

    public Ad updateAd(Long adId, User seller, String title, String description, List<String> photoUrls, List<String> tagNames, boolean showEmail, boolean showPhone) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));
        if (!ad.getSeller().getId().equals(seller.getId())) {
            throw new IllegalAccessError("Not authorized to update this ad");
        }
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setPhotoUrls(photoUrls);
        ad.setShowEmail(showEmail);
        ad.setShowPhone(showPhone);
        ad.setTags(processTags(tagNames));
        return adRepository.save(ad);
    }

    public void deleteAd(Long adId, User seller) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));
        if (!ad.getSeller().getId().equals(seller.getId())) {
            throw new IllegalAccessError("Not authorized to delete this ad");
        }
        adRepository.delete(ad);
    }

    public Ad updateStatus(Long adId, User seller, AdStatus status) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found"));
        if (!ad.getSeller().getId().equals(seller.getId())) {
            throw new IllegalAccessError("Not authorized to update this ad");
        }
        ad.setStatus(status);
        return adRepository.save(ad);
    }

    private List<Tag> processTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name);
            if (tag == null) {
                tag = new Tag();
                tag.setName(name);
                tag.setUsageCount(1);
            } else {
                tag.setUsageCount(tag.getUsageCount() + 1);
            }
            tags.add(tagRepository.save(tag));
        }
        return tags;
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

    public List<Ad> getSellerAds(Long sellerId) {
        return adRepository.findBySellerId(sellerId);
    }
}