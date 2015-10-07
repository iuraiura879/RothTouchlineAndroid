package com.roth.touchline;

import java.io.Serializable;

/**
 * Created by iuragutu on 25/09/15.
 */
public class Thermostat implements Serializable{

    int ID;
    String name;

    float currentTemperature;
    float operatingTemperature;

    int mode = 0;
    int modeSecondary = 0;

    private int controllerID;

    public Thermostat(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOperatingTemperature(float operatingTemperature) {
        this.operatingTemperature = operatingTemperature;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public float getOperatingTemperature() {
        return operatingTemperature;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public int getMode() {
        return mode;
    }

    public int getModeSecondary() {
        return modeSecondary;
    }

    public void setModeSecondary(int modeSecondary) {
        this.modeSecondary = modeSecondary;
    }

    public int getControllerID() {
        return controllerID;
    }

    public void setControllerID(int controllerID) {
        this.controllerID = controllerID;
    }
}
