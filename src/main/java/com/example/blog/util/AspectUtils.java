package com.example.blog.util;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;


public class AspectUtils {
    private static final String ARG_TOKEN = "token";
    private static final String ARG_JSON = "json";

    public static Map<String, Object> getParas(JoinPoint point){
        Object[] args = point.getArgs();
        Map<String, Object> map = new HashMap<>(args.length);
        String[] names = ((MethodSignature)point.getSignature()).getParameterNames();
        for (int i = 0; i < names.length; i++){
            map.put(names[i], args[i]);
        }
        return map;
    }

    public static String getToken(ProceedingJoinPoint point){
        String token;
        Map<String, Object> map = AspectUtils.getParas(point);
        if (map.containsKey(ARG_TOKEN)){
            token = map.get(ARG_TOKEN).toString();
        } else {
            JSONObject json = (JSONObject) map.get(ARG_JSON);
            token = json.getString(ARG_TOKEN);
        }
        return token;
    }
}
