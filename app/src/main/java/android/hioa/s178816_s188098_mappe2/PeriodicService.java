package android.hioa.s178816_s188098_mappe2;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class PeriodicService extends Service {
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Calendar cal = Calendar.getInstance();
		Intent i = new Intent(this, SMSService.class);
		PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar timeStart = Calendar.getInstance();
        timeStart.set(Calendar.HOUR_OF_DAY, 14);
        timeStart.set(Calendar.MINUTE, 30);
        timeStart.set(Calendar.SECOND, 0);
        //set repeat time to 14.30.00 each day

        //alarm.set(AlarmManager.RTC_WAKEUP, timeStart.getTimeInMillis(), pintent);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				1000*60, pintent);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
