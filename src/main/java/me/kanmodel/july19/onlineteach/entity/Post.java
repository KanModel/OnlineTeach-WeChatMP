package me.kanmodel.july19.onlineteach.entity;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-06-25-10:44
 */

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @description: todo
 * @author: KanModel
 * @create: 2019-06-25 10:44
 */

@Entity
@Table(name = "sys_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", columnDefinition = "varchar(50)  default ''")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "created_time", columnDefinition = "datetime")
    private Timestamp createdTime;

    @Column(name = "modified_time", columnDefinition = "datetime")
    private Timestamp modifiedTime;

    public Post(String title, String content, Timestamp createdTime) {
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = createdTime;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getShort(){
        if (content.length() < 10){
            return content;
        }
        return content.substring(0, 10) + "...";
    }

    public String getCreatedTimeFormat(){
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return time.format(createdTime.getTime());
    }

    public String getModifiedTimeFroma(){
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return time.format(modifiedTime.getTime());
    }
}
