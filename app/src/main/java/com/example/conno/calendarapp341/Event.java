package com.example.conno.calendarapp341;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public class Event implements Comparable{

    // Format for data.txt that holds event information.
    // Year, Month, dayOfMonth, StartTime,EndTime,Tag,Title,Description,Location
    private GregorianCalendar date = new GregorianCalendar();
    private String endTime = "";
    private String TAG = "";
    private String eventName = "";
    private String desc = "";
    private String location = "";

    public Event(String [] e){
        date = new GregorianCalendar(Integer.parseInt(e[0]), Integer.parseInt(e[1]), Integer.parseInt(e[2]), Integer.parseInt(e[3]),Integer.parseInt(e[4]));
        TAG = e[5];
        eventName = e[6];
        desc = e[7];
        location = e[8];
    }




    @Override
    public int compareTo(@NonNull Object o) {
        Event otherEvent = (Event) o;
        GregorianCalendar thisD = this.getDate();
        GregorianCalendar otherD = otherEvent.getDate();
        int result = thisD.compareTo(otherD);

        return result;
    }


    //Setters and getters
    public GregorianCalendar getDate() {
        return date;
    }
    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getTAG() {
        return TAG;
    }
    public void setTAG(String TAG) {
        this.TAG = TAG;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
