package dev.a11dev.trackapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

public class TrackingViewModel extends AndroidViewModel {

    private final MutableLiveData<List<String>> coordinatesLiveData = new MutableLiveData<>(new ArrayList<>());

    public TrackingViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<String>> getCoordinatesLiveData() {
        return coordinatesLiveData;
    }

    public void addCoordinate(String coordinate) {
        List<String> currentList = coordinatesLiveData.getValue();
        if (currentList != null) {
            currentList.add(coordinate);
            coordinatesLiveData.setValue(currentList);
        }
    }
}
