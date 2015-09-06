package com.druid.pennapps.druid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.scanner.ScanActivity;

public class StartVRActivity extends AppCompatActivity {

    private DeviceListener mListener = new AbstractDeviceListener() {
        @Override
        public void onConnect(Myo myo, long timestamp) {
            Toast.makeText(StartVRActivity.this, "Myo Connected!", Toast.LENGTH_SHORT).show();
            Button scanbutton = (Button)findViewById(R.id.scanbutton);
            scanbutton.setVisibility(View.GONE);
            Button startVRButton = (Button) findViewById(R.id.startVR);
            startVRButton.setVisibility(View.VISIBLE);

            startVRButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(StartVRActivity.this, UnityPlayerActivity.class);
                    StartVRActivity.this.finish();
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onDisconnect(Myo myo, long timestamp) {
            Toast.makeText(StartVRActivity.this, "Myo Disconnected!", Toast.LENGTH_SHORT).show();
        }
/*
        // onOrientationData() is called whenever a Myo provides its current orientation,
        // represented as a quaternion.
        @Override
        public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
            // Calculate Euler angles (roll, pitch, and yaw) from the quaternion.
            float roll = (float) Math.toDegrees(Quaternion.roll(rotation));
            float pitch = (float) Math.toDegrees(Quaternion.pitch(rotation));
            float yaw = (float) Math.toDegrees(Quaternion.yaw(rotation));
            // Adjust roll and pitch for the orientation of the Myo on the arm.
            if (myo.getXDirection() == XDirection.TOWARD_ELBOW) {
                roll *= -1;
                pitch *= -1;
            }
            // Next, we apply a rotation to the text view using the roll, pitch, and yaw.
            //myoStatus = (TextView) findViewById(R.id.MyoStatus);
            //myoStatus.setRotation(roll);
            //myoStatus.setRotationX(pitch);
            //myoStatus.setRotationY(yaw);

        }
        */
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_vr);
        //Intent intent = getIntent();

        TextView greeting = (TextView) findViewById(R.id.GreetingText);
greeting.setText("Hello");
        //greeting.setText("Hello " + intent.getStringExtra("personName"));
        // First, we initialize the Hub singleton with an application identifier.
        Hub hub = Hub.getInstance();
        if (!hub.init(this, getPackageName())) {
            // We can't do anything with the Myo device if the Hub can't be initialized, so exit.
            Toast.makeText(this, "Couldn't initialize Hub", Toast.LENGTH_SHORT).show();
        }
        else {
            // Next, register for DeviceListener callbacks.
            hub.addListener(mListener);

            Button scanbutton = (Button) findViewById(R.id.scanbutton);
            scanbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(StartVRActivity.this, ScanActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
