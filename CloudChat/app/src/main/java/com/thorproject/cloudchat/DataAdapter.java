package com.thorproject.cloudchat;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<ViewHolder> {

   ArrayList<MyMessage> messages;

   LayoutInflater layoutInflater;
   View view;
   int id;
   ViewGroup parent;

    public DataAdapter(Context context, ArrayList<MyMessage> messages, int id) {
        this.messages = messages;
        this.layoutInflater = LayoutInflater.from(context);
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        view = layoutInflater.inflate(R.layout.item_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(messages != null){
        String msg = messages.get(position).getMessage();
        String nick = messages.get(position).getNickname();
        String date = messages.get(position).getDate();

        holder.message.setText(msg);
        holder.nick.setText(nick);
        holder.date.setText(date);
            Log.d("TAG", "my nick is  " + nick);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
