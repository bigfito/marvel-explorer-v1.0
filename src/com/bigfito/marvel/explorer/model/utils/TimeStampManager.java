/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigfito.marvel.explorer.model.utils;

import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * @author aorozco
 */
public class TimeStampManager {
    
    private Timestamp ts;
    private long tsCovertedToLong;

    public TimeStampManager(){
    }

    public void setTimeStamp(){
        this.ts = new Timestamp( System.currentTimeMillis() );
        this.tsCovertedToLong = ts.getTime();
    }

    public String getTimeStamp(){
        return String.valueOf( tsCovertedToLong );
    }
    
}
