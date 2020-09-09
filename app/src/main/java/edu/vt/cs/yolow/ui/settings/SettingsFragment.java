package edu.vt.cs.yolow.ui.settings;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;

import edu.vt.cs.yolow.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends Fragment implements ServiceConnection {
    public interface FragmentSettings {
        BluetoothDevice getBtDevice();
    }

    private MetaWearBoard metawear = null;
    private FragmentSettings settings;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity owner= getActivity();
        if (!(owner instanceof FragmentSettings)) {
            throw new ClassCastException("Owning activity must implement the FragmentSettings interface");
        }

        settings= (FragmentSettings) owner;
        owner.getApplicationContext().bindService(new Intent(owner, BtleService.class), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ///< Unbind the service when the activity is destroyed
        getActivity().getApplicationContext().unbindService(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        //init UI
//        devices_header = view.findViewById(R.id.header_devices_connected);
//        scan = view.findViewById(R.id.button_scan_devices);
//
//        //init shared view model
//        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        devices_header.setText("Devices connected: (" + viewModel.getNumSensors() + ")");
//
//        viewModel.getNumSensors().observe(getViewLifecycleOwner(), ((item) -> {
//                devices_header.setText("Devices connected: (" + item + ")");
//        }));
//    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        metawear = ((BtleService.LocalBinder) service).getMetaWearBoard(settings.getBtDevice());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    /**
     * Called when the app has reconnected to the board
     */
    public void reconnected() { }
}