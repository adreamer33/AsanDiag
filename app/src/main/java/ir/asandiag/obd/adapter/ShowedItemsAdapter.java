package ir.asandiag.obd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.asandiag.obd.R;
import ir.asandiag.obd.model.ShowedItem;

public class ShowedItemsAdapter extends ListAdapter<ShowedItem, ShowedItemsAdapter.ViewHolder> {

    private OnShowedItemClickListener onShowedItemClickListener;

    public interface OnShowedItemClickListener {
        void onShowedItemClicked(ShowedItem item, int pos);
    }

    public ShowedItemsAdapter(@NonNull DiffUtil.ItemCallback<ShowedItem> diffCallback) {
        super(diffCallback);
    }

    public ShowedItemsAdapter(@NonNull DiffUtil.ItemCallback<ShowedItem> diffCallback, OnShowedItemClickListener onShowedItemClickListener) {
        super(diffCallback);
        this.onShowedItemClickListener = onShowedItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showed, parent, false);
        ViewCompat.setLayoutDirection(v, ViewCompat.LAYOUT_DIRECTION_LTR);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowedItem item = getItem(position);
        if (item != null) {
            holder.item = item;
            holder.position = position;
            ((AppCompatTextView) holder.itemView.findViewById(R.id.tv_item_showed_title)).setText(item.getName());

            if (item.getDescription().length() > 0) {
                ((AppCompatTextView) holder.itemView.findViewById(R.id.tv_item_showed_desc)).setVisibility(View.VISIBLE);
                ((AppCompatTextView) holder.itemView.findViewById(R.id.tv_item_showed_desc)).setText(item.getDescription());
            } else {
                ((AppCompatTextView) holder.itemView.findViewById(R.id.tv_item_showed_desc)).setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(v -> {
                if (onShowedItemClickListener != null) {
                    onShowedItemClickListener.onShowedItemClicked(holder.item, position);
                }
            });

            ((SwitchCompat) holder.itemView.findViewById(R.id.switch_item_showed_state)).setChecked(item.isEnabled());
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShowedItem item;
        private int position = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemDiffCallBack extends DiffUtil.ItemCallback<ShowedItem> {

        @Override
        public boolean areItemsTheSame(@NonNull ShowedItem oldItem, @NonNull ShowedItem newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShowedItem oldItem, @NonNull ShowedItem newItem) {
            return false;
        }
    }

}
