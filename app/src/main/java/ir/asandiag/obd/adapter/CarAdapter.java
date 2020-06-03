package ir.asandiag.obd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.asandiag.obd.R;
import ir.asandiag.obd.model.CarItem;
import ir.asandiag.obd.model.CompanyItem;

public class CarAdapter extends ListAdapter<CarItem, CarAdapter.ViewHolder> {

    private OnCarItemClickListener onCarItemClickListener;

    public interface OnCarItemClickListener {
        void onCarItemClicked(CarItem carItem, int pos);
    }

    public CarAdapter(@NonNull DiffUtil.ItemCallback<CarItem> diffCallback) {
        super(diffCallback);
    }

    public CarAdapter(@NonNull DiffUtil.ItemCallback<CarItem> diffCallback, OnCarItemClickListener onCarItemClickListener) {
        super(diffCallback);
        this.onCarItemClickListener = onCarItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        ViewCompat.setLayoutDirection(v, ViewCompat.LAYOUT_DIRECTION_LTR);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarItem carItem = getItem(position);
        if (carItem != null) {
            holder.carItem = carItem;
            holder.position = position;
            holder.itemView.setOnClickListener(v -> {
                if (onCarItemClickListener != null) {
                    onCarItemClickListener.onCarItemClicked(holder.carItem, position);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CarItem carItem;
        private int position = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemDiffCallBack extends DiffUtil.ItemCallback<CarItem> {

        @Override
        public boolean areItemsTheSame(@NonNull CarItem oldItem, @NonNull CarItem newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CarItem oldItem, @NonNull CarItem newItem) {
            return false;
        }
    }
}
