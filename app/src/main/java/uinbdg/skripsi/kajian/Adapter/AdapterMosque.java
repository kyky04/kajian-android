package uinbdg.skripsi.kajian.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uinbdg.skripsi.kajian.Model.DataItemKajian;
import uinbdg.skripsi.kajian.Model.DataItemMosque;
import uinbdg.skripsi.kajian.R;

/**
 * Created by Comp on 2/11/2018.
 */

public class AdapterMosque extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DataItemMosque> dataItemKajianList;


    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterMosque(Context ctx) {
        this.ctx = ctx;
    }

    public AdapterMosque(Context ctx, List<DataItemMosque> dataItemTeamList) {
        this.dataItemKajianList = dataItemTeamList;
        this.ctx = ctx;
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.et_mosque)
        EditText etMosque;
        @BindView(R.id.et_alamat)
        EditText etAlamat;

        public OriginalViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mosque, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        DataItemMosque kajian = dataItemKajianList.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.etMosque.setText(kajian.getNama());
            view.etAlamat.setText(kajian.getAlamat());
        }
    }

    @Override
    public int getItemCount() {
        return dataItemKajianList.size();
    }

    @Override
    public long getItemId(int position) {
        return dataItemKajianList.get(position).getId();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

}
