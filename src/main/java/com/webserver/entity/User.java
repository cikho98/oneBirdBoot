package com.webserver.entity;

public class User {
    private String name ;
    private String password;
    private String nickname;
    private int age;

    public User(String name, String password, String nickname, int age) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                '}';
    }
}
