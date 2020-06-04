package ir.asandiag.obd.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_main_host_fragment);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.d("debug", "onCreate: " + destination.getLabel() + ":" + model.state.getValue());
            if (destination.getLabel().toString().equals("home") && model.state.getValue() > 1) {
                model.state.setValue(1);
            }

        });


    }

    @Override
    public void onBackPressed() {
        Log.d("debug", "onBackPressed: " + navController.getCurrentDestination().getLabel() + ":" + model.state.getValue());
        if (model != null) {
            switch (model.state.getValue()) {
                case 0:
                    super.onBackPressed();
                    break;
                case 1:
                    model.state.setValue(0);
                    break;
                default:
                    model.state.setValue(model.state.getValue() - 1);
                    navController.navigateUp();
            }
        } else {
            super.onBackPressed();
        }
    }
}