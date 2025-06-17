package com.back.Ad;

import com.back.ad.Ad;
import com.back.ad.AdRepository;
import com.back.ad.AdService;
import com.back.ad.AdStatus;
import com.back.tag.TagService;
import com.back.user.User;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdServiceTest {

    @Autowired
    private AdService adService;

    @MockitoBean
    private AdRepository adRepository;

    @MockitoBean
    private TagService tagService;

    @Test
    public void createAd_throwsException_whenMoreThan10Images() {
        // given
        User seller = new User();
        List<String> images = new ArrayList<>();
        List<String> tagNames = new ArrayList<>();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            adService.createAd(seller, "title", "desc", images, tagNames, true, true);
        });
    }

    @Test
    public void updateAd_throwsException_whenAdNotFound() {
        // given
        User seller = new User();
        org.mockito.Mockito.when(adRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            adService.updateAd(999L, seller, "t", "d", new ArrayList<>(), new ArrayList<>(), true, true);
        });
    }

    @Test
    public void updateAd_throwsException_whenNotAuthorized() {
        // given
        User seller = new User();
        seller.setId(1L);
        Ad ad = new Ad();
        User otherSeller = new User();
        otherSeller.setId(2L);
        ad.setSeller(otherSeller);
        org.mockito.Mockito.when(adRepository.findById(1L)).thenReturn(java.util.Optional.of(ad));

        // when & then
        assertThrows(IllegalAccessError.class, () -> {
            adService.updateAd(1L, seller, "t", "d", new ArrayList<>(), new ArrayList<>(), true, true);
        });
    }

    @Test
    public void deleteAd_throwsException_whenAdNotFound() {
        // given
        User seller = new User();
        org.mockito.Mockito.when(adRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            adService.deleteAd(999L, seller);
        });
    }

    @Test
    public void deleteAd_throwsException_whenNotAuthorized() {
        // given
        User seller = new User();
        seller.setId(1L);
        Ad ad = new Ad();
        User otherSeller = new User();
        otherSeller.setId(2L);
        ad.setSeller(otherSeller);
        org.mockito.Mockito.when(adRepository.findById(1L)).thenReturn(java.util.Optional.of(ad));

        // when & then
        assertThrows(IllegalAccessError.class, () -> {
            adService.deleteAd(1L, seller);
        });
    }

    @Test
    public void updateStatus_throwsException_whenAdNotFound() {
        // given
        User seller = new User();
        org.mockito.Mockito.when(adRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            adService.updateStatus(999L, seller, AdStatus.PUBLISHED);
        });
    }

    @Test
    public void updateStatus_throwsException_whenNotAuthorized() {
        // given
        User seller = new User();
        seller.setId(1L);
        Ad ad = new Ad();
        User otherSeller = new User();
        otherSeller.setId(2L);
        ad.setSeller(otherSeller);
        org.mockito.Mockito.when(adRepository.findById(1L)).thenReturn(java.util.Optional.of(ad));

        // when & then
        assertThrows(AccessDeniedException.class, () -> {
            adService.updateStatus(1L, seller, AdStatus.PUBLISHED);
        });
    }

    @Test
    public void searchAds_returnsByTag_whenTagProvided() {
        // given
        List<Ad> ads = new ArrayList<>();
        org.mockito.Mockito.when(adRepository.findByTagsName("tag")).thenReturn(ads);

        // when
        List<Ad> result = adService.searchAds("query", "tag");

        // then
        assertSame(ads, result);
    }

    @Test
    public void searchAds_returnsByQuery_whenTagNotProvided() {
        // given
        List<Ad> ads = new ArrayList<>();
        org.mockito.Mockito.when(adRepository.searchByTitleOrDescription("query")).thenReturn(ads);

        // when
        List<Ad> result = adService.searchAds("query", "");

        // then
        assertSame(ads, result);
    }

    @Test
    public void getAdByPreviewToken_returnsAd() {
        // given
        Ad ad = new Ad();
        org.mockito.Mockito.when(adRepository.findByPreviewToken("token")).thenReturn(ad);

        // when
        Ad result = adService.getAdByPreviewToken("token");

        // then
        assertSame(ad, result);
    }

    @Test
    public void getAdsBySeller_returnsAds() {
        // given
        List<Ad> ads = new ArrayList<>();
        org.mockito.Mockito.when(adRepository.findBySellerId(1L)).thenReturn(ads);

        // when
        List<Ad> result = adService.getAdsBySeller(1L);

        // then
        assertSame(ads, result);
    }
}