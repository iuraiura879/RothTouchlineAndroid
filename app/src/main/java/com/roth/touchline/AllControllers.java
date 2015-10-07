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

public class AllControllers implements Serializable {

    //Controllers stored ere
    ArrayList<Controller> controllers;

    // Constant with a file name
    public static String fileName = "allControllers.roth";


    public AllControllers(){
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
    public static AllControllers readFromFile(Context context) {


        AllControllers allControllers = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            allControllers = (AllControllers) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allControllers;
    }

    public void addController( Controller contr ){

        controllers.add(contr);

    }

    public Controller getController( int index ){

        return controllers.get(index);
    }

    public int getNumberOfControllers(){

        return controllers.size();

    }

    public Controller findControllerByID(int id) {

        for(int i=0; i < controllers.size(); i++)
            if(controllers.get(i).getID() == id )
                return controllers.get(i);

        return null;
    }

    public void deleteController(int id) {

        for(int i=0; i < controllers.size(); i++)
            if(controllers.get(i).getID() == id )
                controllers.remove(i);

    }


}
