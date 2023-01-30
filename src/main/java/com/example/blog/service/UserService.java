package com.example.blog.service;

import com.example.blog.entity.User;

import java.util.List;
import java.util.UUID;


public interface UserService {
    //校验用户名和密码
    User check(String userName, String password);
    //用户名是否唯一
    boolean isUserNameOccupied(String userName);
    //注册用户
    User register(User user);
    //根据ID查找指定用户
    User getById(UUID id);
    //根据用户名获取加密盐
    String getSalt(String userName);

    //获取用户列表
    List<User> get();
    //获取分页用户
    List<User> get(int page, int size);
    //获取用户数量
    Integer count();
    //查询用户
    List<User> search(String kw);

    //获取申请教师角色的用户
    List<User> getByApplyCreator(boolean applying);
    //同意用户申请教师角色
    User approveCreator(UUID id);
    //拒绝用户教师角色申请
    User declineCreator(UUID id);
   //屏蔽用户
    User banUser(UUID id);
}
