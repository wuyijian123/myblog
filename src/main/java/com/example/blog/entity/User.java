package com.example.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//注解为一个实体
@Entity
public class User extends BaseEntity {

    @Transient
    public static final int TYPE_BANNED = -1; //禁用账号类型
    @Transient
    public static final int TYPE_ADMIN = 0;//管理员类型
    @Transient
    public static final int TYPE_CREATOR = 1;//创作者类型
    @Transient
    public static final int TYPE_STUDENT = 2;//学生类型
    @Transient
    public static final int COUNT_TYPE = 3;//类型数量
    private String nickName; //昵称
    private String userName;//用户名
    private String phone;//电话号码
    private String password;//密码
    private String email;//邮箱
    private String avatar;
    private String salt;
    private int type;

    private boolean applyCreator; //是否申请


    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime; //注册时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime; //更新时间

    @OneToMany(mappedBy = "creator")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    //构造函数，创建用户时设置创建时间和更新时间
    public User() {
        createTime = new Date();
        updateTime = createTime;
    }

    //重写实例判等函数
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.getId());
    }


    //重写hashcode函数
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @JsonBackReference
    public List<Article> getArticles() {
        return articles;
    }

    @JsonBackReference
    public List<Comment> getComments() {
        return comments;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    //各属性的getter和setter
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setApplyCreator(boolean applyCreator) {
        this.applyCreator = applyCreator;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isApplyCreator() {
        return applyCreator;
    }



}
