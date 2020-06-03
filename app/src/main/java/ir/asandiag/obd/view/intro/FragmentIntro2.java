package ir.asandiag.obd.view.intro;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.asandiag.obd.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentIntro2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIntro2 extends Fragment {

    private NavController navController;
    private AppCompatButton btn_intro2_next;
    public FragmentIntro2() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro2, container, false);
        btn_intro2_next = view.findViewById(R.id.btn_intro2_next);
        navController = NavHostFragment.findNavController(this);
        btn_intro2_next.setOnClickListener(v -> {
            navController.navigate(FragmentIntro2Directions.actionFragmentIntro2ToFragmentIntro3());
        });
        return view;
    }
}