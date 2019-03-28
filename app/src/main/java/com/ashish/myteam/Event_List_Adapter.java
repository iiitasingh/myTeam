package com.ashish.myteam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Event_List_Adapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Events_Card> eventCard;

    //constructor initializing the values
    public Event_List_Adapter(Context context, int resource, ArrayList<Events_Card> eventCardDetail) {
        this.context = context;
        this.layout = resource;
        this.eventCard = eventCardDetail;
    }

    @Override
    public int getCount() {
        return eventCard.size();
    }

    @Override
    public Object getItem(int position) {
        return eventCard.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView eventOwnerImg;
        TextView eventName,teamName,eventDesc,eventDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event_List_Adapter.ViewHolder holder = new Event_List_Adapter.ViewHolder();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layout, null, false);
        //holder.eventOwnerImg = view.findViewById(R.id.EventOwnerImg);
        holder.eventName = view.findViewById(R.id.eventTextView7);
        holder.teamName = view.findViewById(R.id.eventCardTeam);
        holder.eventDesc = view.findViewById(R.id.eventCardDesc);
        holder.eventDate = view.findViewById(R.id.EventtextView6);
        Events_Card card = eventCard.get(position);

        holder.eventName.setText(card.getEventName());
        holder.teamName.setText(card.getTeamName());
        holder.eventDesc.setText(card.getEventDesc());
        holder.eventDate.setText("Validity: "+card.getEventDate());
        //[] foodImage = card.getOwnerImg();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        //holder.eventOwnerImg.setImageBitmap(bitmap);
        return view;
    }
}
