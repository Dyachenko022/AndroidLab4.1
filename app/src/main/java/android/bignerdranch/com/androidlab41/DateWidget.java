package android.bignerdranch.com.androidlab41;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class DateWidget extends AppWidgetProvider {

    DateRepository mDateRepository;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       super.onUpdate(context, appWidgetManager, appWidgetIds);
       mDateRepository = DateRepository.getInstance(context);
       RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.date_widget);
       Date date = mDateRepository.getDate();
       Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
       String result = "no date chosen yet";
       if(currentDate.getTime() < date.getTime())
       {

           long differenceBetweenDates = date.getTime() - currentDate.getTime();
           long differenceSeconds = (differenceBetweenDates / 1000) % 60;
           long differenceMinutes = (differenceBetweenDates / (1000*60)) %60;
           long differenceHours = (differenceBetweenDates / (1000*60*60)) % 24;
           long differenceDays = (differenceBetweenDates / (1000*60*60*24));
           result = differenceDays + " дней " + differenceHours + " часов " + differenceMinutes + " минут "
                   + differenceSeconds + "  секунд" ;
       }
       Intent intent = new Intent(context, MainActivity.class);
       PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
       remoteViews.setTextViewText(R.id.date_text, result);
       remoteViews.setOnClickPendingIntent(R.id.choose_date, pendingIntent);
       ComponentName componentName = new ComponentName(context, DateWidget.class);
       AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
    }
}

