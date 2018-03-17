package uinbdg.skripsi.kajian.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uinbdg.skripsi.kajian.Adapter.AdapterKajian;
import uinbdg.skripsi.kajian.Adapter.AdapterKajianByLocation;
import uinbdg.skripsi.kajian.Model.DataItemKajian;
import uinbdg.skripsi.kajian.Model.KajianResponse;
import uinbdg.skripsi.kajian.R;
import uinbdg.skripsi.kajian.Service.ApiClient;
import uinbdg.skripsi.kajian.Service.AppConstans;
import uinbdg.skripsi.kajian.Service.KajianApi;
import uinbdg.skripsi.kajian.Util.CommonUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class KajianActivity extends AppCompatActivity {

    @BindView(R.id.action_search)
    ImageButton actionSearch;
    @BindView(R.id.search_bar)
    CardView searchBar;
    @BindView(R.id.recycler_view_kajian)
    RecyclerView recyclerViewKajian;

    List<DataItemKajian> dataItemKajian;

    AdapterKajian adapterKajian;
    AdapterKajianByLocation adapterKajianByLoacation;

    Retrofit retrofit;
    KajianApi kajianApi;
    @BindView(R.id.tv_no_item)
    TextView tvNoItem;
    @BindView(R.id.sp_pilihan)
    Spinner spPilihan;
    @BindView(R.id.et_search)
    EditText etSearch;

    ProgressDialog progressDialog;
    String tanggal;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kajian);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        retrofit = ApiClient.newInstance();
        kajianApi = retrofit.create(KajianApi.class);
        dataItemKajian = new ArrayList<>();
        recyclerViewKajian.setLayoutManager(new LinearLayoutManager(this));
        spPilihan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    etSearch.setText("Cari Semua Kajian");
                    etSearch.setClickable(false);
                } else if (i == 1) {
                    etSearch.setText(CommonUtil.getCurrentDate());
                    etSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openDatePicker();
                        }
                    });

                    tanggal = etSearch.getText().toString();
                } else if (i == 2) {
                    etSearch.setText("Cari Kajian Terdekat");
                    etSearch.setClickable(false);
                    Dexter.withActivity(KajianActivity.this)
                            .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    Toast.makeText(KajianActivity.this, "Akses Di izinkan", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    Toast.makeText(KajianActivity.this, "Akses tidak Di izinkan", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                }
                            }).onSameThread().check();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    void getKajian() {
        openDialog();
        kajianApi.tampilKajian().enqueue(new Callback<KajianResponse>() {
            @Override
            public void onResponse(Call<KajianResponse> call, Response<KajianResponse> response) {
                closeDialog();
                if (response.code() == AppConstans.HTTP_OK) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        dataItemKajian.add(response.body().getData().get(i));
                    }
                    adapterKajian = new AdapterKajian(KajianActivity.this, dataItemKajian);
                    adapterKajian.setOnItemClickListener(new AdapterKajian.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(KajianActivity.this,MapsDetailActivity.class);
                            intent.putExtra("long", dataItemKajian.get(position).getMosque().getLongitude());
                            intent.putExtra("lat", dataItemKajian.get(position).getMosque().getLatitude());
                            startActivity(intent);
                        }
                    });
                    recyclerViewKajian.setAdapter(adapterKajian);
                    recyclerViewKajian.setHasFixedSize(true);

                    if (adapterKajian.getItemCount() > 0) {
                        itemDitemukan();
                    } else {
                        itemTidakDitemukan();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<KajianResponse> call, Throwable t) {
                closeDialog();

            }
        });
    }

    void getKajianTerdekat() {
        openDialog();
        kajianApi.tampilKajian().enqueue(new Callback<KajianResponse>() {
            @Override
            public void onResponse(Call<KajianResponse> call, Response<KajianResponse> response) {
                closeDialog();
                if (response.code() == AppConstans.HTTP_OK) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        dataItemKajian.add(response.body().getData().get(i));
                    }
                    adapterKajianByLoacation = new AdapterKajianByLocation(KajianActivity.this, dataItemKajian);
                    adapterKajianByLoacation.setOnItemClickListener(new AdapterKajianByLocation.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(KajianActivity.this,MapsDetailActivity.class);
                            intent.putExtra("long", dataItemKajian.get(position).getMosque().getLongitude());
                            intent.putExtra("lat", dataItemKajian.get(position).getMosque().getLatitude());
                            startActivity(intent);
                        }
                    });
                    recyclerViewKajian.setAdapter(adapterKajianByLoacation);
                    recyclerViewKajian.setHasFixedSize(true);

                    if (adapterKajianByLoacation.getItemCount() > 0) {
                        itemDitemukan();
                    } else {
                        itemTidakDitemukan();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<KajianResponse> call, Throwable t) {
                closeDialog();

            }
        });
    }

    void getKajianByDate() {
        openDialog();
        Log.d("tanggal",tanggal);
        kajianApi.tampilKajianByCurrentDate(tanggal).enqueue(new Callback<KajianResponse>() {
            @Override
            public void onResponse(Call<KajianResponse> call, Response<KajianResponse> response) {
                closeDialog();
                if (response.code() == AppConstans.HTTP_OK) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        dataItemKajian.add(response.body().getData().get(i));
                    }
                    adapterKajian = new AdapterKajian(KajianActivity.this, dataItemKajian);
                    adapterKajian.setOnItemClickListener(new AdapterKajian.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(KajianActivity.this,MapsDetailActivity.class);
                            intent.putExtra("long", dataItemKajian.get(position).getMosque().getLongitude());
                            intent.putExtra("lat", dataItemKajian.get(position).getMosque().getLatitude());
                            startActivity(intent);
                        }
                    });
                    recyclerViewKajian.setAdapter(adapterKajian);
                    recyclerViewKajian.setHasFixedSize(true);

                    if (adapterKajian.getItemCount() > 0) {
                        itemDitemukan();
                    } else {
                        itemTidakDitemukan();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<KajianResponse> call, Throwable t) {
                closeDialog();

            }
        });
    }

    @OnClick(R.id.action_search)
    public void onViewClicked() {
        dataItemKajian.clear();
        int selected_pos = spPilihan.getSelectedItemPosition();
        if (selected_pos == 0) {
            getKajian();
        } else if (selected_pos == 1) {
            getKajianByDate();
        } else if (selected_pos == 2) {
            getKajianTerdekat();
        }
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


    void itemDitemukan() {
        recyclerViewKajian.setVisibility(View.VISIBLE);
        tvNoItem.setVisibility(View.GONE);
    }

    void itemTidakDitemukan() {
        recyclerViewKajian.setVisibility(View.GONE);
        tvNoItem.setVisibility(View.VISIBLE);
    }

    void openDatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        etSearch.setText(format.format(newDate.getTime()));
                        tanggal = etSearch.getText().toString();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
