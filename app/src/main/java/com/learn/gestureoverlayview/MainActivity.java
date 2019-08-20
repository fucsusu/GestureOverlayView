package com.learn.gestureoverlayview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {


    private static final int STATUS_SUCCESS = 0;
    private static final int STATUS_CANCELLED = 1;
    private static final int STATUS_NO_STORAGE = 2;
    private static final int STATUS_NOT_LOADED = 3;
    private static final int REQUEST_NEW_GESTURE = 1;

    /**
     * 手势排序
     */
    private final Comparator<GesturesAdapter.NamedGesture> mSorter = new Comparator<GesturesAdapter.NamedGesture>() {
        public int compare(GesturesAdapter.NamedGesture object1, GesturesAdapter.NamedGesture object2) {
            return object1.name.compareTo(object2.name);
        }
    };

    ListView gesturelv;
    public GesturesAdapter mAdapter;

    // 宽度、高度
    private int mThumbnailSize;
    // 密度
    private int mThumbnailInset;
    // 颜色
    private int mPathColor;

    public GestureLibrary mGestureLib;

    public EditText gesture_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gesturelv = findViewById(R.id.gesture_lv);
        mAdapter = new GesturesAdapter(this);
        gesturelv.setAdapter(mAdapter);

        gesture_input = findViewById(R.id.gesture_input);

        mPathColor = getResources().getColor(R.color.gesture_color);
        mThumbnailInset = 2;
        mThumbnailSize = 300;

        mGestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        loadGestures();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_NEW_GESTURE:
                    loadGestures();
                    break;
            }
        }
    }

    //识别手势
    private void findGesture() {
        Intent intent = new Intent(this, GestureFindActivity.class);
        startActivity(intent);
    }

    //手势录入
    private void saveGesture() {
        String gesturename = gesture_input.getText().toString();
        if (!TextUtils.isEmpty(gesturename)) {
            Intent intent = new Intent(this, GestureSaveActivity.class);
            intent.putExtra("name", gesturename);
            startActivityForResult(intent, REQUEST_NEW_GESTURE);
        }
    }


    /**
     * 加载手势
     *
     * @return
     */
    private int loadGestures() {
        mAdapter.clear();
        for (String name : GestureHelp.getInstance().getmGestureLib().getGestureEntries()) {
            for (Gesture gesture : GestureHelp.getInstance().getmGestureLib().getGestures(name)) {
                final Bitmap bitmap = gesture.toBitmap(mThumbnailSize,
                        mThumbnailSize, mThumbnailInset, mPathColor);
                final GesturesAdapter.NamedGesture namedGesture = new GesturesAdapter.NamedGesture();
                namedGesture.gesture = gesture;
                namedGesture.name = name;

                mAdapter.addBitmap(namedGesture.gesture.getID(), bitmap);
                mAdapter.add(namedGesture);
            }
        }
        mAdapter.sort(mSorter);
        mAdapter.notifyDataSetChanged();
        return STATUS_SUCCESS;
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.gesture_save:
                saveGesture();
                break;
            case R.id.gesture_find:
                findGesture();
                break;
        }
    }
}
