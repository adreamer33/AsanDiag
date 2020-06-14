package ir.asandiag.obd.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import ir.asandiag.obd.R;
import ir.asandiag.obd.adapter.CarPartDetialAdapter;
import ir.asandiag.obd.adapter.EqualSpacingItemDecoration;
import ir.asandiag.obd.model.CarPartDetailsItem;

public class DialogFragmentCarPartDetails extends BottomSheetDialogFragment implements CarPartDetialAdapter.OnCarPartDetailClickListener {
    private NavController navController;
    private final List<CarPartDetailsItem> carPartDetailsList = new ArrayList<>();


    public DialogFragmentCarPartDetails() {
        // Required empty public constructor
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @NonNull
    public static DialogFragmentCarPartDetails newInstance(String param1, String param2) {
        DialogFragmentCarPartDetails fragment = new DialogFragmentCarPartDetails();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        for (int i = 0; i < 3; i++) {
            CarPartDetailsItem item = new CarPartDetailsItem(0, "زیمنس");
            carPartDetailsList.add(item);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_car_part_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.rv_dialog_fragment_car_part_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CarPartDetialAdapter(new CarPartDetialAdapter.ItemDiffCallBack(), this));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(-10));
        ((CarPartDetialAdapter) recyclerView.getAdapter()).submitList(carPartDetailsList);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public void onCarPartClicked(CarPartDetailsItem carPartDetailsItem, int pos) {
        if (navController != null) {
            navController.navigate(DialogFragmentCarPartDetailsDirections.actionDialogFragmentCarPartDetailsToDialogFragmentCarPartConfigs());
        }
    }
}