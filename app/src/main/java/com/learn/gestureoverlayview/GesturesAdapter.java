package com.learn.gestureoverlayview;

import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fucc
 * Date: 2019-08-19 18:47
 */
public class GesturesAdapter extends ArrayAdapter<GesturesAdapter.NamedGesture> {
    private final Map<Long, Drawable> mThumbnails = Collections
            .synchronizedMap(new HashMap<Long, Drawable>());
    public final Context mContext;

    public GesturesAdapter(Context context) {
        super(context, 0);
        mContext = context;
    }

    void addBitmap(Long id, Bitmap bitmap) {
        mThumbnails.put(id, new BitmapDrawable(bitmap));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(mContext);
        }

        NamedGesture gesture = getItem(position);
        TextView label = (TextView) convertView;

        label.setTag(gesture);
        label.setText(gesture.name);
        label.setCompoundDrawablesWithIntrinsicBounds(
                mThumbnails.get(gesture.gesture.getID()), null, null, null);

        return convertView;
    }

    static class NamedGesture {
        String name;
        Gesture gesture;
    }

}
