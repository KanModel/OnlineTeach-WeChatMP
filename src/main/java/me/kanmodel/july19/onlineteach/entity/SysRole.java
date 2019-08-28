package me.kanmodel.july19.onlineteach.entity;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-04-07-11:01
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @description: todo
 * @author: KanModel
 * @create: 2019-04-07 11:01
 */
@Entity
public class SysRole {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;
    @Column(name = "role_name", columnDefinition = "varchar(20)", nullable = false)
    private String name;

    public SysRole(){}

    public SysRole(Long id ,String name) {
        this.id = id;
        this.name = name;
    }

    public SysRole(Role role){
        this.id = role.getId();
        this.name = role.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
