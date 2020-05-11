package com.example.optmiddlevalue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    private String urlString1 = "https://tw.screener.finance.yahoo.net/future/aa03?opmr=optionfull&opcm=WTXO&opym=202005W2";
    private String urlString2 = "https://tw.screener.finance.yahoo.net/future/aa03?opmr=optionfull&opcm=WTXO&opym=202005";

    private List<Value1> values1;
    private List<Value2> values2;

    long callTotalValue = 0;
    long putTotalValue = 0;

    int callTotalOI = 0;
    int putTotalOI = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView_1);
        textView2 = findViewById(R.id.textView_2);
        textView3 = findViewById(R.id.textView_3);
        textView4 = findViewById(R.id.textView_4);

        Thread thread1 = new Thread(multiThread1);
        thread1.start();

        Thread thread2 = new Thread(multiThread2);
        thread2.start();
    }

    private Runnable multiThread1 = new Runnable() {
        @Override
        public void run() {
            Document data = null;

            try {
                Connection.Response response = Jsoup.connect(urlString1).execute();
                String body = response.body();
                data = Jsoup.parse(body);
            } catch (IOException e) {
                e.getMessage();
            }

            values1 = new ArrayList<>();
            //解析第二個 <table> 標籤
            Elements table = data.select("table[class=ext-big-tbl] > tbody").get(1).select("tr");
            for (int i = 1; i < table.size(); i++) {
                Elements tds = table.get(i).select("td");

                int value = Integer.parseInt(tds.get(7).text());
                String cfp = tds.get(2).text();
                double cfp_double = 0.00;
                if (!cfp.equals("--")) {
                    cfp_double = Double.parseDouble(tds.get(2).text());
                }
                int coi = Integer.parseInt(tds.get(4).text());

                String pfp = tds.get(10).text();
                double pfp_double = 0.00;
                if (!pfp.equals("--")) {
                    pfp_double = Double.parseDouble(tds.get(10).text());
                }

                int poi = Integer.parseInt(tds.get(12).text());

                Value1 value1 = new Value1(value, cfp_double, coi, pfp_double, poi);
                values1.add(value1);
            }

            for (Value1 value1 : values1) {
//                Log.e(TAG, "履約價： " + value2.getStrikePrice() + "\n");
//                Log.e(TAG, "CALL 成交價： " + value2.getCallFinalPrice() + "\n");
//                Log.e(TAG, "CALL 未平倉： " + value2.getCallOI() + "\n");
//                Log.e(TAG, "PUT 成交價： " + value2.getPutFinalPrice() + "\n");
//                Log.e(TAG, "PUT 未平倉： " + value2.getPutOI() + "\n");

                callTotalOI += value1.getCallOI();
                putTotalOI += value1.getPutOI();

                callTotalValue += (value1.getStrikePrice() + value1.getCallFinalPrice()) * value1.getCallOI();
                putTotalValue += (value1.getStrikePrice() + value1.getPutFinalPrice()) * value1.getPutOI();
            }

            Log.e(TAG, "週中值 1: " + (callTotalValue + putTotalValue) / (callTotalOI + putTotalOI));
            Log.e(TAG, "週中值 2: " + ((callTotalValue / callTotalOI) + (putTotalValue / putTotalOI)) / 2);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView1.setText("週中值 A : " + (callTotalValue + putTotalValue) / (callTotalOI + putTotalOI));
                    textView2.setText("週中值 B : " + ((callTotalValue / callTotalOI) + (putTotalValue / putTotalOI)) / 2);
                    values1.clear();
                    callTotalValue = 0;
                    putTotalValue = 0;
                    callTotalOI = 0;
                    putTotalOI = 0;
                }
            });
        }
    };

    private Runnable multiThread2 = new Runnable() {
        @Override
        public void run() {
            Document data = null;

            try {
                Connection.Response response = Jsoup.connect(urlString2).execute();
                String body = response.body();
                data = Jsoup.parse(body);
            } catch (IOException e) {
                e.getMessage();
            }

            values2 = new ArrayList<>();
            //解析第二個 <table> 標籤
            Elements table = data.select("table[class=ext-big-tbl] > tbody").get(1).select("tr");
            for (int i = 1; i < table.size(); i++) {
                Elements tds = table.get(i).select("td");

                int value = Integer.parseInt(tds.get(7).text());
                String cfp = tds.get(2).text();
                double cfp_double = 0.00;
                if (!cfp.equals("--")) {
                    cfp_double = Double.parseDouble(tds.get(2).text());
                }
                int coi = Integer.parseInt(tds.get(4).text());

                String pfp = tds.get(10).text();
                double pfp_double = 0.00;
                if (!pfp.equals("--")) {
                    pfp_double = Double.parseDouble(tds.get(10).text());
                }

                int poi = Integer.parseInt(tds.get(12).text());

                Value2 value1 = new Value2(value, cfp_double, coi, pfp_double, poi);
                values2.add(value1);
            }


            for (Value2 value2 : values2) {
//                Log.e(TAG, "履約價： " + value2.getStrikePrice() + "\n");
//                Log.e(TAG, "CALL 成交價： " + value2.getCallFinalPrice() + "\n");
//                Log.e(TAG, "CALL 未平倉： " + value2.getCallOI() + "\n");
//                Log.e(TAG, "PUT 成交價： " + value2.getPutFinalPrice() + "\n");
//                Log.e(TAG, "PUT 未平倉： " + value2.getPutOI() + "\n");

                callTotalOI += value2.getCallOI();
                putTotalOI += value2.getPutOI();

                callTotalValue += (value2.getStrikePrice() + value2.getCallFinalPrice()) * value2.getCallOI();
                putTotalValue += (value2.getStrikePrice() + value2.getPutFinalPrice()) * value2.getPutOI();
            }

            Log.e(TAG, "月中值 1: " + (callTotalValue + putTotalValue) / (callTotalOI + putTotalOI));
            Log.e(TAG, "月中值 2: " + ((callTotalValue / callTotalOI) + (putTotalValue / putTotalOI)) / 2);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView3.setText("月中值 A : " + (callTotalValue + putTotalValue) / (callTotalOI + putTotalOI));
                    textView4.setText("月中值 B : " + ((callTotalValue / callTotalOI) + (putTotalValue / putTotalOI)) / 2);
                    values2.clear();
                    callTotalValue = 0;
                    putTotalValue = 0;
                    callTotalOI = 0;
                    putTotalOI = 0;
                }
            });
        }
    };
}
