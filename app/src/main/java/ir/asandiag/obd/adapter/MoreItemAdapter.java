package ir.asandiag.obd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.asandiag.obd.R;
import ir.asandiag.obd.model.MoreItem;

public class MoreItemAdapter extends ListAdapter<MoreItem, MoreItemAdapter.ViewHolder> {

    private OnMoreItemClickListener onMoreItemClickListener;

    public interface OnMoreItemClickListener {
        void onMoreItemClicked(MoreItem item, int pos);
    }

    public MoreItemAdapter(@NonNull DiffUtil.ItemCallback<MoreItem> diffCallback) {
        super(diffCallback);
    }

    public MoreItemAdapter(@NonNull DiffUtil.ItemCallback<MoreItem> diffCallback, OnMoreItemClickListener onMoreItemClickListener) {
        super(diffCallback);
        this.onMoreItemClickListener = onMoreItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more, parent, false);
        ViewCompat.setLayoutDirection(v, ViewCompat.LAYOUT_DIRECTION_LTR);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoreItem item = getItem(position);
        if (item != null) {
            holder.item = item;
            holder.position = position;
            AppCompatTextView content = holder.itemView.findViewById(R.id.tv_item_check_sensor_more_title);
            content.setText(item.getName());
            holder.itemView.setOnClickListener(v -> {
                if (onMoreItemClickListener != null) {
                    onMoreItemClickListener.onMoreItemClicked(holder.item, position);
                }
            });

        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MoreItem item;
        private int position = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemDiffCallBack extends DiffUtil.ItemCallback<MoreItem> {

        @Override
        public boolean areItemsTheSame(@NonNull MoreItem oldItem, @NonNull MoreItem newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MoreItem oldItem, @NonNull MoreItem newItem) {
            return false;
        }
    }

}
