package android.hioa.s178816_s188098_mappe2;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PeriodicService extends Service {
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Intent i = new Intent(this, SMSService.class);
		PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        SavedVariables sv = new SavedVariables(getApplication());
        int hour = sv.getHour();
        int min = sv.getMin();
        Calendar timeStart = Calendar.getInstance();
        timeStart.set(Calendar.HOUR_OF_DAY, hour);
        timeStart.set(Calendar.MINUTE, min);
        timeStart.set(Calendar.SECOND, 0);
        //set repeat time to 14.30.00 each day - get from date-class

        alarm.set(AlarmManager.RTC_WAKEUP, timeStart.getTimeInMillis(), pintent);
        //for testing
		/*alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				1000*60, pintent);*/
        Log.d("PeriodicService","true");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
