package ir.asandiag.obd.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.github.anastr.speedviewlib.DeluxeSpeedView;

import ir.asandiag.obd.R;

public class FragmentSpeedView extends Fragment {
    private DeluxeSpeedView speedView;
    private AppCompatImageButton imgBtnBack;
    private NavController navController;


    public FragmentSpeedView() {
        // Required empty public constructor
    }

    @NonNull
    public static FragmentSpeedView newInstance(String param1, String param2) {
        FragmentSpeedView fragment = new FragmentSpeedView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speed_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        speedView = view.findViewById(R.id.speed_view);
        imgBtnBack = view.findViewById(R.id.imgbtn_speed_view_back);

        imgBtnBack.setOnClickListener(v -> {
            if (navController != null) {
                navController.navigateUp();
            }
        });

        speedView.setUnit("Km/h");
        speedView.speedPercentTo(80, 1500);
    }
}