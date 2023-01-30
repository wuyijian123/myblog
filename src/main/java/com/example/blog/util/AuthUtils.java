package com.example.blog.util;

import com.example.blog.entity.User;
import com.example.blog.web.model.VmUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthUtils {

    public static final List<VmUser> USERS = new ArrayList<>();


    public static boolean illegalUser(String token, int[] allowedRoles) {
        VmUser vmUser = USERS.stream()
                .filter(user -> user.getToken().equals(token))
                .findAny()
                .orElse(null);
        if (vmUser == null || isOverTime(vmUser)) {
            return true;
        }
        vmUser.setLastLogin(new Date());
        return Arrays.stream(allowedRoles).noneMatch(r -> r == vmUser.getType());
    }


    public static VmUser getUser(String token) {
        return USERS.stream()
                .filter(user -> user.getToken().equals(token))
                .findAny()
                .orElse(null);
    }


    public static boolean invalid(UUID userId, String token){
        VmUser user = getUser(token);
        if (user == null || isOverTime(user)){
            return true;
        }
        boolean valid = user.getId().equals(userId);
        if (valid){
            user.setLastLogin(new Date());
        }
        return !valid;
    }


    public static String logout(String token){
        VmUser vmUser = USERS.stream()
                .filter(user -> user.getToken().equals(token))
                .findAny()
                .orElse(null);
        if (vmUser == null){
            return "注销失败，未发现登录记录";
        }
        USERS.remove(vmUser);
        return "注销成功！";
    }


    private static int minutes;

    @Value("${app.over.minutes}")
    public void setMinutes(int minutes) {
        AuthUtils.minutes = minutes;
    }

    //计算当前用户是否过期
    private static boolean isOverTime(VmUser user) {
        long offMillis = System.currentTimeMillis() - user.getLastLogin().getTime();
        long minutes = offMillis / (1000 * 60);
        boolean over = minutes > AuthUtils.minutes;
        if (over) {
            USERS.remove(user);
        }
        return over;
    }

    //根据整数值获取角色列表
    public static List<Integer> getRoles(int value){
        List<Integer> result = new ArrayList<>();
        String strBinary = StringUtils.padLeft(Integer.toBinaryString(value), User.COUNT_TYPE, '0');
        int r = 0;
        for(char b : strBinary.toCharArray()){
            if (b == '1'){
                result.add(r);
            }
            r++;
        }
        return result;
    }
    //根据二进制字符串获取角色值
    public static int getAuthValue(String strBinary){
        return Integer.valueOf(strBinary, 2);
    }

    //获取默认的角色值
    public static int getDefaultAuthValue(){
        String strBinary = StringUtils.padRight("", User.COUNT_TYPE, '1');
        return Integer.valueOf(strBinary, 2);
    }
}
