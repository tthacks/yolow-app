package edu.vt.cs.yolow.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import edu.vt.cs.yolow.R;
import edu.vt.cs.yolow.ui.main.SharedViewModel;

public class SettingsFragment extends Fragment {

    private SharedViewModel viewModel;
    private TextView devices_header;
    private Button scan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init UI
        devices_header = view.findViewById(R.id.header_devices_connected);
        scan = view.findViewById(R.id.button_scan_devices);

        //init shared view model
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        devices_header.setText("Devices connected: (" + viewModel.getNumSensors() + ")");

        //observers and listeners
        scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        viewModel.getNumSensors().observe(getViewLifecycleOwner(), ((item) -> {
                devices_header.setText("Devices connected: (" + item + ")");
        }));
    }
}