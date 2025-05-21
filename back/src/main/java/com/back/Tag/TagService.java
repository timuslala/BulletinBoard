package com.back.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> suggestTags(String prefix) {
        return tagRepository.findByNameStartingWith(prefix);
    }
}