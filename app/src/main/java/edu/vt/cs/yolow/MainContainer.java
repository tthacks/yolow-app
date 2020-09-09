package edu.vt.cs.yolow;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.IBinder;

import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;
import edu.vt.cs.yolow.ui.main.SectionsPagerAdapter;

import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.mbientlab.bletoolbox.scanner.BleScannerFragment;

import java.util.UUID;

import bolts.Task;

public class MainContainer extends AppCompatActivity implements BleScannerFragment.ScannerCommunicationBus, ServiceConnection {
    public static final int REQUEST_START_APP= 1;

    private BtleService.LocalBinder serviceBinder;
    private MetaWearBoard metawear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        getApplicationContext().bindService(new Intent(this, BtleService.class), this, BIND_AUTO_CREATE);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ///< Unbind the service when the activity is destroyed
        getApplicationContext().unbindService(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_START_APP:
                ((BleScannerFragment) getFragmentManager().findFragmentById(R.id.scanner_fragment)).startBleScan();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public UUID[] getFilterServiceUuids() {
        return new UUID[] {MetaWearBoard.METAWEAR_GATT_SERVICE};
    }

    @Override
    public long getScanDuration() {
        return 10000L;
    }

    @Override
    public void onDeviceSelected(final BluetoothDevice device) {
        metawear = serviceBinder.getMetaWearBoard(device);

        final ProgressDialog connectDialog = new ProgressDialog(this);
        connectDialog.setTitle(getString(R.string.title_connecting));
        connectDialog.setMessage(getString(R.string.message_wait));
        connectDialog.setCancelable(false);
        connectDialog.setCanceledOnTouchOutside(false);
        connectDialog.setIndeterminate(true);
        connectDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), (dialogInterface, i) -> metawear.disconnectAsync());
        connectDialog.show();

        metawear.connectAsync().continueWithTask(task -> task.isCancelled() || !task.isFaulted() ? task : reconnect(metawear))
                .continueWith(task -> {
                    if (!task.isCancelled()) {
                        runOnUiThread(connectDialog::dismiss);
                        Intent navActivityIntent = new Intent(MainContainer.this, DeviceSetupActivity.class);
                        navActivityIntent.putExtra(DeviceSetupActivity.EXTRA_BT_DEVICE, device);
                        startActivityForResult(navActivityIntent, REQUEST_START_APP);
                    }

                    return null;
                });
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        serviceBinder = (BtleService.LocalBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public static Task<Void> reconnect(final MetaWearBoard board) {
        return board.connectAsync().continueWithTask(task -> task.isFaulted() ? reconnect(board) : task);
    }
}
