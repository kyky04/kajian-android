package uinbdg.skripsi.kajian.Adapter;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uinbdg.skripsi.kajian.Model.DataItemKajian;
import uinbdg.skripsi.kajian.R;
import uinbdg.skripsi.kajian.Util.GPSTracker;

/**
 * Created by Comp on 2/11/2018.
 */

public class AdapterKajianByLocation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DataItemKajian> dataItemKajianList;
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

    public AdapterKajianByLocation(Context ctx) {
        this.ctx = ctx;
    }

    public AdapterKajianByLocation(Context ctx, List<DataItemKajian> dataItemTeamList) {
        this.dataItemKajianList = dataItemTeamList;
        this.ctx = ctx;
        gpsTracker = new GPSTracker(ctx);
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_pemateri)
        TextView tvPemateri;
        @BindView(R.id.tv_cabang)
        TextView tvTema;
        @BindView(R.id.et_mosque)
        TextView etMosque;
        @BindView(R.id.et_alamat)
        TextView etAlamat;
        @BindView(R.id.et_waktu)
        TextView etWaktu;
        @BindView(R.id.et_date)
        TextView etDate;
        @BindView(R.id.et_location)
        TextView etLocation;
        @BindView(R.id.view_item)
        LinearLayout viewItem;

        public OriginalViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kajian_with_loc, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        DataItemKajian kajian = dataItemKajianList.get(position);
        Location loc1 = new Location("");
        loc1.setLatitude(gpsTracker.getLatitude());
        loc1.setLongitude(gpsTracker.getLongitude());

        Location loc2 = new Location("");
        loc2.setLatitude(kajian.getMosque().getLatitude());
        loc2.setLongitude(kajian.getMosque().getLongitude());

        float distanceInMeters = loc1.distanceTo(loc2) / 1000;
        DecimalFormat df2 = new DecimalFormat(".##");

        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.tvPemateri.setText(kajian.getPemateri());
            view.tvTema.setText(kajian.getTema());
            view.etMosque.setText(kajian.getMosque().getNama());
            view.etWaktu.setText(kajian.getWaktuKajian());
            view.etDate.setText(kajian.getWaktu().substring(0, 10));
            view.etAlamat.setText(kajian.getMosque().getAlamat());
            view.etLocation.setText(String.valueOf(df2.format(distanceInMeters)) + " KM");

            view.viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
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
