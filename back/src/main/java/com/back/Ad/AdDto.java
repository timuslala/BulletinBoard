package com.back.Ad;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

import java.util.List;

@Data
public class AdDto {
    private String title;
    private String description;
    private List<MultipartFile> images;
    private List<String> tags;
    private boolean showEmail;
    private boolean showPhone;
}