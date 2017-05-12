package com.bukakado.bukakado;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bukakado.bukakado.model.NewChatRequest;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by edy.h on 5/11/2017.
 */

public class ChatActivity extends AppCompatActivity {

    private FirebaseListAdapter<NewChatRequest> adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.layout);

        ImageButton sendBtn = (ImageButton) findViewById(R.id.sendMessageButton);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.messageEditText);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference("chats")
                        .push()
                        .setValue(
                                new NewChatRequest(
                                        input.getText().toString(),
                                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                        "vo1yv0rudgWoj3Lc2Y20OTTBpiP2"
                                )
                        );

                // Clear the input
                input.setText("");
            }
        });

        mAuth = FirebaseAuth.getInstance();
        displayChatMessage();
    }

    private void displayChatMessage() {
        ListView listOfMessages = (ListView)findViewById(R.id.msgListView);

        adapter = new FirebaseListAdapter<NewChatRequest>(this, NewChatRequest.class,
                R.layout.message_layout, FirebaseDatabase.getInstance().getReference("chats")) {
            @Override
            protected void populateView(View v, NewChatRequest model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessage());
                messageUser.setText(model.getTo());
            }
        };

        listOfMessages.setAdapter(adapter);
    }
}
