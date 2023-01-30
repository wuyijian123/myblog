package com.example.blog.web.admin;
import com.example.blog.constant.ApiConstant;
import com.example.blog.constant.Messages;
import com.example.blog.entity.User;
import com.example.blog.service.UserService;

import com.example.blog.web.model.ApiResult;
import com.example.blog.web.model.VmUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Api(tags = "系统管理员业务")
@CrossOrigin
@RestController
@RequestMapping(ApiConstant.ROUTE_USER_ROOT)
public class AdminApiController {
    private final UserService service;

    @Autowired
    public AdminApiController(UserService service){
        this.service = service;
    }

    @ApiOperation("查询所有用户")
    @GetMapping(ApiConstant.ROUTE_USERS_ALL)
    public ApiResult getUsers(@RequestParam String token){
//        使用vmuser作为用户列表的类型
        List<VmUser> users = new ArrayList<>();

        service.get() //从数据库中查询所有的用户
                //遍历每个用户，处理后添加到users列表 .forEach（user -> users.add（））
                //VmUser.create(user, true)屏蔽其敏感信息，如密码、盐、tonken值，封装到vmuser类型
                .forEach(user
                        -> users.add(VmUser.create(user, true)));
        //ApiResult() 封装结果返回
        return new ApiResult(true, users.size() + "", users);
    }

    @ApiOperation("分页查询所有用户")
    @GetMapping(ApiConstant.ROUTE_USERS_PAGE)
    public ApiResult getPagedUsers(@RequestParam int page, @RequestParam String token, @RequestParam int size){
        List<VmUser> users = new ArrayList<>();
        service.get(page, size).forEach(user -> users.add(VmUser.create(user, true)));
        return new ApiResult(true, service.count() + "", users);
    }

    @ApiOperation("根据关键词查询用户")
    @GetMapping(ApiConstant.ROUTE_USERS_SEARCH)
    public ApiResult search(@RequestParam String token, @PathVariable String kw) {
        //数据库的user->现实的视图user
        //空的VmUser列表
        List<VmUser> users = new ArrayList<>();
        //1.service.search(kw)关键字查询，获得一组数据库用户user
        //2.forEach（user->****)遍历每个数据用户user，执行3
        //3.users.add（）把每个数据的用户user处理4后添加到List<VmUser> users定义的列表里
       //4.VmUser.create（）根据用户是否为admin确定封装vmuser时是否显示username
        //当前业务只有admin可以操作，所以设置第二个参数为true，即admin
        service.search(kw).forEach(user -> users.add(VmUser.create(user, true)));
        //返回统一格式的结果
        return new ApiResult(true, users.size() + "", users);
    }

    @ApiOperation("查看正在申请发布者的用户")
    @GetMapping(ApiConstant.ROUTE_USERS_APPLYING)
    public ApiResult getApplyingTeacher(@RequestParam String token){
        List<VmUser> users = new ArrayList<>();
        service.getByApplyCreator(true).forEach(user -> users.add(VmUser.create(user, true)));
        return new ApiResult(true, users.size() + "", users);
    }

    @ApiOperation("审批用户申请")
    @PostMapping(ApiConstant.ROUTE_USERS_APPROVE)
    public ApiResult approve(@RequestParam String token, @RequestParam UUID id){
        //同意ID用户成为发布者 type设置为1
        User user = service.approveCreator(id);

        //如果用户不存在，封装错误信息返回
        if (user == null){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
        //如果用户存在,以admin的视角封装VmUser.create(user, true)
        return new ApiResult(true, "", VmUser.create(user, true));
    }

    //@ApiIgnore
    @ApiOperation("拒绝用户申请")
    @PostMapping(ApiConstant.ROUTE_USERS_DECLINE)
    public ApiResult decline(@RequestParam String token, @RequestParam UUID id){
        User user = service.declineCreator(id);
        if (user == null){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
        return new ApiResult(true, "", VmUser.create(user, true));
    }

    @ApiOperation("禁止用户访问")
    @PostMapping(ApiConstant.ROUTE_USERS_BAN)
    public ApiResult ban(@RequestParam String token, @RequestParam UUID id){
        User user = service.banUser(id);
        if (user == null){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
        return new ApiResult(true, "", VmUser.create(user, true));
    }
}
