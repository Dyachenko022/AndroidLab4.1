package android.bignerdranch.com.androidlab41;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    DateRepository mDateRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDateRepository = DateRepository.getInstance(getApplicationContext());
        Button button = findViewById(R.id.set_date_button);
        final DatePicker datePicker = findViewById(R.id.datePicker);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                Date date = calendar.getTime();
                mDateRepository.insertDate(date);
                stopService(new Intent(MainActivity.this, DateService.class));
                startForegroundService(new Intent(MainActivity.this, DateService.class));
                Intent intent = new Intent(MainActivity.this, DateWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int [] idArray = AppWidgetManager.getInstance(getApplication())
                        .getAppWidgetIds(new ComponentName(getApplication(), DateWidget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
                sendBroadcast(intent);
                finish();
            }
        });
    }
}
