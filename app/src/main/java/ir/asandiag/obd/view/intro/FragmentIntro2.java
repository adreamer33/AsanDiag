package ir.asandiag.obd.view.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ir.asandiag.obd.R;

public class FragmentIntro2 extends Fragment {

    private NavController navController;
    private AppCompatTextView tvLogin;
    private AppCompatButton btn_intro2_next;
    public FragmentIntro2() {
        // Required empty public constructor
    }

    @NonNull
    public static FragmentIntro2 newInstance(String param1, String param2) {
        FragmentIntro2 fragment = new FragmentIntro2();
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

        return inflater.inflate(R.layout.fragment_intro2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_intro2_next = view.findViewById(R.id.btn_intro2_next);
        navController = NavHostFragment.findNavController(this);
        btn_intro2_next.setOnClickListener(v -> {
            navController.navigate(FragmentIntro2Directions.actionFragmentIntro2ToFragmentIntro3());
        });

        tvLogin = view.findViewById(R.id.btn_intro2_login);
        tvLogin.setOnClickListener(v -> {
            navController.navigate(FragmentIntro2Directions.actionFragmentIntro2ToActivitySignUp());
            requireActivity().finish();
        });
    }
}