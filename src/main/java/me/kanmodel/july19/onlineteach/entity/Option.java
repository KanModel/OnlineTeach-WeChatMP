package me.kanmodel.july19.onlineteach.entity;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-07-09-8:58
 */

import javax.persistence.*;

/**
 * @description: todo
 * @author: KanModel
 * @create: 2019-07-09 08:58
 */
@Entity
@Table(name = "sys_option")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @Column(name = "option_key", columnDefinition = "varchar(50)", nullable = false)
    private String key;

    @Column(name = "option_value", columnDefinition = "varchar(100)  default ''")
    private String value;

    @Column(name = "option_description", columnDefinition = "varchar(100)  default ''")
    private String description;

    public Option() {
    }

    public Option(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Option(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
