package me.zsj.batterylife;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cyanogenmod.app.CMStatusBarManager;
import cyanogenmod.app.CustomTile;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageBattery;
    private TextView mLevelText;

    private LayerDrawable layerDrawable;

    private int mLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mTileButton = (Button) findViewById(R.id.custom_tile_button);
        mImageBattery = (ImageView) findViewById(R.id.image_battery);
        mLevelText = (TextView) findViewById(R.id.battery_level_text);

        BatteryInfoReceiver batteryInfoReceiver = Receiver.getInstance();

        if (mImageBattery != null) {
            layerDrawable = (LayerDrawable) mImageBattery.getDrawable();
        }

        if (mTileButton != null) {
            mTileButton.setOnClickListener(this);
        }

        batteryInfoReceiver.setInfoListener(new BatteryInfoReceiver.BatteryInfoListener() {
            @Override
            public void info(int level) {
                mLevel = level;
                layerDrawable.setLevel(mLevel * 100);
                setBattery();
                //Log.e("level ", mLevel + "%");
            }
        });
    }

    private void startBatteryService() {
        Intent intent = new Intent(this, BatteryService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        startBatteryService();
        Snackbar.make(mImageBattery, "Battery info had show in quick setting", Snackbar.LENGTH_LONG)
                .show();
    }

    private void setBattery() {
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String level = mLevel + "%";

        mImageBattery.buildDrawingCache();
        Bitmap bitmap = mImageBattery.getDrawingCache();
        mLevelText.setText(level);

        CustomTile mCustomTile = new CustomTile.Builder(this)
                .setOnClickIntent(pendingIntent)
                .setContentDescription("Battery Life")
                .setLabel(level)
                .shouldCollapsePanel(false)
                .setIcon(bitmap)
                .build();

        CMStatusBarManager.getInstance(this)
                .publishTile(1, mCustomTile);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
