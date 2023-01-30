package com.example.blog.constant;


public enum Messages {

    SUCCESS("操作成功"),
    WRONG_ID("错误的ID"),
    INCOMPLETE_INFO("信息不完整"),
    RELATED_DATA("存在关联数据"),
    NO_PERMISSION("没有操作权限"),
    WRONG_PASSWORD("用户名密码错误"),
    USER_FORBIDDEN("用户禁止访问"),
    USER_EXISTS("用户已存在"),
    INVALID_FORMAT("非法的数据格式");

    private final String name;

    Messages(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
