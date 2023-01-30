package com.example.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Category extends BaseEntity {

    private String name;//类型名称
    @OneToMany(mappedBy = "category")
    private List<Article> articles=new ArrayList<>();


    @JsonBackReference

    public List<Article> getArticles() {
        return articles;
    }
}
