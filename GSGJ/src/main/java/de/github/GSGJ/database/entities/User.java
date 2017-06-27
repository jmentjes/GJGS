package de.github.GSGJ.database.entities;

import java.util.ArrayList;

/**
 * Created by claudio on 19.06.17.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Usergroup> groups;

    public User(int id, String name, String email, String password, ArrayList<Usergroup> groups) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.groups = groups;
    }

    public User(String name, String email, String password, ArrayList<Usergroup> groups) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.groups = groups;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Usergroup> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Usergroup> groups) {
        this.groups = groups;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode(){
        return id;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof User){
            User user = (User) obj;
            return user.getId() == this.getId();
        }
        return super.equals(obj);
    }
}
