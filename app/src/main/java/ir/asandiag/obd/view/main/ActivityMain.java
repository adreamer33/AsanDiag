package ir.asandiag.obd.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import ir.asandiag.obd.R;
import ir.asandiag.obd.viewmodel.main.FragmentHomeViewModel;

public class ActivityMain extends AppCompatActivity {

    private NavController navController;
    FragmentHomeViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new ViewModelProvider(this).get(FragmentHomeViewModel.class);


//        navController = Navigation.findNavController(ActivityMain.this, R.id.nav_main_host_fragment);
        model.getIsBackPressed().observe(this,aBoolean -> {
        });
    }

    @Override
    public void onBackPressed() {
        if (model != null) {
            if (!model.isCompanyList().getValue()) {
                model.setIsBackPressed(true);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}