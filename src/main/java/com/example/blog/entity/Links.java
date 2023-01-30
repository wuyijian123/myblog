package com.example.blog.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Links extends  BaseEntity{
    private  String name;
    private  String link;
    public Links(){
    }
}
