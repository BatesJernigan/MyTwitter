/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Date;

/**
 *
 * @author batesjernigan
 */
public class TwitView {
    long userId, twitId, mentionedUser;
    String content, fullName, nickname, email;
    Date postedDate;

    public TwitView(long userId, long twitId, long mentionedUser, String content, String fullName, String nickname, String email, Date postedDate) {
        this.userId = userId;
        this.twitId = twitId;
        this.mentionedUser = mentionedUser;
        this.content = content;
        this.fullName = fullName;
        this.nickname = nickname;
        this.email = email;
        this.postedDate = postedDate;
    }

    @Override
    public String toString() {
        return "TwitView{" + "userId=" + userId + ", twitId=" + twitId + ", mentionedUser=" + mentionedUser + ", content=" + content + ", fullName=" + fullName + ", nickname=" + nickname + ", email=" + email + ", postedDate=" + postedDate + '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTwitId() {
        return twitId;
    }

    public void setTwitId(long twitId) {
        this.twitId = twitId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public long getMentionedUser() {
        return mentionedUser;
    }

    public void setMentionedUser(long mentionedUser) {
        this.mentionedUser = mentionedUser;
    }
    
}
