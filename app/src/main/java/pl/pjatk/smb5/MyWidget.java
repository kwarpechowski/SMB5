package pl.pjatk.smb5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import static android.R.style.Widget;


public class MyWidget extends AppWidgetProvider {
    private static final String IMG = "imgClick";
    private static final String SOUND = "soundClick";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        watchWidget = new ComponentName(context, MyWidget.class);

        remoteViews.setOnClickPendingIntent(R.id.button, getPendingWWW(context, "http://pja.edu.pl"));
        remoteViews.setOnClickPendingIntent(R.id.button2, getPendingSelfIntent(context, IMG));
        remoteViews.setOnClickPendingIntent(R.id.button3, getPendingSelfIntent(context, SOUND));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final MediaPlayer mp = MediaPlayer.create(context, R.raw.sound);
        super.onReceive(context, intent);

        if (IMG.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget);
            watchWidget = new ComponentName(context, MyWidget.class);

            remoteViews.setImageViewResource(R.id.imageView2, R.drawable.lenny);
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        } else if(SOUND.equals(intent.getAction())) {
            mp.start();
        }
    }

    protected PendingIntent getPendingWWW(Context context, String action) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(action));
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}