package ir.asandiag.obd.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import ir.asandiag.obd.adapter.CarPartAdapter;
import ir.asandiag.obd.adapter.EqualSpacingItemDecoration;
import ir.asandiag.obd.model.CarPartItem;

public class DialogFragmentCarParts extends BottomSheetDialogFragment implements CarPartAdapter.OnCarPartClickListener {
    private NavController navController;
    private List<CarPartItem> carPartItemList = new ArrayList<>();

    public static DialogFragmentCarParts newInstance(int itemCount) {
        final DialogFragmentCarParts fragment = new DialogFragmentCarParts();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_car_parts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.rv_car_parts_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CarPartAdapter(new CarPartAdapter.ItemDiffCallBack(), this));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(-10));
        ((CarPartAdapter) recyclerView.getAdapter()).submitList(carPartItemList);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public void onCarPartClicked(CarPartItem carPartItem, int pos) {
        if (navController != null) {
            navController.navigate(DialogFragmentCarPartsDirections.actionDialogFragmentCarPartsToDialogFragmentCarPartDetails());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CarPartItem motor = new CarPartItem(0, "موتور");
        CarPartItem antitheft = new CarPartItem(1, "ضد سرقت");
        CarPartItem gearbox = new CarPartItem(2, "گیربکس");
        carPartItemList.add(motor);
        carPartItemList.add(antitheft);
        carPartItemList.add(gearbox);
    }

}