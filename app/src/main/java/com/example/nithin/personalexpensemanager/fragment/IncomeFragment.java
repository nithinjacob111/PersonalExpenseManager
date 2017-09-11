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


public class IncomeFragment extends Fragment implements View.OnClickListener {
    Button btnDatePicker,btnTimePicker,btnAddincome;
    EditText txtDate,txtTime,txtAmount,txtDesc;
    int mYear,mMonth,mDay,mHour,mMinute,flag=0,IncomeAmount,sign=1,mYear1,mMonth1,mDay1;
    View parentHolder;
    String catSelected,indespcription,timetxt,datetxt,txtDateTime,IncomeAmount2;
    Spinner sItems;
    Activity referenceActivity;

    public IncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        referenceActivity = getActivity();
        parentHolder=inflater.inflate(R.layout.fragment_income, container, false);
        btnDatePicker=(Button)parentHolder.findViewById(R.id.selectDateButton);
        btnTimePicker=(Button)parentHolder.findViewById(R.id.selectTimeButton);
        btnAddincome=(Button)parentHolder.findViewById(R.id.addIncomeButton);
        txtDate=(EditText)parentHolder.findViewById(R.id.selectDateEditText);
        txtTime=(EditText)parentHolder.findViewById(R.id.selectTimeEditText);
        txtAmount=(EditText)parentHolder.findViewById(R.id.incomeAmountEditText);
        txtDesc=(EditText)parentHolder.findViewById(R.id.incomeDescriptionEditText);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        //List<String> spinnerArray =  new ArrayList<String>();
        //spinnerArray.add("Select a Catagory");
        //spinnerArray.add("Loan");
        //spinnerArray.add("Salary");
        //spinnerArray.add("Sales");
        //spinnerArray.add("Other");
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner)parentHolder.findViewById(R.id.incomeCategorySpinner);
        //sItems.setAdapter(adapter);
        //Spinner mySpinner = (Spinner) parentHolder.findViewById(R.id.incomeCategorySpinner);
        //mySpinner.setAdapter(new MyAdapter(getContext(), R.layout.row, getAllList()));


        btnAddincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IncomeAmount2=txtAmount.getText().toString().trim();
                IncomeAmount=Integer.parseInt(txtAmount.getText().toString().trim());
                if(IncomeAmount==0||IncomeAmount<0||IncomeAmount2.equals("")||IncomeAmount2.length()==0||IncomeAmount2.isEmpty()||IncomeAmount2==null) {
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

                indespcription=txtDesc.getText().toString().trim();
                txtDateTime=datetxt+" "+timetxt;
                if(flag==4) {
                    flag=0;
                    MainActivity.db.execSQL("CREATE TABLE IF NOT EXISTS Income_table(Category VARCHAR(30),Amount INTEGER,Sign INTEGER,Description VARCHAR(100),Date DATE,Time TIME,DateAndTime DATETIME,Day INTEGER,Month INTEGER,Year INTEGER);");
                    MainActivity.db.execSQL("INSERT INTO Income_table VALUES('"+catSelected+"','"+IncomeAmount+"','"+sign+"','"+indespcription+"','"+datetxt+"','"+timetxt+"','"+txtDateTime+"','"+mDay1+"','"+mMonth1+"','"+mYear1+"')");
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
    /*public ArrayList<ListItem> getAllList() {

        ArrayList<ListItem> allList = new ArrayList<ListItem>();

        ListItem item = new ListItem();
        item.setData("Select a Category", R.drawable.cat_empty);
        allList.add(item);

        item = new ListItem();
        item.setData("Loan", R.drawable.cat_in_loan);
        allList.add(item);

        item = new ListItem();
        item.setData("Salary", R.drawable.cat_in_salary);
        allList.add(item);

        item = new ListItem();
        item.setData("Sales", R.drawable.cat_in_sales);
        allList.add(item);

        item = new ListItem();
        item.setData("Other", R.drawable.cat_in_other);
        allList.add(item);

        return allList;
    }*/
}