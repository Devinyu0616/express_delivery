package com.devin.web.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data

@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
