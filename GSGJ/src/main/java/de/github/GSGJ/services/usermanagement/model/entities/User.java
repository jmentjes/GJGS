package de.github.GSGJ.services.usermanagement.model.entities;

import java.util.ArrayList;

/**
 * Created by claudio on 19.06.17.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private ArrayList<Usergroup> groups;

    public User(int id, String name, String email, ArrayList<Usergroup> groups) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.groups = groups;
    }

    public User(String name, String email, ArrayList<Usergroup> groups) {
        this.id = -1;
        this.name = name;
        this.email = email;
        this.groups = groups;
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
}
