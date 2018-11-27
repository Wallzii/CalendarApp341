package com.example.conno.calendarapp341;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.conno.calendarapp341.Event;
import com.example.conno.calendarapp341.R;


public class EventAdapter extends ArrayAdapter {

    //private ArrayList<Event> eventSet;
    private Event[] eventSet;
    Context myContext;

    public EventAdapter(Context context, Event[] events) {
        super(context, R.layout.item_event, events);
        this.eventSet=events;
        this.myContext=context;
    }

    //View lookup
    private static class ViewHolder{
        TextView etitle;
        TextView etime;
    }

    //public void onClick(View v){
    //    System.out.print("CLICKCLICKCLICK");
    //    //TODO
    //}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Event event = eventSet[position];

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, null);
            viewHolder.etitle= convertView.findViewById(R.id.list_event_name);
            viewHolder.etime= convertView.findViewById(R.id.list_event_time);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
            result=convertView;
        }

        // Populate the data into the template view
        String times = event.getDate().getTime().toString() + " - " + event.getEndTime();
        viewHolder.etitle.setText(event.getEventName());
        viewHolder.etime.setText(times);

        //TODO FORMAT TIME STRING PROPERLY
        //TODO viewHolder.LinearLayout onclick

        // Return the completed view to render on screen
        return convertView;
    }
}
