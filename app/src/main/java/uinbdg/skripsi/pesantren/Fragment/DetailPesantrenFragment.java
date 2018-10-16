package uinbdg.skripsi.pesantren.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import uinbdg.skripsi.pesantren.Model.DataItemPesantren;
import uinbdg.skripsi.pesantren.R;


public class DetailPesantrenFragment extends DialogFragment {

    private static final String TAG = "LibraryActivity";
    private static final String LIBRARY = "library";


    Unbinder unbinder;
    @BindView(R.id.bt_back)
    ImageButton btBack;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.et_telp)
    EditText etTelp;
    @BindView(R.id.et_jumlah_santri)
    EditText etJumlahSantri;
    @BindView(R.id.et_pemilik)
    EditText etPemilik;
    @BindView(R.id.et_pengajar)
    EditText etPengajar;

    DataItemPesantren pesantren;

    public static DetailPesantrenFragment newInstance(DataItemPesantren item) {

        Bundle args = new Bundle();
        args.putSerializable("pesantren", item);
        DetailPesantrenFragment fragment = new DetailPesantrenFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_pesantren, container, false);
        unbinder = ButterKnife.bind(this, view);

        pesantren = (DataItemPesantren) getArguments().getSerializable("pesantren");

        etNama.setText(pesantren.getNama());
        etAlamat.setText(pesantren.getAlamat());
        etTelp.setText(pesantren.getNoTelp());
        etPengajar.setText(pesantren.getJumlahPengajar());
        etPemilik.setText(pesantren.getPemilik() + " Pengajar");
        etJumlahSantri.setText(pesantren.getJumlahSantri() + " Santri");
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_back)
    public void onViewClicked() {
        dismiss();
    }
}
