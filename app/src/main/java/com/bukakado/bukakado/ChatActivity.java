package com.bukakado.bukakado;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bukakado.bukakado.constant.VariableKeys;
import com.bukakado.bukakado.model.NewChatRequest;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by edy.h on 5/11/2017.
 */

public class ChatActivity extends AppCompatActivity {

    private FirebaseListAdapter<NewChatRequest> adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference chatDbReference;
    private String toUserId;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        toUserId = getIntent().getExtras().getString(VariableKeys.CHAT_TO_USER);
        setContentView(R.layout.layout);
        chatDbReference = FirebaseDatabase.getInstance().getReference("chats");
        mAuth = FirebaseAuth.getInstance();

        ImageButton sendBtn = (ImageButton) findViewById(R.id.sendMessageButton);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.messageEditText);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                chatDbReference.push().setValue(
                                new NewChatRequest(
                                        input.getText().toString(),
                                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                        toUserId
                                )
                        );

                // Clear the input
                input.setText("");
            }
        });
        displayChatMessage();
    }

    private void displayChatMessage() {
        ListView listOfMessages = (ListView)findViewById(R.id.msgListView);
        ArrayAdapter<R.layout.>

        adapter = new FirebaseListAdapter<NewChatRequest>(this, NewChatRequest.class, R.layout.message_layout, FirebaseDatabase.getInstance().getReference("chats")) {
            @Override
            protected void populateView(View v, NewChatRequest model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setGravity(Gravity.END);
                messageText.setText(model.getMessage());
                messageUser.setGravity(Gravity.END);
                messageUser.setText(model.getTo());
            }
        };

        listOfMessages.setAdapter(adapter);
    }
}
