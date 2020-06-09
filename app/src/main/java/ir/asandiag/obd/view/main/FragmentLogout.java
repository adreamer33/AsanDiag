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

import ir.asandiag.obd.R;

public class FragmentLogout extends Fragment {

    private AppCompatImageButton imgbtnBack;
    private NavController navController;

    public FragmentLogout() {
        // Required empty public constructor
    }

    public static FragmentLogout newInstance(String param1, String param2) {
        FragmentLogout fragment = new FragmentLogout();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        imgbtnBack = view.findViewById(R.id.imgbtn_logout_back);
        imgbtnBack.setOnClickListener(v -> {
            navController.navigateUp();
        });
    }
}