package de.github.GSGJ.database.entities;

import java.util.ArrayList;

/**
 * Created by claudio on 19.06.17.
 */
public class Usergroup {

    private int id;
    private String name;
    private ArrayList<User> users;
    private User owner;

    public Usergroup(int id, String name, ArrayList<User> users, User owner) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.owner = owner;
    }

    public Usergroup() {
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode(){
        //TODO implement hash code for maps
        return name.hashCode() + id;
    }

    @Override
    public boolean equals(Object obj){
        //TODO
        return super.equals(obj);
    }
}
