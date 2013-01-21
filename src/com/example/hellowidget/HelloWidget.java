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
import android.widget.Toast;

public class HelloWidget extends AppWidgetProvider {
	public static final String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Log.e("AAAA", "Widget ON UPDATE");

		for(int i=0; i<appWidgetIds.length; i++) {
			Log.e("AAAAAAAA", appWidgetIds[i]+"");

			Log.e("BBBB", this.getClass().getPackage().getName()+"."+AppWidgetManager.EXTRA_APPWIDGET_ID);

			Intent intent = new Intent(context, HelloWidget.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
																	.setAction(ACTION_WIDGET_RECEIVER)
																	.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			PendingIntent pi = PendingIntent.getBroadcast(context, appWidgetIds[i], intent, PendingIntent.FLAG_UPDATE_CURRENT);

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.hellowidget);
			views.setOnClickPendingIntent(R.id.button1, pi);
			views.setTextViewText(R.id.textView1, new Date().toGMTString());

			appWidgetManager.updateAppWidget(appWidgetIds[i], views);
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

		if(intent.getAction().equals(ACTION_WIDGET_RECEIVER)) {
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

	public void onDeleted(Context context, int[] appWidgetIds) {
		Toast.makeText(context, "Widget instance deleted", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEnabled(Context context) {
		Log.e("AAAA", "Widget ON ENABLED");

		super.onEnabled(context);
	}
}

class MyService extends Service {
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
