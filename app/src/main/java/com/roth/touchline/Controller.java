package com.roth.touchline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iuragutu on 25/09/15.
 */
public class Controller implements Serializable{

    int ID;

    String name;
    String stringID;
    String password;

    boolean checked = true;
    boolean connected = false;

    int mode = 0;

    ArrayList<Thermostat> thermostats;

    public Controller(){
        thermostats = new ArrayList<Thermostat>();
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setChecked(boolean checked) {

        this.checked = checked;

    }

    public boolean getChecked() {
        return checked;
    }

    public boolean getConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setStringID(String stringID) {
        this.stringID = stringID;
    }

    public String getStringID() {
        return stringID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void addThermostat(Thermostat tempThermostat) {
        tempThermostat.setControllerID( ID );
        thermostats.add(tempThermostat);
    }

    public int getNumberOfThermostats() {
        return thermostats.size();
    }

    public Thermostat getThermostat(int i) {
        return thermostats.get(i);
    }

    public void setThermostatList(ArrayList<Thermostat> thermostatList) {
        this.thermostats = thermostatList;
    }

    public Thermostat findThermostatByID(int id) {

        for(int i=0; i < thermostats.size(); i++)
            if(thermostats.get(i).getID() == id )
                return thermostats.get(i);

        return null;
    }



}
