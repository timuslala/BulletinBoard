package com.back.ad;



import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.back.user.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @PostMapping
    public ResponseEntity<AdDto> createAd(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody AdDto request) {
        AdDto ad = adService.createAd(
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

    @GetMapping("/{id}")
    public ResponseEntity<AdDto> getAdById(@PathVariable Long id) {
        AdDto ad = adService.getAdById(id);
        return ResponseEntity.ok(ad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdDto> updateAd(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @RequestBody AdDto request) {
        AdDto ad = adService.updateAd(
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
    public ResponseEntity<AdDto> updateStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @RequestBody AdStatusDto request) {
        try {
            AdDto ad = adService.updateStatus(id, userDetails.getUser(), request.getStatus()).toDto();
            return ResponseEntity.ok(ad);
        } catch (java.nio.file.AccessDeniedException ex) {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/preview/{token}")
    public ResponseEntity<AdDto> previewAd(@PathVariable String token) {
        AdDto ad = adService.getAdByPreviewToken(token).toDto();
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdDto>> searchAds(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String tag) {
        List<AdDto> ads = adService.searchAds(query, tag)
                .stream()
                .map(Ad::toDto)
                .toList();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/my")
    public ResponseEntity<List<AdDto>> getMyAds(@AuthenticationPrincipal CustomUserDetails userDetails) {
        com.back.user.User user = userDetails.getUser();
        List<AdDto> ads = adService.getAdsBySeller(user.getId())
                .stream()
                .map(Ad::toDto)
                .toList();
        return ResponseEntity.ok(ads);
    }
}