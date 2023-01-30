package com.example.blog.web.model;

import com.example.blog.entity.Links;
import lombok.Data;

import java.util.UUID;

@Data
public class VmLinks {
    private UUID id;
    private  String name;
    private  String links;



    public VmLinks(Links links) {
        this.id = links.getId();
        this.name = links.getName();
        this.links =links.getLink();
    }
}
