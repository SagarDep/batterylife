package me.zsj.batterylife;

/**
 * Created by zsj on 2016/6/8.
 */
public class Receiver {

    public static BatteryInfoReceiver receiver;

    public static BatteryInfoReceiver getInstance() {
        if (receiver == null) {
            receiver = new BatteryInfoReceiver();
        }
        return receiver;
    }
}
