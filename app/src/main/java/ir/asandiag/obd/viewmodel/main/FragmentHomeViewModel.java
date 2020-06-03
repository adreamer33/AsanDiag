package ir.asandiag.obd.viewmodel.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentHomeViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isBackPressed = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isCompanyList = new MutableLiveData<>(true);

    public FragmentHomeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> isCompanyList() {
        return isCompanyList;
    }

    public void setIsCompanyList(boolean companyList) {
        isCompanyList.setValue(companyList);
    }

    public MutableLiveData<Boolean> getIsBackPressed() {
        return isBackPressed;
    }

    public void setIsBackPressed(boolean isBackPressed) {
        this.isBackPressed.setValue(isBackPressed);
    }
}