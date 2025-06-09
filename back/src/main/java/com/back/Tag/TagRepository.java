package com.back.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
    @Query("SELECT t FROM Tag t WHERE t.name LIKE %:prefix% ORDER BY t.usageCount DESC")
    List<Tag> findByNameStartingWith(String prefix);
}