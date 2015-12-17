/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @javabean for User Entity
 */
public class User implements Serializable {
    private long id;
    private Date birthdate, lastlogin;
    private String fullName, email, password, nickname, profilePicture, passwordSalt;

    public User() {}

    public User(String fullName, String email, String password, String nickname, Date birthdate, String profilePicture, String passwordSalt) {
        this.id = new Random().nextInt(1000000000);
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.profilePicture = profilePicture;
        this.passwordSalt = passwordSalt;
    }

    public User(long id, String fullName, String email, String password, String nickname, Date birthdate, Date lastlogin, String profilePicture, String passwordSalt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.lastlogin = lastlogin;
        this.profilePicture = profilePicture;
        this.passwordSalt = passwordSalt;
    }
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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

    public Date getLastLogin(){
//        try {
//            return new SimpleDateFormat("HH:mm:ss DD-MM-YYYY").parse("00:00:00 "+ lastlogin.getMonth() + "-"+ lastlogin.getDate() + "-" + lastlogin.getYear());
//        } catch (ParseException ex) {
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
        return lastlogin;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", fullName=" + fullName + ", email=" + email + ", password=" + password + ", nickname=" + nickname + ", profilePicture=" + profilePicture + ", passwordSalt=" + passwordSalt + ", birthdate=" + birthdate + '}';
    }
    
}
