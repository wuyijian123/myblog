package com.example.blog.repository;

import com.example.blog.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByUserNameAndPassword(String userName, String password);
    Integer countByUserName(String userName);

    //查询所有用户的数量
    Integer countAllBy();
    /**
     * get全部数据，虽然没有过滤条件，但findAll后面的By必须有，jpa规则，否则查不到数据
     * @return 全部用户数据
     */
    //查询所有的用户，以UpdateTime做排序，降序Desc
    List<User> findAllByOrderByUpdateTimeDesc();
    //分页查询用户，以UpdateTime做排序，降序Desc
    List<User> findAllByOrderByUpdateTimeDesc(Pageable pageable);
    //查询所有（申请/没申请）教师的用户ApplyTeacher，查询结果以ByUpdateTime降序排序，参数为是否申请
    List<User> findUsersByApplyCreatorOrderByUpdateTimeDesc(boolean applying);

    //模糊查询用户名/昵称/email/电话号码/
    @Query("select u from User u where u.userName like CONCAT('%',:kw,'%') or u.nickName " +
            "like CONCAT('%',:kw,'%') or u.email like CONCAT('%',:kw,'%') or u.phone like CONCAT('%',:kw,'%')")
    List<User> findByQuery(@Param("kw") String kw);

    List<UserProjection> findUsersByUserName(String user);
}
