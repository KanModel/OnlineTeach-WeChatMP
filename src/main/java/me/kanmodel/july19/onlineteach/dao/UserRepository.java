package me.kanmodel.july19.onlineteach.dao;

import me.kanmodel.july19.onlineteach.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @description: 用户的持久化方法
 * @author: KanModel
 * @create: 2019-03-23 09:52
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String name);

    @Query(nativeQuery = true, value = "select * from sys_users where user_login like %:login%")
    Page<User> findAllByLogin(@Param("login") String login, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from sys_users u, sys_role r, sys_users_roles ur where " +
            "u.user_id = ur.user_user_id and ur.roles_role_id = r.role_id and role_name='ROLE_USER'")
    List<User> findAllTeacher();

    @Query(nativeQuery = true, value = "select * from sys_users u, sys_role r, sys_users_roles ur where " +
            "u.user_id = ur.user_user_id and ur.roles_role_id = r.role_id and role_name='ROLE_ADMIN'")
    List<User> findAllAdmin();

    @Query(nativeQuery = true, value = "select * from sys_users u, sys_role r, sys_users_roles ur where " +
            "u.user_id = ur.user_user_id and ur.roles_role_id = r.role_id and role_name='ROLE_ADMIN'")
    List<User> findAllSuper();

    Page<User> findAllByIsDelete(Boolean isDelete,Pageable pageable);

    @Query(nativeQuery = true, value = "select * from sys_users where user_login like %:login% AND is_delete = 0")
    Page<User> findAllByLoginAndIsDelete(@Param("login") String login, Pageable pageable);

}
