package com.back.Ad;

import com.back.ad.Ad;
import com.back.ad.AdDto;
import com.back.ad.AdService;
import com.back.ad.AdStatus;
import com.back.ad.AdStatusDto;
import com.back.user.CustomUserDetails;
import com.back.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdService adService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomUserDetails getMockUserDetails() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        return new CustomUserDetails(user);
    }

    @Test
    public void createAd_shouldReturnAd() throws Exception {
        // given
        AdDto adDto = new AdDto();
        adDto.setTitle("Test Title");
        adDto.setDescription("Test Description");
        adDto.setShowEmail(true);
        adDto.setShowPhone(false);
        adDto.setImages(List.of("img1.jpg"));
        adDto.setTags(List.of("tag1"));

        org.mockito.Mockito.when(adService.createAd(any(User.class), anyString(), anyString(), anyList(), anyList(), anyBoolean(), anyBoolean()))
                .thenReturn(adDto);

        // when & then
        mockMvc.perform(post("/api/ads")
                .with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.images[0]").value("img1.jpg"))
                .andExpect(jsonPath("$.tags[0]").value("tag1"))
                .andExpect(jsonPath("$.showEmail").value(true))
                .andExpect(jsonPath("$.showPhone").value(false));
    }

    @Test
    public void updateAd_shouldReturnUpdatedAd() throws Exception {
        // given
        AdDto adDto = new AdDto();
        adDto.setTitle("Updated Title");
        adDto.setDescription("Updated Description");
        adDto.setShowEmail(false);
        adDto.setShowPhone(true);
        adDto.setImages(List.of("img2.jpg"));
        adDto.setTags(List.of("tag2"));

        when(adService.updateAd(eq(2L), any(User.class), anyString(), anyString(), anyList(), anyList(), anyBoolean(), anyBoolean()))
                .thenReturn(adDto);

        // when & then
        mockMvc.perform(put("/api/ads/2")
                .with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.images[0]").value("img2.jpg"))
                .andExpect(jsonPath("$.tags[0]").value("tag2"))
                .andExpect(jsonPath("$.showEmail").value(false))
                .andExpect(jsonPath("$.showPhone").value(true));
    }

    @Test
    public void deleteAd_shouldReturnNoContent() throws Exception {
        // given - nothing to mock

        // when & then
        mockMvc.perform(delete("/api/ads/3")
                .with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails())))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateStatus_shouldReturnAd() throws Exception {
        // given
        AdStatusDto statusDto = new AdStatusDto();
        statusDto.setStatus(AdStatus.PUBLISHED);

        Ad ad = new Ad();
        ad.setId(4L);
        ad.setStatus(AdStatus.PUBLISHED);
        User seller = new User();
        seller.setId(1L);
        ad.setSeller(seller);
        when(adService.updateStatus(eq(4L), any(User.class), eq(AdStatus.PUBLISHED))).thenReturn(ad);

        // when & then
        mockMvc.perform(put("/api/ads/4/status")
                .with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(jsonPath("$.status").value("PUBLISHED"));
    }

    @Test
    public void updateStatus_accessDenied_shouldReturn403() throws Exception {
        // given
        AdStatusDto statusDto = new AdStatusDto();
        statusDto.setStatus(AdStatus.PUBLISHED);

        when(adService.updateStatus(eq(5L), any(User.class), eq(AdStatus.PUBLISHED)))
                .thenThrow(new java.nio.file.AccessDeniedException("denied"));

        // when & then
        mockMvc.perform(put("/api/ads/5/status")
                .with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void previewAd_shouldReturnAd() throws Exception {
        // given
        Ad ad = new Ad();
        ad.setId(6L);
        ad.setTitle("Preview Ad");
        User seller = new User();
        seller.setId(1L);
        ad.setSeller(seller);
        when(adService.getAdByPreviewToken("token123")).thenReturn(ad);

        // when & then
        mockMvc.perform(get("/api/ads/preview/token123")
                .with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6L))
                .andExpect(jsonPath("$.title").value("Preview Ad"));
    }

    @Test
    public void searchAds_shouldReturnList() throws Exception {
        // given
        Ad ad1 = new Ad();
        ad1.setId(7L);
        ad1.setTitle("Ad1");
        Ad ad2 = new Ad();
        ad2.setId(8L);
        ad2.setTitle("Ad2");
        User seller = new User();
        seller.setId(1L);
        ad1.setSeller(seller);
        ad2.setSeller(seller);
        when(adService.searchAds("query", "tag")).thenReturn(Arrays.asList(ad1, ad2));

        // when & then
        mockMvc.perform(get("/api/ads/search")
                .param("query", "query")
                .param("tag", "tag"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(7L))
                .andExpect(jsonPath("$[1].id").value(8L));
    }

    @Test
    public void getMyAds_shouldReturnUserAds() throws Exception {
        // given
        Ad ad = new Ad();
        ad.setId(9L);
        ad.setTitle("My Ad");
        User seller = new User();
        seller.setId(1L);
        ad.setSeller(seller);
        when(adService.getAdsBySeller(1L)).thenReturn(Collections.singletonList(ad));

        // when & then
        mockMvc.perform(get("/api/ads/my")
                .with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(9L))
                .andExpect(jsonPath("$[0].title").value("My Ad"));
    }
}