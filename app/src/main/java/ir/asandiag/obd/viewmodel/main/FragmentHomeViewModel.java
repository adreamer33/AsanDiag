package ir.asandiag.obd.viewmodel.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentHomeViewModel extends AndroidViewModel {
    public MutableLiveData<Integer> state = new MutableLiveData<>(0);

    public FragmentHomeViewModel(@NonNull Application application) {
        super(application);
    }

}