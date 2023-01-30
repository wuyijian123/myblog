package com.example.blog.web.model;

import com.example.blog.entity.Category;
import lombok.Data;

import java.util.UUID;

@Data
public class VmCategory {
    private UUID uuid;
    private  String name;

    public VmCategory(Category category) {
        this.uuid =category.getId();
        this.name = category.getName();
    }

}
