package me.zsj.batterylife;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by zsj on 2016/6/5.
 */
public class BatteryService extends Service {

    private BatteryInfoReceiver infoReceiver;

    @Override
    public void onCreate() {
        super.onCreate();


    }

    private void sendBatteryBroadcast() {
        infoReceiver = Receiver.getInstance();
        registerReceiver(infoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendBatteryBroadcast();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(infoReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BatteryBinder();
    }

    class BatteryBinder extends Binder {

    }
}
