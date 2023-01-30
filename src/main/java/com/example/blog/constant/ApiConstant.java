package com.example.blog.constant;


public final class ApiConstant {
    private ApiConstant(){}
    /**
     * 指定api返回的content type
     */
    public static final String API_PRODUCES = "application/json";
    /**
     * 以下为路由配置
     */
    public static final String ROUTE_AUTH_ROOT = "api/v1";
    public static final String ROUTE_AUTH_LOGIN = "/login";
    public static final String ROUTE_AUTH_LOGOUT = "/logout";
    public static final String ROUTE_AUTH_EXISTS = "/exists/{user}";
    public static final String ROUTE_AUTH_REGISTER = "/register";
    public static final String ROUTE_FILE_UPLOAD = "/upload";

    public static final String ROUTE_USER_ROOT = "api/v1/admin";
    public static final String ROUTE_USERS_ALL = "/";
    public static final String ROUTE_USERS_SEARCH = "/search/{kw}";
    public static final String ROUTE_USERS_PAGE = "/page";
    public static final String ROUTE_USERS_APPLYING = "/applying";
    public static final String ROUTE_USERS_APPROVE = "/approve";
    public static final String ROUTE_USERS_DECLINE = "/decline";
    public static final String ROUTE_USERS_BAN = "/ban";

    public static final String ROUTE_ARTICLE_ROOT = "api/v1/article";

    public static final String KEY_TOKEN = "token";

    public static final String KEY_LOGIN_USER_NAME = "username";
    public static final String KEY_LOGIN_PASSWORD = "password";

    public static final String KEY_REGISTER_NICK_NAME = "nick_name";
    public static final String KEY_REGISTER_USER_NAME = "user_name";
    public static final String KEY_REGISTER_PASSWORD = "password";
    public static final String KEY_REGISTER_AVATAR = "avatar";
    public static final String KEY_REGISTER_EMAIL = "email";
    public static final String KEY_REGISTER_PHONE = "phone";
    public static final String KEY_REGISTER_APPLY_TEACHER = "apply_creator";


}
