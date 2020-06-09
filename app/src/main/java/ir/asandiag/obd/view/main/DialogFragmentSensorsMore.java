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
import ir.asandiag.obd.adapter.EqualSpacingItemDecoration;
import ir.asandiag.obd.adapter.MoreItemAdapter;
import ir.asandiag.obd.model.MoreItem;

public class DialogFragmentSensorsMore extends BottomSheetDialogFragment implements MoreItemAdapter.OnMoreItemClickListener {

    private NavController navController;
    private List<MoreItem> itemList = new ArrayList<>();

    public DialogFragmentSensorsMore() {
        // Required empty public constructor
    }

    public static DialogFragmentSensorsMore newInstance(String param1, String param2) {
        DialogFragmentSensorsMore fragment = new DialogFragmentSensorsMore();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        MoreItem hud = new MoreItem(0, "HUD");
        MoreItem hudConfig = new MoreItem(1, "پیکربندی HUD");
        MoreItem showedItems = new MoreItem(2, "موارد نمایش داده شده");
        itemList.add(hud);
        itemList.add(hudConfig);
        itemList.add(showedItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_sensors_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.rv_dialog_fragment_more_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MoreItemAdapter(new MoreItemAdapter.ItemDiffCallBack(), this));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(-10));
        ((MoreItemAdapter) recyclerView.getAdapter()).submitList(itemList);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onMoreItemClicked(MoreItem item, int pos) {
        if (item.getId() == 2 && navController != null) {
            navController.navigate(DialogFragmentSensorsMoreDirections.actionDialogFragmentSensorsMoreToDialogFragmentShowedItems());
        }
    }
}