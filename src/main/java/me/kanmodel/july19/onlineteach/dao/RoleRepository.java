package me.kanmodel.july19.onlineteach.dao;

import me.kanmodel.july19.onlineteach.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 角色的持久化方法
 */
public interface RoleRepository  extends JpaRepository<SysRole, Long> {
//    User findByLogin(String name);
    SysRole findByName(String name);
}
