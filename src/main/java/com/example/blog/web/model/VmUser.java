package com.example.blog.web.model;

import com.example.blog.entity.User;
import lombok.Getter;
import lombok.Setter;
import com.example.blog.util.AuthUtils;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
public class VmUser {
    private UUID id;
    private String nickName;
    private String userName;
    private String phone;
    private String email;
    private String avatar;
    private String token;
    private int type;
    private Date lastLogin;

    private VmUser(){}

    //私有构造函数。在登录时生成随机token，并将用户的登录信息保存到内存。
    private VmUser(User user){
        id = user.getId();
        nickName = user.getNickName();
        userName = user.getUserName();
        phone = user.getPhone();
        email = user.getEmail();
        avatar = user.getAvatar();
        type = user.getType();
        lastLogin = new Date();
        //UUID随机字符串生成token
        token = UUID.randomUUID().toString();
        //把当前登录的用户添加到服务器内存
        AuthUtils.USERS.add(this);
    }

    //通过登录返回（已有的，没有才创建）对象
    public static VmUser apiLogin(User user) {
        VmUser vmUser = AuthUtils.USERS  //服务器内存中的已登录用户列表Vmuser
                .stream()//集合转换为流
                //从服务器已登录用户列表内存列表中查找用户user,结果仍然是流
                //这个结果流有可能是空的--该用户未登录
                //这个结果流有数据--该用户已经登录
                .filter(u -> u.userName.equals(user.getUserName()))
                //假设这个结果流有数据--该用户已经登录，获取第一个数据（登录用户）
                .findFirst()
                //找不到这个用户的话-该用户未登录，就返回null
                .orElse(null);
        //该用户未登录null,就创建VmUser实例，创建token,存储到服务器内存列表中
        if (vmUser == null) {
            vmUser = new VmUser(user);
        } else {
            //该用户已经登录，修改登录时间
            vmUser.lastLogin = new Date();
        }
        //返回登录用户
        return vmUser;
    }

    //静态工厂方法生成屏蔽了敏感数据的用户信息供不同身份的用户查看
    public static VmUser create(User user, boolean admin){
        VmUser vm = new VmUser();
        vm.setId(user.getId());
        vm.setNickName(user.getNickName());
        vm.setPhone(user.getPhone());
        vm.setEmail(user.getEmail());
        vm.setAvatar(user.getAvatar());
        vm.setToken("");
        if (admin){
            vm.setUserName(user.getUserName());
            vm.setType(user.getType());
        } else {
            vm.setUserName("");
            vm.setType(User.TYPE_STUDENT);
        }
        return vm;
    }
}
