package com.back.tag;

public class TagDto {

    private String name;
    private int usageCount;

    public TagDto() {
    }

    public TagDto(String name, int usageCount) {
        this.name = name;
        this.usageCount = usageCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
