/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 *
 * @javabean for User Entity
 */
public class User implements Serializable {
    private long id;
    private String fullName, email, password, nickname;
    private Date birthdate;

    public User() {}

    public User(String fullName, String email, String password, String nickname, Date birthdate) {
        this.id = new Random().nextInt(1000000000);
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthdate = birthdate;
    }
    
    public User(long id, String fullName, String email, String password, String nickname, Date birthdate) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthdate = birthdate;
    }
    
    public void setId() {
        this.id = new Random().nextInt(1000000000);
    }

    public long getId() {
        return id;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    
    @Override
    public String toString() {
        return "User{" + "fullName=" + fullName + ", email=" + email + ", password=" + password + ", nickname=" + nickname + ", birthdate=" + birthdate + '}';
    }
    
}
