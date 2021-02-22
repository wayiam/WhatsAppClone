package com.saksham.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatMainActivity extends AppCompatActivity {


   private static final String TAG = "ChatMainActivity";

    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;

    private DatabaseReference mDatabaseRefrence;
    private ChatListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        setDisplayName();
        mDatabaseRefrence = FirebaseDatabase.getInstance().getReference();


        //Linking the view in the layout to the java code

        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);


        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                sendMessage();
                return  true;
            }
        });

mSendButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        sendMessage();
    }
});

    }

    private void setDisplayName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDisplayName = user.getDisplayName();
    }


    private void sendMessage() {

        Log.d(TAG, "sendMessage: I sent something");
        // TODO: Grab the text the user typed in and push the message to Firebase
        String input = mInputText.getText().toString();
        if (!input.equals("")) {
            InstantMessage chat = new InstantMessage(input, mDisplayName);
            mDatabaseRefrence.child("messages").push().setValue(chat);
            mInputText.setText("");
        }
    }


        // TODO: Override the onStart() lifecycle method. Setup the adapter here.
        @Override
        public void onStart() {
            super.onStart();
            mAdapter = new ChatListAdapter(this, mDatabaseRefrence, mDisplayName);
            mChatListView.setAdapter(mAdapter);
        }


        @Override
        public void onStop() {
            super.onStop();

            // TODO: Remove the Firebase event listener on the adapter.
            mAdapter.cleanup();

        }
    }






