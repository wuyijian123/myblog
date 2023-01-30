package com.example.blog.web.model;

import com.example.blog.entity.Tag;
import lombok.Data;

import java.util.UUID;

@Data
public class VmTag {
    private UUID uuid;
    private  String name;

    public VmTag(Tag tag) {
        this.name =tag.getName() ;
        this.uuid=tag.getId();
    }
}
