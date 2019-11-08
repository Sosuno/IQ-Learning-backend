package com.iqlearning.database.entities;
/*****
 * Author: Ewa Jasinska
 */

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;


/****
Return codes:
User id = -1 --> User doesn't exists
User = null  --> db is fucked

 Status legend:
    0 - inactive
    1 - blocked
    2 - active
*/
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;
    @Column(name = "login_tries", nullable = false)
    private int loginTries;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "bio")
    private String bio;
    @Column(name = "linkedin")
    private String linkedIn;
    @Column(name = "twitter")
    private String twitter;
    @Column(name = "reddit")
    private String reddit;
    @Column(name = "youtube")
    private String youtube;

    public User() {
    }

    public User(String name, String surname, String username, String password, String email, int status, Timestamp creationTime,int loginTries) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.creationTime = creationTime;
        this.loginTries = loginTries;
    }

    public User(String name, String surname, String username, String password, String email, int status, Timestamp creationTime, int loginTries, String avatar, String bio, String linkedIn, String twitter, String reddit, String youtube) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.creationTime = creationTime;
        this.loginTries = loginTries;
        this.avatar = avatar;
        this.bio = bio;
        this.linkedIn = linkedIn;
        this.twitter = twitter;
        this.reddit = reddit;
        this.youtube = youtube;
    }

    public User(boolean exists){
        if(!exists) {
            this.id = (long) -1;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public int getLoginTries() {
        return loginTries;
    }

    public void setLoginTries(int loginTries) {
        this.loginTries = loginTries;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getReddit() {
        return reddit;
    }

    public void setReddit(String reddit) {
        this.reddit = reddit;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
