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
import ir.asandiag.obd.model.SensorItem;

public class SensorItemAdapter extends ListAdapter<SensorItem, SensorItemAdapter.ViewHolder> {

    private OnSensorItemClickListener onSensorItemClickListener;

    public interface OnSensorItemClickListener {
        void onSensorItemClicked(SensorItem sensorItem, int pos);
    }

    public SensorItemAdapter(@NonNull DiffUtil.ItemCallback<SensorItem> diffCallback) {
        super(diffCallback);
    }

    public SensorItemAdapter(@NonNull DiffUtil.ItemCallback<SensorItem> diffCallback, OnSensorItemClickListener onSensorItemClickListener) {
        super(diffCallback);
        this.onSensorItemClickListener = onSensorItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor, parent, false);
        ViewCompat.setLayoutDirection(v, ViewCompat.LAYOUT_DIRECTION_LTR);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SensorItem carPartItem = getItem(position);
        if (carPartItem != null) {
            holder.sensorItem = carPartItem;
            holder.position = position;
//            ((AppCompatTextView) holder.itemView.findViewById(R.id.tv_item_sensor_title)).setText(carPartItem.getName());
            holder.itemView.setOnClickListener(v -> {
                if (onSensorItemClickListener != null) {
                    onSensorItemClickListener.onSensorItemClicked(holder.sensorItem, position);
                }
            });

        }


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SensorItem sensorItem;
        private int position = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemDiffCallBack extends DiffUtil.ItemCallback<SensorItem> {

        @Override
        public boolean areItemsTheSame(@NonNull SensorItem oldItem, @NonNull SensorItem newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SensorItem oldItem, @NonNull SensorItem newItem) {
            return false;
        }
    }

}
