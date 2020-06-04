package ir.asandiag.obd.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.asandiag.obd.R;
import ir.asandiag.obd.model.CarPartItem;

public class CarPartAdapter extends ListAdapter<CarPartItem, CarPartAdapter.ViewHolder> {

    private OnCarPartClickListener onCarPartClickListener;

    public interface OnCarPartClickListener {
        void onCarPartClicked(CarPartItem carPartItem, int pos);
    }

    public CarPartAdapter(@NonNull DiffUtil.ItemCallback<CarPartItem> diffCallback) {
        super(diffCallback);
    }

    public CarPartAdapter(@NonNull DiffUtil.ItemCallback<CarPartItem> diffCallback, OnCarPartClickListener onCarPartClickListener) {
        super(diffCallback);
        this.onCarPartClickListener = onCarPartClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_part, parent, false);
        ViewCompat.setLayoutDirection(v, ViewCompat.LAYOUT_DIRECTION_LTR);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarPartItem carPartItem = getItem(position);
        if (carPartItem != null) {
            holder.carPartItem = carPartItem;
            holder.position = position;
            ((AppCompatButton) holder.itemView).setText(carPartItem.getName());
            holder.itemView.setOnClickListener(v -> {
                if (onCarPartClickListener != null) {
                    onCarPartClickListener.onCarPartClicked(holder.carPartItem, position);
                }
            });
            Drawable img = null;
            switch (holder.carPartItem.getId()) {
                case 0:
                    img = holder.itemView.getContext().getResources().getDrawable(R.drawable.ic_motor_small);
                    break;
                case 1:
                    img = holder.itemView.getContext().getResources().getDrawable(R.drawable.ic_antitheft);
                    break;
                case 2:
                    img = holder.itemView.getContext().getResources().getDrawable(R.drawable.ic_gearshift);
                    break;
            }
            img.setBounds(0, 0, (int) dp2px(holder.itemView.getContext(),20), (int) dp2px(holder.itemView.getContext(),20));
            ((AppCompatButton) holder.itemView).setCompoundDrawables(null, null, img, null);

        }


    }
    public float dp2px(Context context,float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CarPartItem carPartItem;
        private int position = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemDiffCallBack extends DiffUtil.ItemCallback<CarPartItem> {

        @Override
        public boolean areItemsTheSame(@NonNull CarPartItem oldItem, @NonNull CarPartItem newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CarPartItem oldItem, @NonNull CarPartItem newItem) {
            return false;
        }
    }

}
