package ir.asandiag.obd.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import ir.asandiag.obd.adapter.SortAdapter;
import ir.asandiag.obd.model.SortItem;

public class DialogFragmentSort extends BottomSheetDialogFragment implements SortAdapter.OnSortItemClickListener {

    private NavController navController;
    private List<SortItem> sortItems = new ArrayList<>();

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    public DialogFragmentSort() {
        // Required empty public constructor
    }

    public static DialogFragmentSort newInstance(String param1, String param2) {
        DialogFragmentSort fragment = new DialogFragmentSort();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        SortItem alpha = new SortItem(0, "بر اساس حروف الفبا");
        SortItem most_used = new SortItem(1, "بر اساس بیش‌ترین استفاده");
        SortItem ecu = new SortItem(2, "بر اساس فراوانی ECU");

        sortItems.add(alpha);
        sortItems.add(most_used);
        sortItems.add(ecu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_sort, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.rv_dialog_fragment_sort_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SortAdapter(new SortAdapter.ItemDiffCallBack(), this));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(-10));
        ((SortAdapter) recyclerView.getAdapter()).submitList(sortItems);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public void onCarPartClicked(SortItem sortItem, int pos) {
        Toast.makeText(getContext(), sortItem.getName(), Toast.LENGTH_SHORT).show();
    }
}