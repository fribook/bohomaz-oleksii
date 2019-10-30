package com.thorproject.cloudchat;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView message, nick, date;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.message_item);
        nick = itemView.findViewById(R.id.nick);
        date = itemView.findViewById(R.id.date);

    }
}
