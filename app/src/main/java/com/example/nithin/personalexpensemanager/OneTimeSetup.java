package com.example.nithin.personalexpensemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class OneTimeSetup extends Activity {
    Button sv;
    EditText nm,pw,cfmpw,ans;
    Spinner sq;
    int flag=0,sqn;
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
        setContentView(R.layout.activity_one_time_setup);
        sv=(Button)findViewById(R.id.savebtn);
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nm=(EditText)findViewById(R.id.nameeditText);
                String name=nm.getText().toString().trim();
                if(name.isEmpty() || name.length()==0 || name.equals("") || name== null) {
                    Toast.makeText(OneTimeSetup.this, "Please enter the name", Toast.LENGTH_SHORT).show();
                }else {
                    flag++;
                }
                pw=(EditText)findViewById(R.id.passwordeditText);
                String password=pw.getText().toString().trim();
                if (password.isEmpty() || password.length()==0 || password.equals("") || password== null) {
                    Toast.makeText(OneTimeSetup.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                }else {
                    flag++;
                }
                cfmpw=(EditText)findViewById(R.id.confirmpweditText);
                String confirmpassword=cfmpw.getText().toString().trim();
                if (confirmpassword.isEmpty() || confirmpassword.length()==0 || confirmpassword.equals("") || confirmpassword== null) {
                    Toast.makeText(OneTimeSetup.this, "Please confirm password", Toast.LENGTH_SHORT).show();
                }else {
                    flag++;
                }
                if (password.contentEquals(confirmpassword)) {
                    flag++;
                }else {
                    Toast.makeText(OneTimeSetup.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                    flag=0;
                }
                sq=(Spinner)findViewById(R.id.sqspinner);
                String text= sq.getSelectedItem().toString().trim();
                if (text.equals("Select any")) {
                    Toast.makeText(OneTimeSetup.this, "Select a question", Toast.LENGTH_SHORT).show();
                }else {
                    flag++;
                    if(text.equals("Which is your favourite food?")){
                        sqn=1;
                    }else if (text.equals("What is your mother's maiden name?")){
                        sqn=2;
                    }else if(text.equals("who is your favourite teacher?")){
                        sqn=3;
                    }
                }
                ans=(EditText)findViewById(R.id.sqanseditText);
                String sqanswer=ans.getText().toString().trim();
                if(sqanswer.isEmpty()||sqanswer.length()==0||sqanswer.equals("")||sqanswer==null) {
                    Toast.makeText(OneTimeSetup.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }else {
                    flag++;
                }
                if(flag==6) {
                    MainActivity.db.execSQL("CREATE TABLE IF NOT EXISTS UserDetails(name VARCHAR,password VARCHAR,secqno INTEGER,answer VARCHAR);");
                    MainActivity.db.execSQL("INSERT INTO UserDetails(name,password,secqno,answer)Values('"+name+"','"+password+"','"+sqn+"','"+sqanswer+"');");
                    Toast.makeText(OneTimeSetup.this, "Done", Toast.LENGTH_SHORT).show();
                    flag=0;
                    settings=getSharedPreferences("mysettings",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putString("mystring","yes");
                    editor.commit();
                    Intent i=new Intent(OneTimeSetup.this,Login.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }
}
