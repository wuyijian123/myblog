package com.example.blog.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {
    @Id

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    //设置该列的数据库类型为16字节二进制字符串
    @Column(columnDefinition = "BINARY(16)")
    protected UUID id;

    protected BaseEntity() {
    }

    public UUID getId() {
        return this.id;
    }


    public void setId(UUID id) {
        this.id = id;
    }
}
