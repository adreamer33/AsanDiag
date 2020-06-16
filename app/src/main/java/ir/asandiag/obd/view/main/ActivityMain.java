package ir.asandiag.obd.view.main;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.navigation.NavigationView;

import ir.asandiag.neumorphism.widgetMode.NeumorphCardView;
import ir.asandiag.obd.R;
import ir.asandiag.obd.view.MyActivity;
import ir.asandiag.obd.viewmodel.main.FragmentHomeViewModel;

public class ActivityMain extends MyActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavController navController;
    private FragmentHomeViewModel model;
    private DrawerLayout drawer;
    private NeumorphCardView btnLogOut;
    private NeumorphCardView btnTerminal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new ViewModelProvider(this).get(FragmentHomeViewModel.class);
        drawer = findViewById(R.id.drawer_layout_main);
        btnLogOut = findViewById(R.id.cv_drawer_logout);
        btnTerminal = findViewById(R.id.cv_drawer_terminal);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_main_host_fragment);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.home_fragment) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                if (model.state.getValue() > 1) {
                    model.state.setValue(1);
                }
            } else {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
            Log.d("debug", "onCreate: " + destination.getLabel() + ":" + model.state.getValue());
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_main);
        navigationView.setNavigationItemSelectedListener(this);

        ViewCompat.setLayoutDirection(drawer, ViewCompat.LAYOUT_DIRECTION_RTL);
        ViewCompat.setLayoutDirection(navigationView, ViewCompat.LAYOUT_DIRECTION_LTR);

        btnLogOut.setOnClickListener(v -> {
            if (navController != null) {
                closeDrawer();
                navController.navigate(FragmentHomeDirections.actionHomeFragmentToFragmentLogout());
            }
        });

        btnTerminal.setOnClickListener(v -> {
            darkStateClicked = true;
            darkState = !darkState;
            AppCompatDelegate.setDefaultNightMode(darkState ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            ActivityMain.this.recreate();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onSupportNavigateUp();
        return navController.navigateUp();
    }

    @Override
    public void onBackPressed() {
        Log.d("debug", "onBackPressed: " + navController.getCurrentDestination().getLabel() + ":" + model.state.getValue());
        if (model != null) {
            switch (model.state.getValue()) {
                case 0:
                    if (navController.getCurrentDestination().getLabel().equals("homeFragment")) {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        } else {
                            switch (model.motionState.getValue()) {
                                case R.id.items_search:
                                    FragmentHome.hideSearch(R.id.items_search, true);
                                    break;
                                case R.id.items_search_no_obd:
                                    FragmentHome.hideSearch(R.id.items_search_no_obd, false);
                                    break;
                                default:
                                    super.onBackPressed();
                            }
                        }
                    } else {
                        navController.navigateUp();
                    }
                    break;
                case 1:
                    if (navController.getCurrentDestination().getLabel().equals("homeFragment")) {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        } else {
                            switch (model.motionState.getValue()) {
                                case R.id.items_search:
                                    FragmentHome.hideSearch(R.id.items_search, true);
                                    break;
                                case R.id.items_search_no_obd:
                                    FragmentHome.hideSearch(R.id.items_search_no_obd, false);
                                    break;
                                default:
                                    model.state.setValue(0);
                            }
                        }
                    } else {
                        navController.navigateUp();
                    }
                    break;
                default:
                    model.state.setValue(model.state.getValue() - 1);
                    navController.navigateUp();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void openDrawer() {
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    public void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


}