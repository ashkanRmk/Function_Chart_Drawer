package com.example.smn.functioncalculator;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Chart_Activity extends AppCompatActivity {

    TextView Tv_Func_name, Tv_func_Var, Tv_Max, Tv_Min;

    GraphView graph;

    Button btn_save;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_);

        Tv_Func_name = (TextView) findViewById(R.id.Tv_Fun_Name);
        Tv_func_Var = (TextView) findViewById(R.id.Tv_Fun_Var);
        Tv_Max = (TextView) findViewById(R.id.Tv_Max);
        Tv_Min = (TextView) findViewById(R.id.Tv_Min);
        btn_save = (Button) findViewById(R.id.btn_save);

        //Receive Data from Main Activity
        Intent intent = getIntent();
        double[] var = intent.getDoubleArrayExtra("var");
        double[] result = intent.getDoubleArrayExtra("result");
        String fname = intent.getStringExtra("fname");
        String fvar = intent.getStringExtra("fval");

        String min = min_array(result);
        String max = max_array(result);

        Tv_Max.setText(max);
        Tv_Min.setText(min);
        Tv_Func_name.setText(fname);
        Tv_func_Var.setText(fvar);

        ///////////Graph init
        graph = (GraphView) findViewById(R.id.graph);

        int index = result.length;

        DataPoint[] points = new DataPoint[index];
        for (int i = 0; i < points.length; i++) {
            points[i] = new DataPoint(var[i], result[i]);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);


//        // set manual X bounds
//        graph.getViewport().setYAxisBoundsManual(true);
//        graph.getViewport().setMinY(-150);
//        graph.getViewport().setMaxY(150);
//
//        graph.getViewport().setXAxisBoundsManual(true);
//        graph.getViewport().setMinX(4);
//        graph.getViewport().setMaxX(80);
//
//        // enable scaling and scrolling
//        graph.getViewport().setScalable(true);
//        graph.getViewport().setScalableY(true);


        graph.addSeries(series);
        //////////////////////

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.back) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path to include sd card appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(getApplicationContext(), "Saved Picture in : \n " + mPath, Toast.LENGTH_SHORT).show();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "نیاز به دسترسی روت!", Toast.LENGTH_SHORT).show();

        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }


    public String max_array(double[] array) {
        double tmp = Math.round(array[0]);
        for (int i = 0; i < array.length; i++) {
            if (array[i] > tmp) {
                tmp = Math.round(array[i]);
            }
        }
        String res = "" + tmp;
        return res;
    }

    public String min_array(double[] array) {
        double tmp = Math.round(array[0]);
        for (int i = 0; i < array.length; i++) {
            if (array[i] < tmp) {
                tmp = Math.round(array[i]);
            }
        }
        String res = "" + tmp;
        return res;
    }

}
