//package com.mbientlab.metawear.tutorial.starter.deprecated;
//
//import android.app.ProgressDialog;
//import android.bluetooth.BluetoothDevice;
//import android.content.ComponentName;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.IBinder;
//import android.os.Bundle;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//
//import com.mbientlab.bletoolbox.scanner.BleScannerFragment;
//import com.mbientlab.metawear.MetaWearBoard;
//import com.mbientlab.metawear.android.BtleService;
//import com.mbientlab.metawear.tutorial.starter.R;
//
//import java.util.UUID;
//
//import bolts.Task;
//
//public class Main_Activity_starter extends AppCompatActivity implements BleScannerFragment.ScannerCommunicationBus, ServiceConnection {
//    public static final int REQUEST_START_APP= 1;
//
//    private BtleService.LocalBinder serviceBinder;
//    private MetaWearBoard metawear;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_human, R.id.navigation_settings)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
//
//        getApplicationContext().bindService(new Intent(this, BtleService.class), this, BIND_AUTO_CREATE);
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        ///< Unbind the service when the activity is destroyed
//        getApplicationContext().unbindService(this);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch(requestCode) {
//            case REQUEST_START_APP:
//                ((BleScannerFragment) getFragmentManager().findFragmentById(R.id.scanner_fragment)).startBleScan();
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public UUID[] getFilterServiceUuids() {
//        return new UUID[] {MetaWearBoard.METAWEAR_GATT_SERVICE};
//    }
//
//    @Override
//    public long getScanDuration() {
//        return 10000L;
//    }
//
//    @Override
//    public void onDeviceSelected(final BluetoothDevice device) {
//        metawear = serviceBinder.getMetaWearBoard(device);
//
//        final ProgressDialog connectDialog = new ProgressDialog(this);
//        connectDialog.setTitle(getString(R.string.title_connecting));
//        connectDialog.setMessage(getString(R.string.message_wait));
//        connectDialog.setCancelable(false);
//        connectDialog.setCanceledOnTouchOutside(false);
//        connectDialog.setIndeterminate(true);
//        connectDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), (dialogInterface, i) -> metawear.disconnectAsync());
//        connectDialog.show();
//
//        metawear.connectAsync().continueWithTask(task -> task.isCancelled() || !task.isFaulted() ? task : reconnect(metawear))
//                .continueWith(task -> {
//                    if (!task.isCancelled()) {
//                        runOnUiThread(connectDialog::dismiss);
//                        Intent navActivityIntent = new Intent(Main_Activity_starter.this, DeviceSetupActivity.class);
//                        navActivityIntent.putExtra(DeviceSetupActivity.EXTRA_BT_DEVICE, device);
//                        startActivityForResult(navActivityIntent, REQUEST_START_APP);
//                    }
//
//                    return null;
//                });
//    }
//
//    @Override
//    public void onServiceConnected(ComponentName name, IBinder service) {
//        serviceBinder = (BtleService.LocalBinder) service;
//    }
//
//    @Override
//    public void onServiceDisconnected(ComponentName name) {
//
//    }
//
//    public static Task<Void> reconnect(final MetaWearBoard board) {
//        return board.connectAsync().continueWithTask(task -> task.isFaulted() ? reconnect(board) : task);
//    }
//}
