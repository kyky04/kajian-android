package uinbdg.skripsi.pesantren.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uinbdg.skripsi.pesantren.Adapter.AdapterEmpty;
import uinbdg.skripsi.pesantren.Adapter.AdapterPesantren;
import uinbdg.skripsi.pesantren.Fragment.DetailPesantrenFragment;
import uinbdg.skripsi.pesantren.Model.DataItemPesantren;
import uinbdg.skripsi.pesantren.Model.PesantrenResponse;
import uinbdg.skripsi.pesantren.R;
import uinbdg.skripsi.pesantren.Util.GPSTracker;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static uinbdg.skripsi.pesantren.Activities.PesantrenActivity.getDistance;
import static uinbdg.skripsi.pesantren.Util.Contans.PESANTREN;

public class SearchPesantrenActivity extends AppCompatActivity {

    @BindView(R.id.action_search)
    ImageButton actionSearch;
    @BindView(R.id.search_bar)
    CardView searchBar;
    @BindView(R.id.recycler_view)
    RecyclerView recycler;


    AdapterPesantren adapter;

    @BindView(R.id.sp_pilihan)
    Spinner spPilihan;
    @BindView(R.id.et_search)
    EditText etSearch;

    ProgressDialog progressDialog;
    String tanggal;

    DatePickerDialog datePickerDialog;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    GPSTracker gpsTracker;
    double latitude, longitude;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<DataItemPesantren> pesantrenList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesantren);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
        adapter = new AdapterPesantren(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        spPilihan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    etSearch.setHint("Cari Nama Pesantren");
                    etSearch.setClickable(false);
                } else if (i == 1) {
                    etSearch.setHint("Cari Nama Pesantren");
                    etSearch.setClickable(false);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter.setOnItemClickListener(new AdapterPesantren.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openDetail(adapter.getItem(position));
            }
        });

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_id_map:
                Intent intent = new Intent(SearchPesantrenActivity.this, MapsDetailActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("pesantren", (Serializable) pesantrenList);
                intent.putExtras(data);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @OnClick(R.id.action_search)
    public void onViewClicked() {
        getData();
//        int selected_pos = spPilihan.getSelectedItemPosition();
//        if (selected_pos == 0) {
//        } else if (selected_pos == 1) {
//            getTerdekat();
//        }
    }

    private void getTerdekat() {

    }

    private void getData() {
        openDialog();
        pesantrenList.clear();
        ANRequest.GetRequestBuilder getRequestBuilder = new ANRequest.GetRequestBuilder(PESANTREN);
        if (!"".equals(etSearch.getText().toString())) {
            getRequestBuilder.addQueryParameter("nama", "*" + etSearch.getText().toString() + "*");
        }
        getRequestBuilder
                .build()
                .getAsObject(PesantrenResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof PesantrenResponse) {
                            if (((PesantrenResponse) response).getData().size() > 0) {
                                pesantrenList = ((PesantrenResponse) response).getData();

                                for (int i = 0; i < pesantrenList.size(); i++) {
                                    pesantrenList.get(i).setJarak(getDistance(gpsTracker.getLatitude(), gpsTracker.getLongitude(), Double.valueOf(pesantrenList.get(i).getLatitude()), Double.parseDouble(pesantrenList.get(i).getLongitude())) / 1000);
                                }
                                Collections.sort(pesantrenList, new Comparator<DataItemPesantren>() {
                                    @Override
                                    public int compare(DataItemPesantren dataItemPesantren, DataItemPesantren t1) {
                                        return (int) (dataItemPesantren.getJarak() - t1.getJarak());
                                    }
                                });

                                adapter.swap(pesantrenList);
                                Toast.makeText(SearchPesantrenActivity.this, "Data ditemukan", Toast.LENGTH_SHORT).show();
                            } else {
                                recycler.setAdapter(new AdapterEmpty(SearchPesantrenActivity.this));
                                Toast.makeText(SearchPesantrenActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR RESPONSE", anError.getErrorDetail());
                        closeDialog();

                    }
                });
    }

    void openDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading . . . ");
        progressDialog.show();
    }

    void closeDialog() {
        progressDialog.dismiss();
    }

    void openDetail(DataItemPesantren pesantren) {
        DetailPesantrenFragment detailFragment = DetailPesantrenFragment.newInstance(pesantren);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, detailFragment).addToBackStack(null).commit();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

}
