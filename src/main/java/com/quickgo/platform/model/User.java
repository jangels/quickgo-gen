package com.quickgo.platform.model;


import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String nickname;
    private Long createtime;
    private String email;
    private String password;
    private String type;
    private String avatar;
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public interface Type{
        String USER="USER";
        String ADMIN="ADMIN";
    }
    public interface Status{
        String VALID="VALID";
        String INVALID="INVALID";
        String PENDING="PENDING";
    }
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createtime=" + createtime +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", avatar='" + avatar + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public User() {
    }
}
