package me.kanmodel.july19.onlineteach.entity;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-06-25-10:44
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @description: 内容JavaBean
 * @author: KanModel
 * @create: 2019-06-25 10:44
 */

@ApiModel("内容")
@Entity
@Table(name = "sys_post")
public class Post {
    @ApiModelProperty(value = "id", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("内容标题")
    @Column(name = "title", columnDefinition = "varchar(50)  default ''")
    private String title;

    @ApiModelProperty("内容内容")
    @Column(name = "content", columnDefinition = "text")
    private String content;

    @ApiModelProperty("内容创建时间")
    @Column(name = "created_time", columnDefinition = "datetime")
    private Timestamp createdTime;

    @ApiModelProperty("内容修改时间")
    @Column(name = "modified_time", columnDefinition = "datetime")
    private Timestamp modifiedTime;

    @ApiModelProperty("是否已经删除")
    private Boolean isDelete = false;

    public Post(String title, String content, Timestamp createdTime, Boolean isDelete) {
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = createdTime;
        this.isDelete = isDelete;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}
