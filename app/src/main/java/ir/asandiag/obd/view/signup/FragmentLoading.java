package ir.asandiag.obd.view.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ir.asandiag.obd.R;

public class FragmentLoading extends Fragment {
    private NavController navController;
    private AppCompatImageView imgLoading;
    private int durationMillis=700;

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
        }, durationMillis);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        imgLoading = view.findViewById(R.id.img_loading);
        Animation anim = AnimationUtils.loadAnimation(requireActivity(), R.anim.rotate);
        anim.setDuration(durationMillis);
        imgLoading.startAnimation(anim);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }
}