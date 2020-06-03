package ir.asandiag.obd.view.intro;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ir.asandiag.obd.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentIntro1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIntro1 extends Fragment {

    private NavController navController;
    private AppCompatButton btn_intro1_next;
    public FragmentIntro1() {
        // Required empty public constructor
    }

    public static FragmentIntro1 newInstance(String param1, String param2) {
        FragmentIntro1 fragment = new FragmentIntro1();
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
        View view = inflater.inflate(R.layout.fragment_intro1, container, false);
        btn_intro1_next = view.findViewById(R.id.btn_intro1_next);
        navController = NavHostFragment.findNavController(this);
        btn_intro1_next.setOnClickListener(v -> {
            navController.navigate(FragmentIntro1Directions.actionFragmentIntro1ToFragmentIntro2());
        });
        return view;
    }
}