package com.learn.gestureoverlayview;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by fucc
 * Date: 2019-08-20 14:50
 */
public class GestureHelp {
    public static final String TAG = "GestureHelp";
    // 存放手势的文件
    private final File mStoreFile = new File(
            Environment.getExternalStorageDirectory(), "gestures");

    private static GestureHelp mGestureHelp;
    private GestureLibrary mGestureLib;

    private GestureHelp() {
        if (mGestureLib == null) {
            mGestureLib = GestureLibraries.fromFile(mStoreFile);
        }

        if (mGestureLib.load()) {
            Log.e(TAG, "GestureHelp: 手势库加载成功");
        } else {
            Log.e(TAG, "GestureHelp: 手势库加载失败");
        }
    }

    public static GestureHelp getInstance() {
        if (mGestureHelp == null) {
            synchronized (GestureHelp.class) {
                if (mGestureHelp == null) {
                    mGestureHelp = new GestureHelp();
                }
            }
        }
        return mGestureHelp;
    }

    public GestureLibrary getmGestureLib() {
        return mGestureLib;
    }

    public void saveGesture(String name, Gesture gesture) {
        mGestureLib.addGesture(name, gesture);
        mGestureLib.save();
    }

    public ArrayList getFindResult(Gesture gesture) {
        return mGestureLib.recognize(gesture);
    }


}
