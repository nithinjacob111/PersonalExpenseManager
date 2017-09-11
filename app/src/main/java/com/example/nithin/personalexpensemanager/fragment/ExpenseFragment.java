package com.example.nithin.personalexpensemanager.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nithin.personalexpensemanager.HomeScreen;
import com.example.nithin.personalexpensemanager.ListItem;
import com.example.nithin.personalexpensemanager.MainActivity;
import com.example.nithin.personalexpensemanager.MyAdapter;
import com.example.nithin.personalexpensemanager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nithin on 4/9/17.
 */

public class ExpenseFragment extends Fragment implements View.OnClickListener {
    Button btnDatePicker,btnTimePicker,btnAddexpense;
    EditText txtDate,txtTime,txtAmount,txtDesc;
    int mYear,mMonth,mDay,mHour,mMinute,flag=0,ExpenseAmount,sign=2,mYear1,mMonth1,mDay1;
    View parentHolder;
    String catSelected,exdespcription,timetxt,datetxt,txtDateTime,ExpenseAmount2;
    Spinner sItems;
    Activity referenceActivity;
    public ExpenseFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        referenceActivity = getActivity();
        parentHolder=inflater.inflate(R.layout.fragment_expense, container, false);
        btnDatePicker=(Button)parentHolder.findViewById(R.id.expenseSelectDateButton);
        btnTimePicker=(Button)parentHolder.findViewById(R.id.expenseSelectTimeButton);
        btnAddexpense=(Button)parentHolder.findViewById(R.id.addExpenseButton);
        txtDate=(EditText)parentHolder.findViewById(R.id.expenseSelectDateEditText);
        txtTime=(EditText)parentHolder.findViewById(R.id.expenseSelectTimeEditText);
        txtAmount=(EditText)parentHolder.findViewById(R.id.expenseAmountEditText);
        txtDesc=(EditText)parentHolder.findViewById(R.id.expenseDescriptionEditText);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        sItems = (Spinner)parentHolder.findViewById(R.id.expenseCategorySpinner);

        btnAddexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseAmount2  =txtAmount.getText().toString().trim();
                ExpenseAmount=Integer.parseInt(txtAmount.getText().toString().trim());
                if(ExpenseAmount==0||ExpenseAmount<0||ExpenseAmount2.equals("")||ExpenseAmount2.length()==0||ExpenseAmount2.isEmpty()||ExpenseAmount2==null) {
                    Toast.makeText(referenceActivity, "Enter a valid amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    flag++;
                }
                catSelected=sItems.getSelectedItem().toString().trim();
                if(catSelected.equals("Select a Category")) {
                    Toast.makeText(referenceActivity, "Select a category", Toast.LENGTH_SHORT).show();
                }
                else {
                    flag++;
                }
                datetxt=txtDate.getText().toString().trim();
                if (datetxt.equals("")||datetxt.length()==0||datetxt.isEmpty()) {
                    Toast.makeText(referenceActivity, "Enter date", Toast.LENGTH_SHORT).show();
                }
                else {
                    flag++;
                }
                timetxt=txtTime.getText().toString().trim();
                if (timetxt.equals("")||timetxt.length()==0||timetxt.isEmpty()) {
                    Toast.makeText(referenceActivity, "Enter time", Toast.LENGTH_SHORT).show();
                }
                else {
                    flag++;
                }

                exdespcription=txtDesc.getText().toString().trim();
                txtDateTime=datetxt+" "+timetxt;
                if(flag==4) {
                    flag=0;
                    MainActivity.db.execSQL("CREATE TABLE IF NOT EXISTS Expense_table(Category VARCHAR(30),Amount INTEGER,Sign INTEGER,Description VARCHAR(100),Date DATE,Time TIME,DateAndTime DATETIME,Day INTEGER,Month INTEGER,Year INTEGER);");
                    MainActivity.db.execSQL("INSERT INTO Expense_table VALUES('"+catSelected+"','"+ExpenseAmount+"','"+sign+"','"+exdespcription+"','"+datetxt+"','"+timetxt+"','"+txtDateTime+"','"+mDay1+"','"+mMonth1+"','"+mYear1+"')");
                    Intent i=new Intent(getActivity(), HomeScreen.class);
                    startActivity(i);
                }

            }
        });


        return parentHolder;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_screen, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            //weekno=c.get(Calendar.WEEK_OF_YEAR);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                    txtDate.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                    mYear1=year;
                    mMonth1=monthOfYear+1;
                    mDay1=dayOfMonth;
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
                    txtTime.setText(hourOfDay + ":" + minute);
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

}
