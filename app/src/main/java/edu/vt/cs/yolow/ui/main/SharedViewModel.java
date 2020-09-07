package edu.vt.cs.yolow.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<String>> sensorList = new MutableLiveData<>();
    private MutableLiveData<Integer> sensorListLength = new MutableLiveData<>(0);

    public void addSensor(String id) {
        sensorList.getValue().add(id);
        sensorListLength.setValue(sensorListLength.getValue() + 1);
    }

    public LiveData<List<String>> getList() {
        return sensorList;
    }

    public LiveData<Integer> getNumSensors() {
        return sensorListLength;
    }

}
