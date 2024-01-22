package com.leo.bsprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    public static ConnectedThread connectedThread;
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update

    MaterialCardView cardView, goEEG, goShoulders;

    static TextView eyeLabel, shouldersLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);

        eyeLabel = findViewById(R.id.eye_state_label);

        cardView = findViewById(R.id.goVoice);
        goEEG = findViewById(R.id.goEEG);
        goShoulders = findViewById(R.id.goShoulders);

        goEEG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, InfoActivityEEG.class);
                startActivity(intent);
            }
        });

        goShoulders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, info_activity_shoulders.class);
                startActivity(intent);
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, VoiceActivity.class);
                startActivity(intent);
            }
        });

        connectedThread = new ConnectedThread(BluetoothConnectionActivity.mmSocket);
        connectedThread.start();


    }


    /* ============================ Thread to Create Bluetooth Connection =================================== */
    public class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;


        ArrayList<String> eegList = new ArrayList<>();
        List<Object> shouldersList = new ArrayList<>();

        ShouldersListModel shouldersListModel = new ShouldersListModel();

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes = 0; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    /*
                    Read from the InputStream from Arduino until termination character is reached.
                    Then send the whole String message to GUI Handler.
                     */
                    buffer[bytes] = (byte) mmInStream.read();
                    String message_eeg;
                    String message_left_shoulder;
                    String read_right_shoulder;
                    String read_eye;


                    if (buffer[bytes] == ';'){
                        message_eeg = new String(buffer,0,bytes);
                        eegList.add(message_eeg);
                        Log.e("Arduino Message EEG",message_eeg);
                        BluetoothConnectionActivity.handler.obtainMessage(MESSAGE_READ,message_eeg).sendToTarget();
                        bytes = 0;
                    }
                    else if (buffer[bytes] == '&') {
                        message_left_shoulder = new String(buffer,0,bytes);
                        shouldersListModel.setLeftShoulders(message_left_shoulder);
                        Log.e("Arduino Message Left Shoulder",message_left_shoulder);
                        BluetoothConnectionActivity.handler.obtainMessage(MESSAGE_READ,message_left_shoulder).sendToTarget();
                        bytes = 0;
                    }
                    else if (buffer[bytes] == '$') {
                        read_right_shoulder = new String(buffer,0,bytes);
                        shouldersListModel.setRightShoulders(read_right_shoulder);
                        shouldersList.add(shouldersListModel);
                        Log.e("Arduino Message Right Shoulder",read_right_shoulder);
                        BluetoothConnectionActivity.handler.obtainMessage(MESSAGE_READ,read_right_shoulder).sendToTarget();
                        bytes = 0;
                    }
                    else if (buffer[bytes] == '@') {
                        read_eye = new String(buffer,0,bytes);
                        Log.e("Arduino Message EYE",read_eye);

                        BluetoothConnectionActivity.handler.obtainMessage(MESSAGE_READ,read_eye).sendToTarget();
                        bytes = 0;
                    }
                    else {
                        bytes++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes(); //converts entered String into bytes
            try {

                mmOutStream.write(bytes);

            } catch (IOException e) {
                Log.e("Send Error","Unable to send message",e);
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
}