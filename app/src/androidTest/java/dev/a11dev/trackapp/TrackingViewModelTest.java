package dev.a11dev.trackapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class TrackingViewModelTest {

    private TrackingViewModel trackingViewModel;

    @Mock
    private Observer<List<String>> observer;

    // Regola per assicurarsi che i test del LiveData vengano eseguiti immediatamente
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        trackingViewModel = new TrackingViewModel(new android.app.Application());
        trackingViewModel.getCoordinatesLiveData().observeForever(observer);
    }

    @Test
    public void testAddCoordinate() {
        // Aggiungi una coordinata al ViewModel
        String coordinate = "Lat: 12.34, Lng: 56.78";
        trackingViewModel.addCoordinate(coordinate);

        // Verifica che la coordinata sia stata aggiunta
        List<String> expectedList = new ArrayList<>();
        expectedList.add(coordinate);

        assertEquals(expectedList, trackingViewModel.getCoordinatesLiveData().getValue());
        verify(observer).onChanged(expectedList);
    }
}
