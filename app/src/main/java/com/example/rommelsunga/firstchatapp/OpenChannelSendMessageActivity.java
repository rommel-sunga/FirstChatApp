package com.example.rommelsunga.firstchatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

import com.sendbird.android.BaseChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserMessage;
import com.sendbird.android.OpenChannel;

public class OpenChannelSendMessageActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String APP_ID = "4513ED93-B71C-4056-9FD6-78E44E4AD8C8"; // Rommel's Dev Test Application US-2
    private static final String USER_ID = "test_user_id_1";
    private static final String CHANNEL_NAME = "channel_1";
    private static final String CHANNEL_URL = "sendbird_open_channel_154_2bd5ffa68bc45cfde7ff27eaec03bb9ee61c42ed";
    private OpenChannel mChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_channel_send_message);

        SendBird.init(APP_ID, getApplicationContext());
        login(USER_ID, new SendBird.ConnectHandler(){
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });
    }

    public static void login(String userId, final SendBird.ConnectHandler handler) {
        SendBird.connect(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User userId, SendBirdException e) {
                Log.d("debug", "Connected");
                if (e != null) {    // Error.
                    return;
                }
            }
        });
    }

    private void createOpenChannel(String name) {
        OpenChannel.createChannelWithOperatorUserIds(name, null, null, null, new OpenChannel.OpenChannelCreateHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {
                    // Error!
                    return;
                }
            }
        });
    }

    public void sendMessageToChannel(final String message1) {
        Log.d("debug", "getChannel Called");

        OpenChannel.getChannel(CHANNEL_URL, new OpenChannel.OpenChannelGetHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                Log.d("debug", "getChannel Called inside onResult");
                if (e != null) {
                    Log.d("debug", "Error");

                    // Error!
                    return;
                }

                openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                    @Override
                    public void onResult(SendBirdException e) {
                        Log.d("debug", "openChannel.enter");

                        if (e != null) {    // Error.
                            Log.d("debug", "error encountered ");

                            return;
                        }
                    }
                });

                openChannel.sendUserMessage(message1, new BaseChannel.SendUserMessageHandler() {
                    @Override
                    public void onSent(UserMessage userMessage, SendBirdException e) {
                        Log.d("debug", "sendUserMessage");

                        if (e != null) {
                            Log.d("debug", "error encountered ");

                            // Error!
                            return;
                        }

                    }
                });
            }
        });
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Log.d("debug", "SendMessage Called");
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        // Display sent message to RecyclerView
        intent.putExtra(EXTRA_MESSAGE, message);
        createOpenChannel(CHANNEL_NAME);
        sendMessageToChannel(message);
        startActivity(intent);
    }
}
