package com.back.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> suggestTags(String prefix) {
        return tagRepository.findByNameStartingWith(prefix);
    }
    @Transactional
    public List<Tag> processTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name);
            if (tag == null) {
                tag = new Tag();
                tag.setName(name);
                tag.setUsageCount(1);
                tag = tagRepository.save(tag);
            } else {
                tag.setUsageCount(tag.getUsageCount() + 1);
                tag = tagRepository.save(tag);
            }
            tags.add(tag);
        }
        tagRepository.flush();
        return tags;
    }
    @Transactional
    public void removeTags(List<Tag> tags) {
        for (Tag tag : tags) {
            tag.setUsageCount(tag.getUsageCount() - 1);
            if (tag.getUsageCount() <= 0) {
                tagRepository.delete(tag);
            } else {
                tagRepository.save(tag);
            }
            tagRepository.flush();
        }
    }
}