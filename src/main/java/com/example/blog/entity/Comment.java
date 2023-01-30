package com.example.blog.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Comment  extends  BaseEntity{
    private String comment;
    @ManyToOne
    private  Article article;
    @ManyToOne(fetch = FetchType.EAGER)
    private  User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建日期

    public  Comment(){
        createTime =new Date();
    }

    @JsonManagedReference
    public Article getArticle() {
        return article;
    }
    @JsonManagedReference
    public User getUser() {
        return user;
    }

}
