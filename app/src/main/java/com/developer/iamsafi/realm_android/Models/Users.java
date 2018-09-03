package com.developer.iamsafi.realm_android.Models;

import android.support.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Users extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String Password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword( String password) {
        Password = password;
    }
}

