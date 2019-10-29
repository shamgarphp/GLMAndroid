package com.example.sss.goodlife.Utils;


import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

public class Utilities {

    // Singleton
    private static Utilities sharedInstance = null;

    public Activity currentActivityDetails;

    private Utilities() {
    }

    public static Utilities getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new Utilities();
        }
        return sharedInstance;
    }




    /*This method is used to set hover effects for buttons */
    public static void setHoverForButtons(final View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                try {
                    // when button toucheda
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        view.setAlpha(0.6f);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //when finger was lifted
                        view.setAlpha(1f);
                    }
                }
                catch (Exception ignored)
                {
                }
                return false;
            }
        });
    }
}

