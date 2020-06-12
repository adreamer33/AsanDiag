package ir.asandiag.obd.view.main;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.motion.widget.MotionLayout;
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
import ir.asandiag.obd.adapter.EqualSpacingItemDecoration;
import ir.asandiag.obd.model.CarItem;
import ir.asandiag.obd.model.CompanyItem;
import ir.asandiag.obd.viewmodel.main.FragmentHomeViewModel;

public class FragmentHome extends Fragment implements CompanyAdapter.OnCompanyItemClickListener, CarAdapter.OnCarItemClickListener {

    private FragmentHomeViewModel mViewModel;
    private RecyclerView rvItems;
    private NavController navController;
    private static AppCompatImageButton imgBtnSort;
    private static AppCompatImageButton imgBtnMenu;
    private static AppCompatEditText etSearch;
    private static AppCompatImageView imgSearch;
    private static MotionLayout motionLayout;

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
        motionLayout = (MotionLayout) view;
        rvItems = view.findViewById(R.id.rv_home_items);
        imgBtnSort = view.findViewById(R.id.imgbtn_home_sort);
        imgBtnMenu = view.findViewById(R.id.imgbtn_home_menu);
        etSearch = view.findViewById(R.id.et_home_search);
        imgSearch = view.findViewById(R.id.img_home_search);

        navController = NavHostFragment.findNavController(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvItems.setLayoutManager(gridLayoutManager);
        rvItems.setNestedScrollingEnabled(false);
        // For example 10 pixels
        int spaceInPixels = 10;
        rvItems.setHasFixedSize(true);
        rvItems.addItemDecoration(new EqualSpacingItemDecoration(-spaceInPixels));
        initRecyclerView();

        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                mViewModel.motionState.setValue(i);
                switch (i) {
                    case R.id.items:
                        imgBtnSort.setEnabled(true);
                        break;
                    case R.id.items_no_obd:
                        imgBtnSort.setEnabled(true);
                        break;
                    case R.id.items_search:
                        imgBtnSort.setEnabled(false);
                        break;
                    case R.id.items_search_no_obd:
                        imgBtnSort.setEnabled(false);
                        break;
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });


        imgBtnSort.setOnClickListener(v -> {
            if (navController != null) {
//                if (((NeumorphImageButton) v).getShapeType() == ShapeType.FLAT) {
//                    ((NeumorphImageButton) v).setShapeType(ShapeType.PRESSED);
//                } else {
//                    ((NeumorphImageButton) v).setShapeType(ShapeType.FLAT);
//                }
                navController.navigate(FragmentHomeDirections.actionHomeToDialogFragmentSort());
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    imgSearch.setImageResource(R.drawable.ic_arrow_right_24);
                } else {
                    imgSearch.setImageResource(R.drawable.ic_search);
                }
            }
        });

        imgSearch.setOnClickListener(v -> {
            switch (motionLayout.getCurrentState()) {
                case R.id.items:
                    showSearch(R.id.items, true);
                    break;
                case R.id.items_no_obd:
                    showSearch(R.id.items_no_obd, false);
                    break;
                case R.id.items_search:
                    hideSearch(R.id.items_search, true);
                    break;
                case R.id.items_search_no_obd:
                    hideSearch(R.id.items_search_no_obd, false);
                    break;
                default:
            }
        });

        imgBtnMenu.setOnClickListener(v -> {
            try {
                ((ActivityMain) requireActivity()).openDrawer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void showSearch(int startDest, boolean obd_visible) {
        int endDest = obd_visible ? R.id.items_search : R.id.items_search_no_obd;
        motionLayout.setTransition(startDest, endDest);
        motionLayout.transitionToEnd();
        etSearch.setText(null);
        etSearch.setEnabled(true);
        etSearch.setVisibility(View.VISIBLE);
    }

    public static void hideSearch(int startDest, boolean obd_visible) {
        int endDest = obd_visible ? R.id.items : R.id.items_no_obd;
        motionLayout.setTransition(startDest, endDest);
        motionLayout.transitionToEnd();
        imgSearch.setImageResource(R.drawable.ic_search);
        etSearch.setText(null);
        etSearch.setEnabled(false);
        etSearch.setVisibility(View.INVISIBLE);
    }

    private void initRecyclerView() {
        if (mViewModel == null || mViewModel.state.getValue() == 0) {
            transitionToCompanies();
            rvItems.setAdapter(companyAdapter);
            if (companyItems != null && rvItems != null && rvItems.getAdapter() != null) {
                if (((CompanyAdapter) rvItems.getAdapter()).getItemCount() == 0) {
                    ((CompanyAdapter) rvItems.getAdapter()).submitList(companyItems);
                }
            }
        } else {
            transitionToCars();
            rvItems.setAdapter(carAdapter);
            if (carItems != null && rvItems != null && rvItems.getAdapter() != null) {
                if (((CarAdapter) rvItems.getAdapter()).getItemCount() == 0) {
                    ((CarAdapter) rvItems.getAdapter()).submitList(carItems);
                }
            }
        }
    }

    private void transitionToCompanies() {
        switch (motionLayout.getCurrentState()) {
            case R.id.items_no_obd:
                motionLayout.setTransition(R.id.items_no_obd, R.id.items);
                motionLayout.transitionToEnd();
                break;
            case R.id.items_search_no_obd:
                motionLayout.setTransition(R.id.items_search_no_obd, R.id.items);
                motionLayout.transitionToEnd();
                break;
            default:
        }
    }

    private void transitionToCars() {
        switch (motionLayout.getCurrentState()) {
            case R.id.items:
                motionLayout.setTransition(R.id.items, R.id.items_no_obd);
                motionLayout.transitionToEnd();
                break;
            case R.id.items_search:
                motionLayout.setTransition(R.id.items_search, R.id.items_no_obd);
                motionLayout.transitionToEnd();
                break;
            default:
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 24; i++) {
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