package android.hioa.s178816_s188098_mappe2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import android.telephony.SmsManager;

import java.util.Calendar;

public class SMSService extends Service {
    private static final String SMS_SENT_INTENT_FILTER = "android.hioa.s178816_s188098_mappe2.sms_send";
    private static final String SMS_DELIVERED_INTENT_FILTER = "android.hioa.s178816_s188098_mappe2.sms_delivered";
//    private final String standardMessage = getApplication().getString(R.string.smsDefault);

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent i = new Intent(this,MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);

        DBHandler db = new DBHandler(getApplicationContext());
        for(Person person : db.getAllPersonsWithBirthday())
        {
            sendSms(person);
            //start notification
            Notification noti = new Notification.Builder(this)
                    .setContentTitle("hei").//getApplication().getString(R.string.sendtSms)
                    setContentText("hehe").//getApplication().getString(R.string.sendtSmsTo)+ person.getFirstname() +" "+ person.getLastname()
                    setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent).build();
            noti.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, noti);

        }
        /*Toast.makeText(getApplicationContext(), "I MinService",
				Toast.LENGTH_SHORT).show();*/
		return super.onStartCommand(intent, flags, startId);
	}

    public void sendSms(Person p)
    {
        String message = "";
        if(p.getCustomMessage().equals(""))
            message = "kdjskjs";//standardMessage;
        else
            message = p.getCustomMessage();

        String phnNo = p.getMobile()+""; //preferable use complete international number

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
                SMS_SENT_INTENT_FILTER), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(
                SMS_DELIVERED_INTENT_FILTER), 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phnNo, null, message, sentPI, deliveredPI);
    }

}
