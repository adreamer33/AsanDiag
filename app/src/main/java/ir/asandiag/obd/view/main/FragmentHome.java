package ir.asandiag.obd.view.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.asandiag.obd.adapter.CarAdapter;
import ir.asandiag.obd.adapter.CompanyAdapter;
import ir.asandiag.obd.model.CarItem;
import ir.asandiag.obd.model.CompanyItem;
import ir.asandiag.obd.viewmodel.main.FragmentHomeViewModel;
import ir.asandiag.obd.R;

public class FragmentHome extends Fragment implements CompanyAdapter.OnCompanyItemClickListener, CarAdapter.OnCarItemClickListener {

    private FragmentHomeViewModel mViewModel;
    private RecyclerView rvItems;

    private List<CompanyItem> companyItems = new ArrayList<>();
    private List<CarItem> carItems = new ArrayList<>();


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
        for (int i = 0; i < 10; i++) {
            CompanyItem companyItem = new CompanyItem(i, "کیا موتور", "اپتیما، اسپورتیج و ..");
            companyItems.add(companyItem);

            CarItem carItem = new CarItem(i, "پژو پارس");
            carItems.add(carItem);
        }
        mViewModel = new ViewModelProvider(requireActivity()).get(FragmentHomeViewModel.class);

        mViewModel.getIsBackPressed().observe(getViewLifecycleOwner(), backPressed -> {
            if (backPressed) {
                mViewModel.setIsCompanyList(true);
                initRecyclerView();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvItems = view.findViewById(R.id.rv_home_items);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvItems.setLayoutManager(gridLayoutManager);
        rvItems.setNestedScrollingEnabled(false);

        initRecyclerView();
    }

    private void initRecyclerView() {
        if (mViewModel==null || mViewModel.isCompanyList().getValue()) {
            rvItems.setAdapter(new CompanyAdapter(new CompanyAdapter.ItemDiffCallBack(), this));
            if (companyItems != null && rvItems != null && rvItems.getAdapter() != null) {
                ((CompanyAdapter) rvItems.getAdapter()).submitList(companyItems);
            }
        } else {
            rvItems.setAdapter(new CarAdapter(new CarAdapter.ItemDiffCallBack(), this));
            if (carItems != null && rvItems != null && rvItems.getAdapter() != null) {
                ((CarAdapter) rvItems.getAdapter()).submitList(carItems);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCompanyItemClicked(CompanyItem companyItem, int pos) {
        mViewModel.setIsCompanyList(false);
        initRecyclerView();
    }

    @Override
    public void onCarItemClicked(CarItem carItem, int pos) {
        Toast.makeText(requireActivity(), pos + "", Toast.LENGTH_SHORT).show();
    }
}