package com.back.ad;

import lombok.Data;

import java.util.List;

@Data
public class AdDto {
    private String title;
    private String description;
    private List<String> images;
    private List<String> tags;
    private boolean showEmail;
    private boolean showPhone;
}