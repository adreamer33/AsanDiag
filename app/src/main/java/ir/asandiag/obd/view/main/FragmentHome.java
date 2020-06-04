package ir.asandiag.obd.view.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.asandiag.obd.R;
import ir.asandiag.obd.adapter.CarAdapter;
import ir.asandiag.obd.adapter.CompanyAdapter;
import ir.asandiag.obd.model.CarItem;
import ir.asandiag.obd.model.CompanyItem;
import ir.asandiag.obd.viewmodel.main.FragmentHomeViewModel;

public class FragmentHome extends Fragment implements CompanyAdapter.OnCompanyItemClickListener, CarAdapter.OnCarItemClickListener {

    private FragmentHomeViewModel mViewModel;
    private RecyclerView rvItems;
    private NavController navController;
    private AppCompatImageButton imgBtnSort;

    private List<CompanyItem> companyItems = new ArrayList<>();
    private List<CarItem> carItems = new ArrayList<>();
    private CompanyAdapter companyAdapter = new CompanyAdapter(new CompanyAdapter.ItemDiffCallBack(), this);
    private CarAdapter carAdapter = new CarAdapter(new CarAdapter.ItemDiffCallBack(), this);


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(FragmentHomeViewModel.class);
        mViewModel.state.observe(getViewLifecycleOwner(), integer -> {
            if (integer == 0 || integer == 1) {
                initRecyclerView();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvItems = view.findViewById(R.id.rv_home_items);
        imgBtnSort = view.findViewById(R.id.imgbtn_home_sort);

        navController = NavHostFragment.findNavController(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvItems.setLayoutManager(gridLayoutManager);
        rvItems.setNestedScrollingEnabled(false);

        initRecyclerView();

        imgBtnSort.setOnClickListener(v -> {
            if (navController != null) {
                navController.navigate(FragmentHomeDirections.actionHomeToDialogFragmentSort());
            }
        });
    }

    private void initRecyclerView() {
        if (mViewModel == null || mViewModel.state.getValue() == 0) {
            rvItems.setAdapter(companyAdapter);
            if (companyItems != null && rvItems != null && rvItems.getAdapter() != null) {
                if (((CompanyAdapter) rvItems.getAdapter()).getItemCount() == 0) {
                    ((CompanyAdapter) rvItems.getAdapter()).submitList(companyItems);
                }
            }
        } else {
            rvItems.setAdapter(carAdapter);
            if (carItems != null && rvItems != null && rvItems.getAdapter() != null) {
                if (((CarAdapter) rvItems.getAdapter()).getItemCount() == 0) {
                    ((CarAdapter) rvItems.getAdapter()).submitList(carItems);
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 10; i++) {
            CompanyItem companyItem = new CompanyItem(i, "کیا موتور", "اپتیما، اسپورتیج و ..");
            companyItems.add(companyItem);

            CarItem carItem = new CarItem(i, "پژو پارس");
            carItems.add(carItem);
        }
    }

    @Override
    public void onCompanyItemClicked(CompanyItem companyItem, int pos) {
        mViewModel.state.setValue(1);
        initRecyclerView();
    }

    @Override
    public void onCarItemClicked(CarItem carItem, int pos) {
        mViewModel.state.setValue(2);
        navController.navigate(FragmentHomeDirections.actionHomeToDialogFragmentCarParts());
    }
}