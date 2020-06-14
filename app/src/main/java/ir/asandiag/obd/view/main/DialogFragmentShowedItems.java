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
import ir.asandiag.obd.adapter.ShowedItemsAdapter;
import ir.asandiag.obd.model.ShowedItem;

public class DialogFragmentShowedItems extends BottomSheetDialogFragment implements ShowedItemsAdapter.OnShowedItemClickListener {
    private NavController navController;
    private final List<ShowedItem> itemList = new ArrayList<>();

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    public DialogFragmentShowedItems() {
        // Required empty public constructor
    }

    @NonNull
    public static DialogFragmentShowedItems newInstance(String param1, String param2) {
        DialogFragmentShowedItems fragment = new DialogFragmentShowedItems();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        ShowedItem gasD = new ShowedItem(0, "دریچه گاز", "توضیحات مربوط به این آیتم در صورت وجود");
        ShowedItem gasV = new ShowedItem(1, "ولتاژ دریچه گاز", "", true);
        ShowedItem maf = new ShowedItem(2, "MAF", "");
        itemList.add(gasD);
        itemList.add(gasV);
        itemList.add(maf);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_showed_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.rv_showed_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ShowedItemsAdapter(new ShowedItemsAdapter.ItemDiffCallBack(), this));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(-10));
        ((ShowedItemsAdapter) recyclerView.getAdapter()).submitList(itemList);
        navController = NavHostFragment.findNavController(this);
    }


    @Override
    public void onShowedItemClicked(ShowedItem item, int pos) {

    }
}