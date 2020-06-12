package ir.asandiag.obd.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.asandiag.obd.R;
import ir.asandiag.obd.model.SortItem;

public class SortAdapter extends ListAdapter<SortItem, SortAdapter.ViewHolder> {

    private OnSortItemClickListener onSortItemClickListener;

    public interface OnSortItemClickListener {
        void onCarPartClicked(SortItem sortItem, int pos);
    }

    public SortAdapter(@NonNull DiffUtil.ItemCallback<SortItem> diffCallback) {
        super(diffCallback);
    }

    public SortAdapter(@NonNull DiffUtil.ItemCallback<SortItem> diffCallback, OnSortItemClickListener onSortItemClickListener) {
        super(diffCallback);
        this.onSortItemClickListener = onSortItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort, parent, false);
        ViewCompat.setLayoutDirection(v, ViewCompat.LAYOUT_DIRECTION_LTR);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SortItem sortItem = getItem(position);
        if (sortItem != null) {
            holder.sortItem = sortItem;
            holder.position = position;
            AppCompatTextView content = holder.itemView.findViewById(R.id.tv_item_sort_title);
            content.setText(sortItem.getName());
            holder.itemView.setOnClickListener(v -> {
                if (onSortItemClickListener != null) {
                    onSortItemClickListener.onCarPartClicked(holder.sortItem, position);
                }
            });
            Drawable img = null;
            switch (holder.sortItem.getId()) {
                case 0:
                    img = AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.ic_sort_alpha_down);
                    break;
                case 1:
                    img = AppCompatResources.getDrawable(holder.itemView.getContext(),R.drawable.ic_sort_amount_down);
                    break;
                case 2:
                    img = AppCompatResources.getDrawable(holder.itemView.getContext(),R.drawable.ic_sort_ecu);
                    break;
            }
            if (img != null) {
                img.setBounds(0, 0, (int) dp2px(holder.itemView.getContext(), 20), (int) dp2px(holder.itemView.getContext(), 20));
            }
            content.setCompoundDrawables(null, null, img, null);
        }


    }

    public float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SortItem sortItem;
        private int position = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemDiffCallBack extends DiffUtil.ItemCallback<SortItem> {

        @Override
        public boolean areItemsTheSame(@NonNull SortItem oldItem, @NonNull SortItem newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SortItem oldItem, @NonNull SortItem newItem) {
            return false;
        }
    }

}
