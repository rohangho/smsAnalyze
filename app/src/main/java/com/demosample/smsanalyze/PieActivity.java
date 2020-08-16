package com.demosample.smsanalyze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PieActivity extends AppCompatActivity {

    ArrayList<String> income=new ArrayList<String>();
    ArrayList<String> expense= new ArrayList<String>();
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        income=(ArrayList<String>)getIntent().getSerializableExtra("Income");
        expense=(ArrayList<String>)getIntent().getSerializableExtra("Expense");
        pieChart = findViewById(R.id.piechart);
        getNumbers();
    }


    /**
     * Message Parser Logic
     */
    private void getNumbers() {
        Observable.fromCallable(() -> {
           int s=0;
           int s1=0;
           for(int i=0;i<income.size();i++)
           {
               int a = income.get(i).indexOf("Rs");
               int b= income.get(i).indexOf("INR");
               int k=0;
               char[] abc = income.get(i).toCharArray();
               if(a!=-1)
               {

                   for(int j=a;j<abc.length;j++)
                   {

                       if(((int)abc[j])>=48 && ((int)abc[j])<=57)
                       {
                           k=k+Integer.parseInt(String.valueOf(abc[j]));
                       }

                   }
               }
               else if(b!=-1)
               {
                   for(int l=b;l<abc.length;l++)
                   {
                       if((int)abc[l]>=48 && (int)abc[l]<=57)
                       {
                           k=k+Integer.parseInt(String.valueOf(abc[l]));
                       }

                   }
               }
               else
                   continue;


             s=s+k;

           }

            for(int i=0;i<expense.size();i++)
            {
                int a = expense.get(i).indexOf("Rs");
                int b= expense.get(i).indexOf("INR");
                int k=0;
                char[] abc = expense.get(i).toCharArray();
                if(a!=-1)
                {

                    for(int j=a;j<abc.length;j++)
                    {

                        if(((int)abc[j])>=48 && ((int)abc[j])<=57)
                        {
                            k=k+Integer.parseInt(String.valueOf(abc[j]));
                        }

                    }
                }
                else if(b!=-1)
                {
                    for(int l=b;l<abc.length;l++)
                    {
                        if((int)abc[l]>=48 && (int)abc[l]<=57)
                        {
                            k=k+Integer.parseInt(String.valueOf(abc[l]));
                        }

                    }
                }
                else
                    continue;


                s1=s1+k;

            }
            int[] output =new int[]{s,s1};
           return output;


        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    showPie(result[0],result[1]);
                });
    }

    /**
     *  Function to display the pie
     * @param income total income
     * @param expense total expense
     */
    public void showPie(int income,int expense)
    {
        ArrayList details = new ArrayList();

        details.add(new Entry(income, 0));
        details.add(new Entry(expense, 1));

        PieDataSet dataSet = new PieDataSet(details, "");

        ArrayList year = new ArrayList();
        year.add("Income");
        year.add("Expense");
        PieData data = new PieData(year, dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChart.animateXY(3500, 5000);
    }
}