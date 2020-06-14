package ir.asandiag.obd.viewmodel.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class FragmentHomeViewModel extends AndroidViewModel {
    @NonNull
    public MutableLiveData<Integer> state = new MutableLiveData<>(0);
    @NonNull
    public MutableLiveData<Integer> motionState = new MutableLiveData<>(0);

    public FragmentHomeViewModel(@NonNull Application application) {
        super(application);
    }

}