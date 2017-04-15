package com.example.lining.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by lining on 17-2-28.
 */
public class TwoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whether_intall);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(TwoActivity.this, "1111111", Toast.LENGTH_SHORT).show();
        Log.e("onKeyDown===","11111111");
        if(keyCode==KeyEvent.KEYCODE_DPAD_CENTER){
            Toast.makeText(TwoActivity.this, "确认键", Toast.LENGTH_SHORT).show();
            Log.e("onKeyDown==","确认键");
            return true;
        }else if(keyCode==KeyEvent.KEYCODE_DPAD_RIGHT){
            Toast.makeText(TwoActivity.this, "右键", Toast.LENGTH_SHORT).show();
            Log.e("onKeyDown==","右键");
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
