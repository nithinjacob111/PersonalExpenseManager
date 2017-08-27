package com.example.nithin.personalexpensemanager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
    Button lg,fp;
    TextView qs;
    EditText pw,ans,respw;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        lg=(Button)findViewById(R.id.loginButton);
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw=(EditText)findViewById(R.id.loginPasswordEditText);
                String password=pw.getText().toString().trim();
                if (password.isEmpty() || password.length()==0 || password.equals("")) {
                    Toast.makeText(Login.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                }else {
                    Cursor c=MainActivity.db.rawQuery("SELECT password FROM UserDetails",null);
                    if(c.moveToFirst()) {
                        String pass=c.getString(0);
                        if (password.equals(pass)) {
                            c.close();
                            Intent i=new Intent(Login.this,HomeScreen.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        fp=(Button)findViewById(R.id.forgotPasswordButton);
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt,null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.LightDialogTheme);
                alertDialogBuilder.setView(promptsView);
                ans = (EditText) promptsView.findViewById(R.id.secqansEditText);
                Cursor c2=MainActivity.db.rawQuery("SELECT secqno FROM UserDetails",null);
                if(c2.moveToFirst()) {
                    int sqn=c2.getInt(0);
                    if (sqn==1) {
                        qs=(TextView)promptsView.findViewById(R.id.secqTextView);
                        qs.setText("Which is your favourite food?");
                    }
                    else if (sqn==2) {
                        qs=(TextView)promptsView.findViewById(R.id.secqTextView);
                        qs.setText("What is your mother's maiden name?");
                    }
                    else if (sqn==3) {
                        qs=(TextView)promptsView.findViewById(R.id.secqTextView);
                        qs.setText("who is your favourite teacher?");
                    }
                    c2.close();
                }
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        final Cursor c3=MainActivity.db.rawQuery("SELECT answer FROM UserDetails",null);
                                        if(c3.moveToFirst()) {
                                            String answ=c3.getString(0);
                                            final String answered=ans.getText().toString().trim();
                                            if (answered.equals(answ)) {
                                                LayoutInflater li2 = LayoutInflater.from(context);
                                                View promptsView2 = li2.inflate(R.layout.prompt2, null);
                                                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(context,R.style.LightDialogTheme);
                                                alertDialogBuilder2.setView(promptsView2);
                                                respw=(EditText)promptsView2.findViewById(R.id.resetPasswordEditText);
                                                alertDialogBuilder2.setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog2,int id2) {
                                                                String resetpass=respw.getText().toString().trim();
                                                                MainActivity.db.execSQL("UPDATE UserDetails SET password ='"+resetpass+"'");
                                                                c3.close();
                                                            }
                                                })
                                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog2,int id2) {
                                                                        dialog2.cancel();
                                                                    }
                                                                });
                                                AlertDialog alertDialog2 = alertDialogBuilder2.create();
                                                alertDialog2.show();

                                            }
                                            else {
                                                Toast.makeText(context, "sorry, wrong answer", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }
}
