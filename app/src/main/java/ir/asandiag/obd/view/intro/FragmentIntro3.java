package ir.asandiag.obd.view.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ir.asandiag.obd.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentIntro3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIntro3 extends Fragment {

    private NavController navController;
    private AppCompatButton btn_intro3_login;

    public FragmentIntro3() {
        // Required empty public constructor
    }

    public static FragmentIntro3 newInstance(String param1, String param2) {
        FragmentIntro3 fragment = new FragmentIntro3();
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
        View view = inflater.inflate(R.layout.fragment_intro3, container, false);
        btn_intro3_login = view.findViewById(R.id.btn_intro3_login);
        navController = NavHostFragment.findNavController(this);
        btn_intro3_login.setOnClickListener(v -> {
            navController.navigate(FragmentIntro3Directions.actionFragmentIntro3ToActivitySignUp());
            requireActivity().finish();
        });
        return view;
    }
}