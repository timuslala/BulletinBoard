package com.back.ad;

import lombok.Data;

import java.util.List;

@Data
public class AdDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long sellerId;
    private List<String> images;
    private List<String> tags;
    private String email;
    private String phone;
    private boolean showEmail;
    private boolean showPhone;
}