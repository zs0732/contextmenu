package com.example.contextmenu;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity2 extends AppCompatActivity implements View.OnCreateContextMenuListener {
    Intent gi;
    double[] arr2 = new double[20];
    String[] arr = new String[20];
    boolean choice;
    double x1, x2;
    ListView lV;
    TextView tV;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.setHeaderTitle("בחר פעולה רצויה על הסדרה :");
        menu.add("מקומו של האיבר בסדרה");
        menu.add("סכום בין האיבר הראשון ל-איבר שנבחר");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        String mikum = item.getTitle().toString();
        if (mikum.equals("מקומו של האיבר בסדרה")) {
            tV.setText("מיקום האיבר בסדרה: " + (position + 1));
        } else if (mikum.equals("סכום בין האיבר הראשון ל-איבר שנבחר")) {
            double sum = 0;
            for (int i = 0; i <= position; i++) {
                sum = sum + arr2[i];
            }
            tV.setText("סכום מהאיבר הראשון עד האיבר הנבחר: " + sum);
        }
        return super.onContextItemSelected(item);
    }
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lV = findViewById(R.id.lV);
        gi = getIntent();
        tV = findViewById(R.id.tV);
        x1 = gi.getDoubleExtra("first", -1);
        x2 = gi.getDoubleExtra("mult", -1);
        choice = gi.getBooleanExtra("seriesChoice", true);
        lV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        arrFill();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr);
        lV.setAdapter(adp);
        lV.setOnCreateContextMenuListener(this);
    }
    public void arrFill() {
        double sum = x1;
        arr2[0] = x1;
        arr[0] = String.valueOf(x1);
        if (choice) {
            for (int i = 1; i < arr.length; i++) {
                sum = sum * x2;
                arr2[i] = sum;
                arr[i] = differentView(sum);
            }
        } else {
            for (int j = 1; j < arr.length; j++) {
                sum = sum + x2;
                arr2[j] = sum;
                arr[j] = differentView(sum);
            }
        }
    }
    public static String differentView(double term) {
        if (term % 1 == 0 && Math.abs(term) < 10000) {
            return String.valueOf((int) term);
        }

        if (Math.abs(term) < 10000 && Math.abs(term) >= 0.001) {
            return String.format("%.3f", term);
        }

        int exponent = 0;
        double coefficient = term;

        if (Math.abs(term) >= 1) {
            while (Math.abs(coefficient) >= 10) {
                coefficient /= 10;
                exponent++;
            }
        } else {
            while (Math.abs(coefficient) < 1 && coefficient != 0) {
                coefficient *= 10;
                exponent--;
            }
        }

        return String.format("%.3f * 10^%d", coefficient, exponent);
    }

    public void prev(View view)
    {
        finish();
    }
}
