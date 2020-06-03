package ir.asandiag.obd.view.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.asandiag.obd.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLoading#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLoading extends Fragment {
    private NavController navController;

    public FragmentLoading() {
        // Required empty public constructor
    }

    public static FragmentLoading newInstance(String param1, String param2) {
        FragmentLoading fragment = new FragmentLoading();
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
    public void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            navController.navigate(FragmentLoadingDirections.actionFragmentLoadingToActivityMain());
            requireActivity().finish();
        }, 700);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }
}