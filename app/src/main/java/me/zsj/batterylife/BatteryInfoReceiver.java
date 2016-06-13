package me.zsj.batterylife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 * Created by zsj on 2016/6/5.
 */
public class BatteryInfoReceiver extends BroadcastReceiver {

    private BatteryInfoListener infoListener;

    public void setInfoListener(BatteryInfoListener infoListener) {
        this.infoListener = infoListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        if (infoListener != null) infoListener.info(level);
    }

    public interface BatteryInfoListener {
        void info(int level);
    }

}
