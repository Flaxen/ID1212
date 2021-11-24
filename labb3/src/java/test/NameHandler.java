/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Flaxen
 */
public class NameHandler {
    private String name;
    private String userID;
    
    public NameHandler() {
        name = null;
        userID = null;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name + " from java";
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return this.userID = userID + ", id fetched from db using java";
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
}