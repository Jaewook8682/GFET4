package com.microchip.mu_ble1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

import java.util.Arrays;

public class MeasureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        Intent msr_intent = getIntent();
        int d_num = msr_intent.getIntExtra("d_nn");

        // Graph View
        LineChart chart_iv  = findViewById(R.id.graph_iv);
        chart_iv.getLegend().setEnabled(true);
        chart_iv.setTouchEnabled(true);
        chart_iv.setDoubleTapToZoomEnabled(true);
        chart_iv.invalidate();
        LineData data = new LineData();
        chart_iv.setData(data);

        LineChart chart_res = findViewById(R.id.graph_response);
        chart_res.getLegend().setEnabled(true);
        chart_res.setTouchEnabled(true);
        chart_res.setDoubleTapToZoomEnabled(true);
        chart_res.invalidate();
        LineData data2 = new LineData();
        chart_res.setData(data2);

        EditText n_measure_ = findViewById(R.id.n_measure);
        Button bt_start = findViewById(R.id.start);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("START", "CLICKED");
                chart_iv.invalidate();
                LineData data = new LineData();
                chart_iv.setData(data);

                chart_res.invalidate();
                LineData data2 = new LineData();
                chart_res.setData(data2);

                d_num = 0;
                measure_n = Integer.valueOf(String.valueOf(n_measure_.getText()));
                arr_rcv = new String[measure_n * 300];
                String sendData = "g";
                for (int i = 0; i < measure_n; i++) {
                    Log.d("REQ" + interval_n, "req");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    bleService.writeToTransparentUART(sendData.getBytes());
                }
            }
        });

        Button clear_ = findViewById(R.id.clear);
        clear_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Clear", "CLICKED");
                Arrays.fill(arr_rcv, "0");
                //Arrays.fill(arr_rsp, "0");
                chart_iv.invalidate();
                chart_iv.clear();
                chart_res.invalidate();
                chart_res.clear();
            }
        });

        EditText et_save_ = findViewById(R.id.et_save);
        Button bt_save_   = findViewById(R.id.bt_save);
        bt_save_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeFile(String.valueOf(et_save_.getText()));
                et_save_.setText(null);
                Toast mToast = Toast.makeText(getApplicationContext(), "Successfully saved", Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }
}