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
import ir.asandiag.obd.model.CarPartDetailsItem;

public class CarPartDetialAdapter extends ListAdapter<CarPartDetailsItem, CarPartDetialAdapter.ViewHolder> {

    private OnCarPartDetailClickListener onCarPartDetailClickListener;

    public interface OnCarPartDetailClickListener {
        void onCarPartClicked(CarPartDetailsItem carPartDetailsItem, int pos);
    }

    public CarPartDetialAdapter(@NonNull DiffUtil.ItemCallback<CarPartDetailsItem> diffCallback) {
        super(diffCallback);
    }

    public CarPartDetialAdapter(@NonNull DiffUtil.ItemCallback<CarPartDetailsItem> diffCallback, OnCarPartDetailClickListener onCarPartDetailClickListener) {
        super(diffCallback);
        this.onCarPartDetailClickListener = onCarPartDetailClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_part_detail, parent, false);
        ViewCompat.setLayoutDirection(v, ViewCompat.LAYOUT_DIRECTION_LTR);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarPartDetailsItem carPartItem = getItem(position);
        if (carPartItem != null) {
            holder.carPartDetailsItem = carPartItem;
            holder.position = position;
            AppCompatTextView content = holder.itemView.findViewById(R.id.tv_item_car_part_detail_title);
            content.setText(carPartItem.getName());
            holder.itemView.setOnClickListener(v -> {
                if (onCarPartDetailClickListener != null) {
                    onCarPartDetailClickListener.onCarPartClicked(holder.carPartDetailsItem, position);
                }
            });
            Drawable img = null;
            switch (holder.carPartDetailsItem.getType()) {
                case 0:
                    img = AppCompatResources.getDrawable(holder.itemView.getContext(),R.drawable.ic_motor_small);
                    break;
                case 1:
                    img = AppCompatResources.getDrawable(holder.itemView.getContext(),R.drawable.ic_antitheft);
                    break;
                case 2:
                    img = AppCompatResources.getDrawable(holder.itemView.getContext(),R.drawable.ic_gearshift);
                    break;
            }
            if (img != null) {
                img.setBounds(0, 0, (int) dp2px(holder.itemView.getContext(), 20), (int) dp2px(holder.itemView.getContext(), 20));
            }
            content.setCompoundDrawables(null, null, img, null);

        }


    }

    public float dp2px(@NonNull Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CarPartDetailsItem carPartDetailsItem;
        private int position = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemDiffCallBack extends DiffUtil.ItemCallback<CarPartDetailsItem> {

        @Override
        public boolean areItemsTheSame(@NonNull CarPartDetailsItem oldItem, @NonNull CarPartDetailsItem newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CarPartDetailsItem oldItem, @NonNull CarPartDetailsItem newItem) {
            return false;
        }
    }

}
