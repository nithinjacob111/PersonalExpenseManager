package com.example.nithin.personalexpensemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.view.Window;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {
    static SQLiteDatabase db;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        db=openOrCreateDatabase("PEMDB", Context.MODE_PRIVATE,null);

        settings=getSharedPreferences("mysettings",Context.MODE_PRIVATE);
        String mystring=settings.getString("mystring","no");
        if(mystring.equals("yes")) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // TODO: Your application init goes here.
                    Intent mInHome = new Intent(MainActivity.this, Login.class);
                    MainActivity.this.startActivity(mInHome);
                    MainActivity.this.finish();
                }
            }, 3000);
        }
        else if (mystring.equals("no")){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // TODO: Your application init goes here.
                    Intent mInHome = new Intent(MainActivity.this, OneTimeSetup.class);
                    MainActivity.this.startActivity(mInHome);
                    MainActivity.this.finish();
                }
            }, 3000);
        }
    }
}