package application.com.car.listeners;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import application.com.car.customViews.CustomScrollView;

/**
 * Created by Zahit Talipov on 18.01.2016.
 */
public class TouchableWrapper extends FrameLayout {
    public TouchableWrapper(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                Log.d("action","down");
                CameraListener.mapIsDown=true;
                CameraListener.markerClean();
                CustomScrollView.enableScrolling=false;
                break;
            }
            case MotionEvent.ACTION_UP:{
                Log.d("action","up");
                CameraListener.mapIsDown=false;
                CustomScrollView.enableScrolling=true;
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
