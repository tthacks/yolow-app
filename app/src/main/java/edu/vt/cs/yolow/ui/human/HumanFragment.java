package edu.vt.cs.yolow.ui.human;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.HashMap;

import edu.vt.cs.yolow.R;

public class HumanFragment extends Fragment {

    private HumanViewModel humanViewModel;
    // private HashMap<Integer, TextView> locations;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_human, container, false);
//        locations = new HashMap<>();
//        locations.put(R.id.loc_chest, view.findViewById(R.id.loc_chest));
//        locations.put(R.id.loc_stomach, view.findViewById(R.id.loc_stomach));
//        locations.put(R.id.loc_l_arm_hi, view.findViewById(R.id.loc_l_arm_hi));
//        locations.put(R.id.loc_l_arm_lo, view.findViewById(R.id.loc_l_arm_lo));
//        locations.put(R.id.loc_r_arm_hi, view.findViewById(R.id.loc_r_arm_hi));
//        locations.put(R.id.loc_r_arm_lo, view.findViewById(R.id.loc_r_arm_lo));
//        locations.put(R.id.loc_l_leg_hi, view.findViewById(R.id.loc_l_leg_hi));
//        locations.put(R.id.loc_l_leg_lo, view.findViewById(R.id.loc_l_leg_lo));
//        //locations.put(R.id.loc_r_leg_hi, view.findViewById(R.id.loc_r_leg_hi));
//        locations.put(R.id.loc_r_leg_lo, view.findViewById(R.id.loc_r_leg_lo));
//
//        locations.get(R.id.loc_stomach).setBackgroundColor(0x000000);

        return view;
    }
}