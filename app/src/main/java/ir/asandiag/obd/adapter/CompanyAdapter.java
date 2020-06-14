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
import ir.asandiag.obd.model.CompanyItem;

public class CompanyAdapter extends ListAdapter<CompanyItem, CompanyAdapter.ViewHolder> {

    private OnCompanyItemClickListener onCompanyItemClickListener;

    public interface OnCompanyItemClickListener {
        void onCompanyItemClicked(CompanyItem companyItem, int pos);
    }

    public CompanyAdapter(@NonNull DiffUtil.ItemCallback<CompanyItem> diffCallback) {
        super(diffCallback);
    }

    public CompanyAdapter(@NonNull DiffUtil.ItemCallback<CompanyItem> diffCallback, OnCompanyItemClickListener onCompanyItemClickListener) {
        super(diffCallback);
        this.onCompanyItemClickListener = onCompanyItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        ViewCompat.setLayoutDirection(v, ViewCompat.LAYOUT_DIRECTION_LTR);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompanyItem companyItem = getItem(position);
        if (companyItem != null) {
            holder.companyItem = companyItem;
            holder.position = position;
            holder.itemView.setOnClickListener(v -> {
                if (onCompanyItemClickListener != null) {
                    onCompanyItemClickListener.onCompanyItemClicked(holder.companyItem, position);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CompanyItem companyItem;
        private int position = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemDiffCallBack extends DiffUtil.ItemCallback<CompanyItem> {

        @Override
        public boolean areItemsTheSame(@NonNull CompanyItem oldItem, @NonNull CompanyItem newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CompanyItem oldItem, @NonNull CompanyItem newItem) {
            return false;
        }
    }
}
