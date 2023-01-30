package com.example.blog.service;

import com.example.blog.entity.User;
import com.example.blog.repository.UserProjection;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    //注入UserRepository
    @Autowired
    public UserServiceImpl(UserRepository repository){
        this.repository = repository;
    }

    //通过用户名和密码校验用户用户--通过JPA的repository去数据校验
    //给定用户名和密码，校验成功返回用户，否则返回null
    @Override
    public User check(String userName, String password) {
        return repository.findUserByUserNameAndPassword(userName, password);
    }

    //给定用户名通过JPA-repository查数据库是否有超过0个记录存在
    //表示数据库中是否有同名用户存在--注册业务时使用
    @Override
    public boolean isUserNameOccupied(String userName) {
        return repository.countByUserName(userName) > 0;
    }

    //注册用户时，等价于向数据库新增一条用户记录
    @Override
    public User register(User user) {
        return repository.save(user);
    }

    //通过用户名获取用户的Salt-用于密码加密验证
    @Override
    public String getSalt(String userName) {
        //通过用户名查找用户
        List<UserProjection> users = repository.findUsersByUserName(userName);

        if (users == null || users.size() == 0){
            return "";
        }

        UserProjection user = users.get(0);
        if (user == null){
            return "";
        }
        String salt = user.getSalt();
        //如果数据库中存在该用户，就返回该用户的salt
        return salt == null ? "" : salt;
    }
    //查询所有用户
    @Override
    public List<User> get() {
        return repository.findAllByOrderByUpdateTimeDesc();
    }
    //分页查询所有，page=页号，从0开始计数，size每页记录数
    @Override
    public List<User> get(int page, int size) {
        return repository.findAllByOrderByUpdateTimeDesc(PageRequest.of(page, size));
    }
   //查询所有用户的数量
    @Override
    public Integer count() {
        return repository.countAllBy();
    }
    //关键字查询
    @Override
    public List<User> search(String kw) {
        return repository.findByQuery(kw);
    }

    //查询指定ID的用户，如果用户不存在返回null
    @Override
    public User getById(UUID id) {
        return repository.findById(id).orElse(null);
    }
    //查询是否申请教师的用户
    @Override
    public List<User> getByApplyCreator(boolean applying) {
        return repository.findUsersByApplyCreatorOrderByUpdateTimeDesc(applying);
    }

    //事务Transactional
    //审批-同意成为教师
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User approveCreator(UUID id) {
        //findById是eager的，立刻返回值，getById/getOne是延迟加载
        User user = repository.findById(id).orElse(null);
        //如果指定用存在
        if (user != null){
            //改变用户的申请状态，设置为不申请。
            user.setApplyCreator(false);
            //把用户类型设置为发布者
            user.setType(User.TYPE_CREATOR);
            //存储到数据库中--更新操作
            return repository.save(user);
        }
        //否则返回空
        return null;
    }
    //审批业务-拒绝申请
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User declineCreator(UUID id) {
        User user = repository.findById(id).orElse(null);
        if (user != null){
            //修改申请状态-设置为不申请
            user.setApplyCreator(false);
            return repository.save(user);
        }
        return null;
    }
   //屏蔽用户
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User banUser(UUID id) {
        User user = repository.findById(id).orElse(null);
        //用户不为null，当前用户不是admin就可以禁用
        if (user != null && user.getType() != User.TYPE_ADMIN){
            //设置用户的类型为-1
            user.setType(User.TYPE_BANNED);
            return repository.save(user);
        }
        return null;
    }

}
