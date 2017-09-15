package com.example.nithin.personalexpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.label;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BarChart barChart,barChart2;
    PieChart pieChart,pieChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeScreen.this,add_data.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        barChart=(BarChart)findViewById(R.id.incomeBarGraph);
        pieChart=(PieChart)findViewById(R.id.incomeCategoryPieGraph);

        String sql1="SELECT * FROM sqlite_master WHERE type='table' AND name='Income_table'";
        Cursor c1 = MainActivity.db.rawQuery(sql1, null);
        int count1 = c1.getCount();
        if(count1==0) {
            c1.close();
        }else {


            String sql = "SELECT sum(Amount),month1 FROM Income_table GROUP BY Month1 ORDER BY DateAndTime";
            Cursor c = MainActivity.db.rawQuery(sql, null);
            int count = c.getCount();
            int[] amount = new int[count];
            int[] months = new int[count];
            for (int m = 0; m < count; m++) {
                c.moveToNext();
                amount[m] = c.getInt(0);
                months[m] = c.getInt(1);
            }
            ArrayList<BarEntry> yVals = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                yVals.add(new BarEntry(amount[i], i));
            }
            ArrayList<String> xVals = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                xVals.add(monthFloatToString(months[i]));
            }
            BarDataSet set = new BarDataSet(yVals, "Income amount");
            BarData data = new BarData(xVals, set);
            barChart.setData(data);
            barChart.setDescription("monthly Income");
            barChart.setTouchEnabled(true);
            barChart.animateY(3000, Easing.EasingOption.EaseOutQuad);
            barChart.setDragEnabled(true);
            c.close();

            String sql2="SELECT sum(Amount),Category FROM Income_table GROUP BY Category";
            Cursor c2 = MainActivity.db.rawQuery(sql2, null);
            int count2 = c2.getCount();
            int[] amount1 = new int[count2];
            String[] category = new String[count2];
            for(int m=0; m<count2; m++) {
                c2.moveToNext();
                amount1[m] = c2.getInt(0);
                category[m] = c2.getString(1);
            }
            ArrayList<Entry> entries = new ArrayList<>();
            for (int i = 0; i < count2; i++) {
                entries.add(new Entry(amount1[i], i));
            }
            ArrayList<String> themonths = new ArrayList<String>();
            for (int i = 0; i < count2; i++) {
                themonths.add(category[i]);
            }
            PieDataSet set1 = new PieDataSet(entries, " ");
            PieData data1 = new PieData(themonths, set1);
            pieChart.setData(data1);
            set1.setColors(ColorTemplate.COLORFUL_COLORS);
            pieChart.setTouchEnabled(true);
            pieChart.setDescription("Income, Catagory wise");
            pieChart.animateY(3000, Easing.EasingOption.EaseOutCirc);
            c2.close();



            c1.close();

        }


        /*ArrayList<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(56f,1));
        barEntries.add(new BarEntry(-12f,3));
        barEntries.add(new BarEntry(34f,4));
        BarDataSet barDataSet=new BarDataSet(barEntries,"Dates");
        ArrayList<String> theDates=new ArrayList<>();
        theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");

        BarData theData =new BarData(theDates,barDataSet);
        barChart.setData(theData);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);*/
        barChart2=(BarChart)findViewById(R.id.expenseBarGraph);
        pieChart2=(PieChart)findViewById(R.id.expenseCategoryPieGraph);

        String sql3="SELECT * FROM sqlite_master WHERE type='table' AND name='Expense_table'";
        Cursor c3 = MainActivity.db.rawQuery(sql3, null);
        int count3 = c3.getCount();
        if(count3==0) {
            c3.close();
        }else {


            String sql4 = "SELECT sum(Amount),month1 FROM Expense_table GROUP BY Month1 ORDER BY DateAndTime";
            Cursor c4 = MainActivity.db.rawQuery(sql4, null);
            int count4 = c4.getCount();
            int[] amount2 = new int[count4];
            int[] months2 = new int[count4];
            for (int m = 0; m < count4; m++) {
                c4.moveToNext();
                amount2[m] = c4.getInt(0);
                months2[m] = c4.getInt(1);
            }
            ArrayList<BarEntry> yVals1 = new ArrayList<>();
            for (int i = 0; i < count4; i++) {
                yVals1.add(new BarEntry(amount2[i], i));
            }
            ArrayList<String> xVals1 = new ArrayList<>();
            for (int i = 0; i < count4; i++) {
                xVals1.add(monthFloatToString(months2[i]));
            }
            BarDataSet set2 = new BarDataSet(yVals1, "Expense amount");
            BarData data1 = new BarData(xVals1, set2);
            barChart2.setData(data1);
            barChart2.setDescription("monthly Expense");
            barChart2.setTouchEnabled(true);
            barChart2.animateY(3000, Easing.EasingOption.EaseOutQuad);
            barChart2.setDragEnabled(true);
            c4.close();

            String sql5="SELECT sum(Amount),Category FROM Expense_table GROUP BY Category";
            Cursor c5 = MainActivity.db.rawQuery(sql5, null);
            int count5 = c5.getCount();
            int[] amount3 = new int[count5];
            String[] category1 = new String[count5];
            for(int m=0; m<count5; m++) {
                c5.moveToNext();
                amount3[m] = c5.getInt(0);
                category1[m] = c5.getString(1);
            }
            ArrayList<Entry> entries = new ArrayList<>();
            for (int i = 0; i < count5; i++) {
                entries.add(new Entry(amount3[i], i));
            }
            ArrayList<String> themonths1 = new ArrayList<String>();
            for (int i = 0; i < count5; i++) {
                themonths1.add(category1[i]);
            }
            PieDataSet set3 = new PieDataSet(entries, " ");
            PieData data2 = new PieData(themonths1, set3);
            pieChart2.setData(data2);
            set3.setColors(ColorTemplate.COLORFUL_COLORS);
            pieChart2.setTouchEnabled(true);
            pieChart2.setDescription("Expense, Catagory wise");
            pieChart2.animateY(3000, Easing.EasingOption.EaseOutCirc);
            c5.close();
            c3.close();

        }

    }
    public String monthFloatToString(int monval) {
        String Mon="";
        if (monval==1) {
            Mon="Jan";
        }
        else if (monval==2) {
            Mon="Feb";
        }
        else if (monval==3) {
            Mon="Mar";
        }
        else if (monval==4) {
            Mon="Apr";
        }
        else if (monval==5) {
            Mon="May";
        }
        else if (monval==6) {
            Mon="Jun";
        }
        else if (monval==7) {
            Mon="Jul";
        }
        else if (monval==8) {
            Mon="Aug";
        }
        else if (monval==9) {
            Mon="Sep";
        }
        else if (monval==10) {
            Mon="Oct";
        }
        else if (monval==11) {
            Mon="Nov";
        }
        else if (monval==12) {
            Mon="Dec";
        }
        return Mon;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_home_screen) {
            // Handle the camera action
        } else if (id == R.id.nav_add_records) {
            Intent i=new Intent(HomeScreen.this,add_data.class);
            startActivity(i);

        }// else if (id == R.id.nav_slideshow) {

        //} else if (id == R.id.nav_manage) {

        //} else if (id == R.id.nav_share) {

   //     } else if (id == R.id.nav_send) {

     //   }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
