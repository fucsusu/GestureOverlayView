package com.learn.gestureoverlayview;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GestureFindActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {
    public static final String TAG = "GestureFindActivity";
    GestureOverlayView gesturesView;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_find);

        gesturesView = findViewById(R.id.gestures);
        gesturesView.addOnGesturePerformedListener(this);
        txt = findViewById(R.id.textView1);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList predictions = GestureHelp.getInstance().getFindResult(gesture);
        Log.e(TAG, "onGesturePerformed: " + predictions.size());
        if (predictions.size() > 0) {
            Prediction prediction = (Prediction) predictions.get(0);
            Log.e(TAG, "onGesturePerformed: " + prediction.score);
            if (prediction.score > 2.0) {
                Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT).show();
                txt.append(prediction.name);
            } else {
                Toast.makeText(this, "未识别出！！！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
