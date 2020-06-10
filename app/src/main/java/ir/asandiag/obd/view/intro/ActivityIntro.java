package ir.asandiag.obd.view.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.Log;

import ir.asandiag.obd.R;
import ir.asandiag.pageindicatorview.PageIndicatorView;

public class ActivityIntro extends AppCompatActivity {
    private PageIndicatorView pageIndicatorView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_intro_host_fragment);
        navController = navHostFragment.getNavController();

        pageIndicatorView = findViewById(R.id.piv_intro);
        pageIndicatorView.setCount(3);
        pageIndicatorView.setSelection(0);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getLabel() != null) {
                switch (destination.getLabel().toString()) {
                    case "intro1":
                        pageIndicatorView.setSelection(2);
                        break;
                    case "intro2":
                        pageIndicatorView.setSelection(1);
                        break;
                    case "intro3":
                        pageIndicatorView.setSelection(0);
                        break;
                }
            }
        });
    }
}