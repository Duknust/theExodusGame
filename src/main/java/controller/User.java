package controller;

import java.util.List;

/**
 * Created by duarteduarte on 19/02/15.
 */
public class User {
    String username = null;
    int points = 0;
    String stage = null;
    String status = null;

    public User(String username, int points, String stage, String status) {
        this.username = username;
        this.points = points;
        this.stage = stage;
        this.status = status;
    }

    public String getUsername() {return username;}
    public int getPoints() {return points;}
    public String getStage() {return stage;}
    public String getStatus() {return status;}

    public void setUsername(String username) {this.username=username;}
    public void setPoints(int points) {this.points = points;}
    public void setStage(String stage) {this.stage = stage;}
    public void setStatus(String status) {this.status = status;}
}
