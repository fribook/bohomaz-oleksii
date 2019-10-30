package com.thorproject.cloudchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static int MAX_MESSAGE_LENGTH = 100;

    DatabaseReference database;

    EditText mEditTextMessage;
    Button  mSendButton;
    RecyclerView mMessagesRecycler;
    Button dialogButtonOk, dialogButtonCancel;
    SharedPreferences sPref;
    String nickname;
    int personalId;
    final String NICKNAME = "nickname";
    final String PERSONALID = "personalId";
    private static final int NOTIFY_ID = 101;

    ArrayList<MyMessage> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPref = getPreferences(MODE_PRIVATE);
        nickname = sPref.getString(NICKNAME, "");
        personalId = sPref.getInt(PERSONALID, 0);
        if(personalId == 0){
            personalId = new Random().nextInt();
        }
        if (nickname == "") {
            showDialog();
        }
        Log.d("TAG", "nick is " + nickname);

        mEditTextMessage = findViewById(R.id.message_input);
        mSendButton = findViewById(R.id.send_msg_btn);
        mMessagesRecycler = findViewById(R.id.msg_recycler);

        mMessagesRecycler.setLayoutManager(new LinearLayoutManager(this));

        final DataAdapter dataAdapter = new DataAdapter(this, messages, personalId);
        mMessagesRecycler.setAdapter(dataAdapter);


        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer num = new Random().nextInt();
                database = FirebaseDatabase.getInstance().getReference().child("Message")
                        .child("Message" + num);
                String msg = mEditTextMessage.getText().toString();

                if(msg.equals("")){
                    Toast.makeText(getApplicationContext(), "Введите сообщение!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(msg.length() > MAX_MESSAGE_LENGTH){
                    Toast.makeText(getApplicationContext(), "Слишком длинное сообщение!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(nickname != "") {
                    DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    MyMessage m = new MyMessage(msg, nickname, personalId, date);
                    database.getRef().setValue(m);
                    mEditTextMessage.setText("");
                }else{
                    showDialog();
                }


            }
        });

          database = FirebaseDatabase.getInstance().getReference().child("Message");


        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MyMessage p = dataSnapshot.getValue(MyMessage.class);
//                if(p.getNickname() != null){
                messages.add(p);
                Log.d("TAG", "1 my nick is  " + p.getNickname() + "  " + p.getMessage());
                dataAdapter.notifyDataSetChanged();
                mMessagesRecycler.smoothScrollToPosition(messages.size());
            }
//            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        final EditText editText = dialog.findViewById(R.id.editTextDialog);

        dialogButtonOk = (Button) dialog.findViewById(R.id.btnOk);
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString() != ""){
                    nickname = editText.getText().toString();
                }
                sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(NICKNAME, nickname);
                ed.putInt(PERSONALID, personalId);
                ed.commit();
                dialog.dismiss();
            }
        });

        dialogButtonCancel = (Button) dialog.findViewById(R.id.btnCancel);
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(NICKNAME, nickname);
        ed.putInt(PERSONALID, personalId);
        ed.commit();
    }




}



