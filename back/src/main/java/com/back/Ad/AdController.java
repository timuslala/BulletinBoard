package com.back.Ad;



import com.back.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @PostMapping
    public ResponseEntity<Ad> createAd(
            @AuthenticationPrincipal User user,
            @RequestBody AdDto request) {
        Ad ad = adService.createAd(
                user,
                request.getTitle(),
                request.getDescription(),
                request.getPhotoUrls(),
                request.getTags(),
                request.isShowEmail(),
                request.isShowPhone()
        );
        return ResponseEntity.ok(ad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ad> updateAd(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody AdDto request) {
        Ad ad = adService.updateAd(
                id,
                user,
                request.getTitle(),
                request.getDescription(),
                request.getPhotoUrls(),
                request.getTags(),
                request.isShowEmail(),
                request.isShowPhone()
        );
        return ResponseEntity.ok(ad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        adService.deleteAd(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Ad> updateStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody AdStatusDto request) {
        Ad ad = adService.updateStatus(id, user, request.getStatus());
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/preview/{token}")
    public ResponseEntity<Ad> previewAd(@PathVariable String token) {
        Ad ad = adService.getAdByPreviewToken(token);
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ad>> searchAds(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String tag) {
        List<Ad> ads = adService.searchAds(query, tag);
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Ad>> getMyAds(@AuthenticationPrincipal User user) {
        List<Ad> ads = adService.getSellerAds(user.getId());
        return ResponseEntity.ok(ads);
    }
}