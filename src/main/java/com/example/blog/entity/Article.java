package com.example.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
public class Article extends  BaseEntity {

    @Transient
    public static final boolean TYPE_OPEN = true; //开启
    @Transient
    public static final boolean TYPE_OFF = false;//关闭
    private  String title;//文章标题
    private String content; //文章内容
    private  boolean open;//是否开放
    private String cover;//封面


    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建日期

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//更新日期

    @ManyToOne
    private User creator;
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "article")
    private List<Comment> comments=new ArrayList<>();
    @ManyToOne
    private  Category category;//类型
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "article_tags",
            joinColumns = { @JoinColumn(name = "article_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private Set<Tag> tags =new HashSet<>();


    public Article() {
        createTime=new Date();
        updateTime=createTime;
    }
    @JsonManagedReference
    public  User getCreator(){
        return  creator;
    }
    @JsonBackReference
    public List<Comment> getComments() {
        return comments;
    }
    @JsonManagedReference
    public Category getCategory() {
        return category;
    }

}
