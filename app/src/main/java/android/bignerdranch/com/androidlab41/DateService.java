package android.bignerdranch.com.androidlab41;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateService extends Service {
    DateRepository mDateRepository = DateRepository.getInstance();
    String channelId ="channel_id";
    NotificationManager mNotificationManager;
    Thread mThread = new Thread()
    {
        @Override
        public void run() {
            Date date = mDateRepository.getDate();
            try
            {
                final long timeToNotify = date.getTime() - Calendar.getInstance().getTime().getTime();
                TimeUnit.MILLISECONDS.sleep(timeToNotify);
                Notification.Builder builder = new Notification.Builder(DateService.this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Timer")
                        .setContentText("The time has come");
                Notification notification = builder.build();
                mNotificationManager.notify(1, notification);
            }catch(InterruptedException ie)
            {
                return;
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String name = "channelName";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel =new NotificationChannel(channelId, name, importance);
        channel.setDescription("Notifications for service");
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        channel.enableVibration(true);
        channel.setShowBadge(true);
        mNotificationManager.createNotificationChannel(channel);
        Notification.Builder builder = new Notification.Builder(DateService.this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Timer").setContentText("Time's coming");
        Notification foregroundNotification = builder.build();
        super.onCreate();
        startForeground(1, foregroundNotification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.interrupt();

    }
}
