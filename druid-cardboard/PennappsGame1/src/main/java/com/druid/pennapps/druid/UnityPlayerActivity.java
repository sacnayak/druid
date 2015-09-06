package com.druid.pennapps.druid;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;
import com.unity3d.player.*;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.thalmic.myo.AbstractDeviceListener;

public class UnityPlayerActivity extends Activity
{
	protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
	public static String getPitch()
	{
		return (PITCH + "");
	}

	public static String getLevel()
	{
		return ("" + 3);
	}
	public static String getScaleFactor()
	{
		return ("" + 0.01);
	}

	static float PITCH = 0.0f;
	private DeviceListener mListener = new AbstractDeviceListener() {
		@Override
		public void onConnect(Myo myo, long timestamp) {
			Toast.makeText(UnityPlayerActivity.this, "Myo Connected!", Toast.LENGTH_SHORT).show();

		}



		@Override
		public void onDisconnect(Myo myo, long timestamp) {
			Toast.makeText(UnityPlayerActivity.this, "Myo Disconnected!", Toast.LENGTH_SHORT).show();
		}

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
				//pitch *= -1;
			}
			PITCH = pitch;
			Log.d("PITCH data", PITCH + "");
		}

	};
	// Setup activity layout
	@Override protected void onCreate (Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		Hub hub = Hub.getInstance();
		if (!hub.init(this, getPackageName())) {
			// We can't do anything with the Myo device if the Hub can't be initialized, so exit.
			Toast.makeText(this, "Couldn't initialize Hub", Toast.LENGTH_SHORT).show();
		}
		else {
			// Next, register for DeviceListener callbacks.
			hub.addListener(mListener);
		}
		getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

		mUnityPlayer = new UnityPlayer(this);
		if (mUnityPlayer.getSettings().getBoolean("hide_status_bar", true))
		{
			getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
			                       WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

		setContentView(mUnityPlayer);
		mUnityPlayer.requestFocus();
	}

	// Quit Unity
	@Override protected void onDestroy ()
	{
		mUnityPlayer.quit();
		super.onDestroy();
	}

	// Pause Unity
	@Override protected void onPause()
	{
		super.onPause();
		mUnityPlayer.pause();
	}

	// Resume Unity
	@Override protected void onResume()
	{
		super.onResume();
		mUnityPlayer.resume();
	}

	// This ensures the layout will be correct.
	@Override public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mUnityPlayer.configurationChanged(newConfig);
	}

	// Notify Unity of the focus change.
	@Override public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
	}

	// For some reason the multiple keyevent type is not supported by the ndk.
	// Force event injection by overriding dispatchKeyEvent().
	@Override public boolean dispatchKeyEvent(KeyEvent event)
	{
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.injectEvent(event);
		return super.dispatchKeyEvent(event);
	}

	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
	/*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
