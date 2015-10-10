/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;

/**
 *
 * @javabean for User Entity
 */
public class User implements Serializable {
    private String fullName, email, password, nickname, birthdate;
    
    public User() {
        this.fullName = "";
        this.email = "";
        this.password = "";
        this.nickname = "";
        this.birthdate = "";
    }

    public User(String email, String password, String fullName, String nickname, String birthdate) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.nickname = nickname;
        this.birthdate = birthdate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "User{" + "fullName=" + fullName + ", email=" + email + ", password=" + password + ", nickname=" + nickname + ", birthdate=" + birthdate + '}';
    }
}
