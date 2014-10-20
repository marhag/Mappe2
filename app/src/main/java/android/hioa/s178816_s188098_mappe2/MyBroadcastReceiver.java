package android.hioa.s178816_s188098_mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "I BroadcastReceiver", Toast.LENGTH_SHORT)
				.show();

		Intent i = new Intent(context, PeriodicService.class);
		context.startService(i);
	}

}
