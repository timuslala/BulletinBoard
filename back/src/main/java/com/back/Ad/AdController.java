package com.back.Ad;



import com.back.User.CustomUserDetails;
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
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody AdDto request) {
        Ad ad = adService.createAd(
                userDetails.getUser(),
                request.getTitle(),
                request.getDescription(),
                request.getImages(),
                request.getTags(),
                request.isShowEmail(),
                request.isShowPhone()
        );
        return ResponseEntity.ok(ad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ad> updateAd(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @RequestBody AdDto request) {
        Ad ad = adService.updateAd(
                id,
                userDetails.getUser(),
                request.getTitle(),
                request.getDescription(),
                request.getImages(),
                request.getTags(),
                request.isShowEmail(),
                request.isShowPhone()
        );
        return ResponseEntity.ok(ad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id) {
        adService.deleteAd(id, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Ad> updateStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @RequestBody AdStatusDto request) {
        Ad ad = adService.updateStatus(id, userDetails.getUser(), request.getStatus());
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
    public ResponseEntity<List<Ad>> getMyAds(@AuthenticationPrincipal CustomUserDetails userDetails) {
        com.back.User.User user = userDetails.getUser();
        List<Ad> ads = adService.getAdsBySeller(user.getId());
        return ResponseEntity.ok(ads);
    }
}