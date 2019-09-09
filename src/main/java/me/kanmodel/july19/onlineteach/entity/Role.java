package me.kanmodel.july19.onlineteach.entity;

/**
 * Created with IntelliJ IDEA.
 * Description:角色实体
 * User: KanModel
 * Date: 2019-04-13-10:31
 */
public enum Role {
    ROLE_USER("ROLE_USER", 1L), ROLE_ADMIN("ROLE_ADMIN", 2L), ROLE_SUPER("ROLE_SUPER", 3L);

    private String name;
    private Long id;

    Role(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
