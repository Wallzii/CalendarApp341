package com.example.conno.calendarapp341;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public class Event implements Comparable{

    // Format for data.txt that holds event information.
    // Year, Month, dayOfMonth, StartTime,EndTime,Tag,Title,Description,Location
    private GregorianCalendar date = new GregorianCalendar();
    private String startHour = "";
    private String startMin;
    private String endTime = "";
    private String TAG = "";
    private String eventName = "";
    private String desc = "";
    private String location = "";

    public Event(String [] e){
        date = new GregorianCalendar(Integer.parseInt(e[0]), Integer.parseInt(e[1]) - 1, Integer.parseInt(e[2]));
        startHour = e[3];
        startMin = e[4];
        endTime = e[5];
        TAG = e[6];
        eventName = e[7];
        desc = e[8];
        location = e[9];
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Event otherEvent = (Event) o;
        GregorianCalendar thisD = this.getDate();
        GregorianCalendar otherD = otherEvent.getDate();
        int result;
        if(thisD.get(Calendar.YEAR)==otherD.get(Calendar.YEAR)){
            if(thisD.get(Calendar.MONTH)==otherD.get(Calendar.MONTH)){
                if (thisD.get(Calendar.DAY_OF_MONTH)==thisD.get(Calendar.DAY_OF_MONTH)){
                    int thisHour,thatHour,thisMinute,thatMinute;
                    thisHour=Integer.parseInt(this.startHour);
                    thatHour=Integer.parseInt(otherEvent.getStartHour());
                    thisMinute=Integer.parseInt(this.startHour);
                    thatMinute=Integer.parseInt(otherEvent.getStartHour());
                    if(thatHour==thatHour){
                        if(thisMinute==thatMinute){
                            result=0;
                        }
                        else if(thisMinute<thatMinute){
                            result = -1;
                        } else result = 1;
                    }
                    else if(thisHour<thatHour){
                        result = -1;
                    }
                    else result = 1;
                }
                else if(thisD.get(Calendar.DAY_OF_MONTH)<thisD.get(Calendar.DAY_OF_MONTH)){
                    result=-1;
                }
                else result = 1;
            }
            else if(thisD.get(Calendar.MONTH)==thisD.get(Calendar.MONTH)){
                result=-1;
            }
            else result=1;
        } else if(thisD.get(Calendar.DAY_OF_MONTH)<thisD.get(Calendar.DAY_OF_MONTH)){
            result=-1;
        }else result = 1;
        return result;
    }


    //Setters and getters
    public GregorianCalendar getDate() {
        return date;
    }
    public String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd");
        return dateFormat.format(date.getTime());
    }
    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
    public String getStartHour() {
        return startHour;
    }
    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }
    public String getStartMin() {
        return startMin;
    }
    public void setStartMin(String startMin) {
        this.startMin = startMin;
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

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String testDate = dateFormat.format(date.getTime());
        return "Event{" +
                "date=" + testDate +
                "', startHour='" + startHour +
                "', startMin='" + startMin +
                "', endTime='" + endTime +
                "', TAG='" + TAG +
                "', eventName='" + eventName +
                "', desc='" + desc +
                "', location='" + location +
                '}';
    }
}
