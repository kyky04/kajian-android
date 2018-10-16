package uinbdg.skripsi.pesantren.Adapter;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uinbdg.skripsi.pesantren.Model.DataItemPesantren;
import uinbdg.skripsi.pesantren.R;
import uinbdg.skripsi.pesantren.Util.GPSTracker;

/**
 * Created by Comp on 2/11/2018.
 */

public class AdapterPesantren extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DataItemPesantren> listItem;
    GPSTracker gpsTracker;



    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterPesantren(Context ctx) {
        this.ctx = ctx;
        listItem = new ArrayList<>();
        gpsTracker = new GPSTracker(ctx);
    }

    public AdapterPesantren(Context ctx, List<DataItemPesantren> dataItemTeamList) {
        this.listItem = dataItemTeamList;
        this.ctx = ctx;
        gpsTracker = new GPSTracker(ctx);
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.et_mosque)
        TextView etPesantren;
        @BindView(R.id.et_alamat)
        TextView etAlamat;
        @BindView(R.id.et_map)
        TextView etMap;
        @BindView(R.id.lyt_parent)
        LinearLayout lytParent;

        public OriginalViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesantren, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        DataItemPesantren pesantren = listItem.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.etPesantren.setText(pesantren.getNama());
            view.etAlamat.setText(pesantren.getAlamat());
            view.etMap.setText(new DecimalFormat("##.##").format(pesantren.getJarak())+" KM");
//            view.etMap.setText(String.valueOf(getDistance(gpsTracker.getLatitude(), gpsTracker.getLongitude(), Double.valueOf(listItem.get(position).getLatitude()), Double.parseDouble(listItem.get(position).getLongitude())) / 1000) + " Km");

            view.lytParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    @Override
    public long getItemId(int position) {
        return listItem.get(position).getId();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi) {
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }

    public void add(DataItemPesantren item) {
        listItem.add(item);
        notifyItemInserted(listItem.size() + 1);
    }

    public void addAll(List<DataItemPesantren> listItem) {
        for (DataItemPesantren item : listItem) {
            add(item);
        }
    }

    public void removeAll() {
        listItem.clear();
        notifyDataSetChanged();
    }

    public void swap(List<DataItemPesantren> datas) {
        if (datas == null || datas.size() == 0) listItem.clear();
        if (listItem != null && listItem.size() > 0)
            listItem.clear();
        listItem.addAll(datas);
        notifyDataSetChanged();

    }

    public DataItemPesantren getItem(int pos) {
        return listItem.get(pos);
    }

    public void setFilter(List<DataItemPesantren> list) {
        listItem = new ArrayList<>();
        listItem.addAll(list);
        notifyDataSetChanged();
    }

}
