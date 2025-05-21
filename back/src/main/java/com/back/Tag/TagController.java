package com.back.Tag;

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
    public ResponseEntity<List<Tag>> suggestTags(@RequestParam String prefix) {
        List<Tag> tags = tagService.suggestTags(prefix);
        return ResponseEntity.ok(tags);
    }
}