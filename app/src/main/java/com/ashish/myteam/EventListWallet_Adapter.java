package com.ashish.myteam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventListWallet_Adapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Events_Card> eventCard;

    //constructor initializing the values
    public EventListWallet_Adapter(Context context, int resource, ArrayList<Events_Card> eventCardDetail) {
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
        ImageView contriImg;
        TextView eventName,teamName,eventDesc,eventDate,TotalContri, SpentContri, RemainingContri,eventMemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EventListWallet_Adapter.ViewHolder holder = new EventListWallet_Adapter.ViewHolder();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layout, null, false);
        holder.contriImg = view.findViewById(R.id.contriImg);
        holder.eventName = view.findViewById(R.id.eventTextView7);
        holder.teamName = view.findViewById(R.id.eventCardTeam);
        holder.eventDesc = view.findViewById(R.id.eventCardDesc);
        holder.eventDate = view.findViewById(R.id.EventtextView6);
        holder.TotalContri = view.findViewById(R.id.TotalContri);
        holder.SpentContri = view.findViewById(R.id.SpentContri);
        holder.RemainingContri = view.findViewById(R.id.RemainingContri);
        holder.eventMemList = view.findViewById(R.id.eventMemList);
        final Events_Card card = eventCard.get(position);

        holder.eventName.setText(card.getEventName());
        holder.teamName.setText(card.getTeamName());
        holder.eventDesc.setText(card.getEventDesc());
        holder.eventDate.setText("Date: "+card.getEventDate());
        holder.TotalContri.setText("Total: "+card.getContriAmt());
        holder.SpentContri.setText("Spent: "+card.getSpentAmt());
        holder.RemainingContri.setText("Remaining: "+card.getRemAmt());

        if(card.getEventContri().equals("true"))
        {
            holder.contriImg.setImageResource(R.drawable.tick);
        }
        else {
            holder.contriImg.setImageResource(R.drawable.cancel);
        }

        holder.eventMemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent memList = new Intent(context,EventMemberList.class);
                memList.putExtra("members",new String[]{card.getEventMembers(),card.getCreditMemb()});
                context.startActivity(memList);
            }
        });
        return view;
    }
}

