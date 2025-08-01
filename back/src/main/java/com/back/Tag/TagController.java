package com.back.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/suggest")
    public ResponseEntity<List<TagDto>> suggestTags(@RequestParam String prefix) {
        List<TagDto> tags = tagService.suggestTags(prefix).stream()
                .map(Tag::toDto)
                .toList();
        return ResponseEntity.ok(tags);
    }
}