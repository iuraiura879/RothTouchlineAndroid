package com.roth.touchline;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iuragutu on 04/10/15.
 */
public class User implements Serializable{

    int id;

    String userID;
    String passwordHash;

    String name = "";
    String email = "";
    String mobileNr;
    int language;

    ArrayList<Controller> controllers;

    // Constant with a file name
    public static String fileName = "user.roth";

    public User(){
        controllers = new ArrayList<Controller>();
    }

    // Serializes an object and saves it to a file
    public void saveToFile(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Creates an object by reading it from a file
    public static User readFromFile(Context context) {
        User user = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            user = (User) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNr(String mobileNr) {
        this.mobileNr = mobileNr;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public Controller getController( int index ){

        return controllers.get(index);
    }

    public int getNumberOfControllers(){

        return controllers.size();

    }

    public void addController(Controller controller) {

        controllers.add(controller);

    }

    public Controller findControllerByID(int id) {

        for(int i=0; i < controllers.size(); i++)
            if(controllers.get(i).getID() == id )
                return controllers.get(i);

        return null;
    }

    public void deleteControllerWithID(int id) {

        for(int i=0; i < controllers.size(); i++)
            if(controllers.get(i).getID() == id )
                 controllers.remove(i);
    }


    public void replaceThermostatListForControllerID(int id, ArrayList<Thermostat> thermostats) {

        findControllerByID(id).setThermostatList(thermostats);
    }

    public String getName() {
        return name;
    }


    public int getLanguage() {
        return language;
    }


    public String getEmail() {
        return email;
    }
}
