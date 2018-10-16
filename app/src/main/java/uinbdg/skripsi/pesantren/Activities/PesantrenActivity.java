package uinbdg.skripsi.pesantren.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uinbdg.skripsi.pesantren.Adapter.AdapterPesantren;
import uinbdg.skripsi.pesantren.Model.DataItemPesantren;
import uinbdg.skripsi.pesantren.Model.PesantrenResponse;
import uinbdg.skripsi.pesantren.R;
import uinbdg.skripsi.pesantren.Util.GPSTracker;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static uinbdg.skripsi.pesantren.Util.Contans.PESANTREN;

public class PesantrenActivity extends AppCompatActivity {

    AdapterPesantren adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    List<DataItemPesantren> pesantrenList = new ArrayList<>();

    ProgressDialog progressDialog;

    GPSTracker gpsTracker;
    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque);
        ButterKnife.bind(this);
        initView();

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
    }

    private void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterPesantren(this);
        recyclerView.setAdapter(adapter);

        getData();
        adapter.setOnItemClickListener(new AdapterPesantren.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(PesantrenActivity.this, MapsDetailActivity.class);
                intent.putExtra("long", adapter.getItem(position).getLongitude());
                intent.putExtra("lat", adapter.getItem(position).getLatitude());
                intent.putExtra("nama", adapter.getItem(position).getNama());
                intent.putExtra("jarak", adapter.getItem(position).getJarak());
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void getData() {
        openDialog();
        AndroidNetworking.get(PESANTREN)
                .build()
                .getAsObject(PesantrenResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof PesantrenResponse) {
                            if (((PesantrenResponse) response).getData().size() > 0) {
                                pesantrenList.clear();
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
                                Toast.makeText(PesantrenActivity.this, "Data ditemukan", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PesantrenActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
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

    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi) {
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }

}
