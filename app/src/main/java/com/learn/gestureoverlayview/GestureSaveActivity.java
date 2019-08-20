package com.learn.gestureoverlayview;

import androidx.appcompat.app.AppCompatActivity;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class GestureSaveActivity extends AppCompatActivity {

    private Gesture mGesture;
    private Button mSave;
    private static final float LENGTH_THRESHOLD = 120.0f;
    public TextView input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gesture_save);

        mSave = findViewById(R.id.gesture_save_bt);

        GestureOverlayView overlay = findViewById(R.id.gestures_input);
        overlay.addOnGestureListener(new GesturesProcessor());

        input = findViewById(R.id.gesture_name);
        input.setText(getIntent().getStringExtra("name"));
    }

    /**
     * 增加手势的按钮
     */
    public void addGesture() {
        if (mGesture != null) {
            CharSequence name = input.getText();
            if (name.length() == 0) {
                input.setError("输入gesture的名字！");
                return;
            }
            // 把手势增加到手势库
            GestureHelp.getInstance().saveGesture(String.valueOf(name), mGesture);
            setResult(RESULT_OK);
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    /**
     * 取消手势
     */
    public void cancelGesture() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.gesture_save_bt:
                addGesture();
                break;
            case R.id.gesture_cancle_bt:
                cancelGesture();
                break;
        }
    }

    /**
     * 手势监听着
     *
     * @author Administrator
     */
    private class GesturesProcessor implements
            GestureOverlayView.OnGestureListener {

        @Override
        public void onGestureStarted(GestureOverlayView overlay,
                                     MotionEvent event) {
            mSave.setEnabled(false);
            mGesture = null;
        }

        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
        }

        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            // 获取在GestureOverlayView手势
            mGesture = overlay.getGesture();                                        // 注2
            if (mGesture.getLength() < LENGTH_THRESHOLD) {
                overlay.clear(false);
            }
            mSave.setEnabled(true);
        }

        public void onGestureCancelled(GestureOverlayView overlay,
                                       MotionEvent event) {
        }
    }
}
