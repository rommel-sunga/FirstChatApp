package com.example.rommelsunga.firstchatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /**
     * Opens Send Message to Open Channel Activity
     */
    public void openChannelSendMessage(View view) {
        Intent intent = new Intent(this, OpenChannelSendMessageActivity.class);
        startActivity(intent);
    }

    /**
     * Opens Open Channel List Activity
     */
    public void openChannelListView(View view) {
        Intent intent = new Intent(this, OpenChannelListViewActivity.class);
        startActivity(intent);
    }
}
