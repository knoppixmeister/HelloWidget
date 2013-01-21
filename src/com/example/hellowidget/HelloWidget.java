package com.example.hellowidget;

import java.util.Date;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class HelloWidget extends AppWidgetProvider {
	public static String YOUR_AWESOME_ACTION = "YourAwesomeAction";

	@SuppressWarnings("deprecation")
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;

		for(int i=0; i<N; i++) {
			int appWidgetId = appWidgetIds[i];

			Intent intent = new Intent(context, HelloWidget.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
																	.setAction(YOUR_AWESOME_ACTION)
																	.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 
																				appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            											//.getService(context, 0, intent, 0);

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.hellowidget);
			views.setOnClickPendingIntent(R.id.button1, pendingIntent);
			views.setTextViewText(R.id.textView1, new Date().toGMTString());

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("AAAA", "INT_ACT: "+intent.getAction());

		Bundle ext = intent.getExtras();
		if(ext != null) {
			Log.e("AAAA", "EXT: "+ext.toString());

			for(Object item : ext.keySet().toArray()) {
				Log.e("AAAA", "KEY: "+item);
			}
		}
		else Log.e("AAA", "EXT = null");

		if(intent.getAction().equals(YOUR_AWESOME_ACTION)) {
			Log.e("BBBB", intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)+"");

			if(ext != null) {
				Integer widgetId = ext.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
				if(widgetId != null) {
					RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.hellowidget);
					views.setTextViewText(R.id.textView1, new Date().toGMTString());

					AppWidgetManager.getInstance(context).updateAppWidget(widgetId, views);
				}
			}
		}

		super.onReceive(context, intent);
	}
}

class MyService extends Service {
	@Override
	public void onStart(Intent intent, int startId) {
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
