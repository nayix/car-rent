package com.car.rent.dao;

import com.car.rent.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author nayix
 * @date 2020/6/28 20:09
 */
public interface UserDAO extends CrudRepository<User, Long> {
    /**
     * 通过tel获取用户MD5盐
     * @param tel
     * @return
     */
    @Query("select salt from User where tel = :tel")
    String getSaltByTel(@Param("tel") String tel);

    /**
     * 通过tel查询用户
     * @param tel
     * @return
     */
    User findUserByTel(String tel);

    /**
     * 通过id获取user
     * @param uid
     * @return
     */
    User findUserByUid(long uid);

    /**
     * 通过手机号和密码删除用户
     * @param tel
     * @param password
     * @return
     */
    @Modifying
    @Transactional
    @Query("delete from User where tel = :tel and password = :password")
    int deleteByTelAndPassword(@Param("tel") String tel, @Param("password") String password);

    /**
     * 通过id更新余额
     * @param uid
     * @param balance
     */
    @Modifying
    @Transactional
    @Query("update User set balance = :balance where uid = :uid")
    void updateBalance(long uid, int balance);

    /**
     * 通过手机号更改用户名
     * @param tel
     * @param username
     * @return
     */
    @Modifying
    @Transactional
    @Query("update User set username = :username where tel = :tel")
    int updateUsername(@Param("tel") String tel, @Param("username") String username);

    /**
     * 通过手机号和旧密码更改密码
     * @param tel
     * @param oldPass
     * @param newPass
     * @return
     */
    @Modifying
    @Transactional
    @Query("update User set password = :newPass, salt = :newSalt where tel = :tel and password = :oldPass")
    int updatePassword(@Param("tel") String tel, @Param("oldPass") String oldPass, @Param("newPass") String newPass, @Param("newSalt") String newSalt);
}
