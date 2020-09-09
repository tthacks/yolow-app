package edu.vt.cs.yolow.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
