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
		AdDto adDto = new AdDto();
		adDto.setTitle("Test Title");
		adDto.setDescription("Test Description");
		adDto.setShowEmail(true);
		adDto.setShowPhone(false);


		org.mockito.Mockito.when(adService.createAd(any(User.class), anyString(), anyString(), anyList(), anyList(), anyBoolean(), anyBoolean()))
				.thenReturn(adDto);

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
		AdDto adDto = new AdDto();
		adDto.setTitle("Updated Title");
		adDto.setDescription("Updated Description");
		adDto.setShowEmail(false);
		adDto.setShowPhone(true);

		when(adService.updateAd(eq(2L), any(User.class), anyString(), anyString(), anyList(), anyList(), anyBoolean(), anyBoolean()))
				.thenReturn(adDto);

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
		mockMvc.perform(delete("/api/ads/3")
				.with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails())))
				.andExpect(status().isNoContent());
	}

	@Test
	public void updateStatus_shouldReturnAd() throws Exception {
		AdStatusDto statusDto = new AdStatusDto();
		statusDto.setStatus(AdStatus.PUBLISHED);

		Ad ad = new Ad();
		ad.setId(4L);
		ad.setStatus(AdStatus.PUBLISHED);

		when(adService.updateStatus(eq(4L), any(User.class), eq(AdStatus.PUBLISHED))).thenReturn(ad);

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
		AdStatusDto statusDto = new AdStatusDto();
		statusDto.setStatus(AdStatus.PUBLISHED);

		when(adService.updateStatus(eq(5L), any(User.class), eq(AdStatus.PUBLISHED)))
				.thenThrow(new java.nio.file.AccessDeniedException("denied"));

		mockMvc.perform(put("/api/ads/5/status")
				.with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(statusDto)))
				.andExpect(status().isForbidden());
	}

	@Test
	public void previewAd_shouldReturnAd() throws Exception {
		Ad ad = new Ad();
		ad.setId(6L);
		ad.setTitle("Preview Ad");

		when(adService.getAdByPreviewToken("token123")).thenReturn(ad);

		mockMvc.perform(get("/api/ads/preview/token123"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(6L))
				.andExpect(jsonPath("$.title").value("Preview Ad"));
	}

	@Test
	public void searchAds_shouldReturnList() throws Exception {
		Ad ad1 = new Ad();
		ad1.setId(7L);
		ad1.setTitle("Ad1");
		Ad ad2 = new Ad();
		ad2.setId(8L);
		ad2.setTitle("Ad2");

		when(adService.searchAds("query", "tag")).thenReturn(Arrays.asList(ad1, ad2));

		mockMvc.perform(get("/api/ads/search")
				.param("query", "query")
				.param("tag", "tag"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(7L))
				.andExpect(jsonPath("$[1].id").value(8L));
	}

	@Test
	public void getMyAds_shouldReturnUserAds() throws Exception {
		Ad ad = new Ad();
		ad.setId(9L);
		ad.setTitle("My Ad");

		when(adService.getAdsBySeller(1L)).thenReturn(Collections.singletonList(ad));

		mockMvc.perform(get("/api/ads/my")
				.with(SecurityMockMvcRequestPostProcessors.user(getMockUserDetails())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(9L))
				.andExpect(jsonPath("$[0].title").value("My Ad"));
	}
}
// This code is a test class for the AdController in a Spring Boot application.