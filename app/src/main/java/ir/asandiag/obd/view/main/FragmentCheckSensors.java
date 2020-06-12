package ir.asandiag.obd.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import ir.asandiag.obd.R;
import ir.asandiag.obd.adapter.EqualSpacingItemDecoration;
import ir.asandiag.obd.adapter.SensorItemAdapter;
import ir.asandiag.obd.model.SensorItem;

public class FragmentCheckSensors extends BottomSheetDialogFragment implements SensorItemAdapter.OnSensorItemClickListener {
    private NavController navController;
    private List<SensorItem> sensorItems = new ArrayList<>();
    private AppCompatImageButton imgBtnBack;
    private AppCompatImageButton imgBtnMore;
    private AppCompatImageButton fab;
    private boolean paused = true;

    public FragmentCheckSensors() {
        // Required empty public constructor
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    public static FragmentCheckSensors newInstance(String param1, String param2) {
        FragmentCheckSensors fragment = new FragmentCheckSensors();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        for (int i = 0; i < 10; i++) {
            SensorItem item = new SensorItem(i, "زاویه دریچه گاز");
            sensorItems.add(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_sensors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);


        final RecyclerView recyclerView = view.findViewById(R.id.rv_check_sensors_items);
        imgBtnBack = view.findViewById(R.id.imgBtn_check_sensors_back);
        imgBtnMore = view.findViewById(R.id.imgBtn_check_sensors_more);
        fab = view.findViewById(R.id.fab_check_sensors_pause);

        fab.setOnClickListener(v -> {
            if (paused) {
                fab.setImageResource(R.drawable.ic_play);
            } else {
                fab.setImageResource(R.drawable.ic_pause);
            }
            paused = !paused;
        });
        imgBtnMore.setOnClickListener(v -> {
            if (navController != null) {
                navController.navigate(FragmentCheckSensorsDirections.actionFragmentCheckSensorsToDialogFragmentSensorsMore());
            }
        });

        imgBtnBack.setOnClickListener(v -> {
            if (navController != null) {
                navController.navigateUp();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(-10));
        recyclerView.setAdapter(new SensorItemAdapter(new SensorItemAdapter.ItemDiffCallBack(), this));
        ((SensorItemAdapter) recyclerView.getAdapter()).submitList(sensorItems);
    }

    @Override
    public void onSensorItemClicked(SensorItem sensorItem, int pos) {
        Toast.makeText(getContext(), sensorItem.getName(), Toast.LENGTH_SHORT).show();
    }
}