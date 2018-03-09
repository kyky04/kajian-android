package uinbdg.skripsi.kajian.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
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
import uinbdg.skripsi.kajian.Adapter.AdapterMosque;
import uinbdg.skripsi.kajian.Model.DataItemKajian;
import uinbdg.skripsi.kajian.Model.DataItemMosque;
import uinbdg.skripsi.kajian.Model.KajianResponse;
import uinbdg.skripsi.kajian.Model.MosqueResponse;
import uinbdg.skripsi.kajian.R;
import uinbdg.skripsi.kajian.Service.ApiClient;
import uinbdg.skripsi.kajian.Service.AppConstans;
import uinbdg.skripsi.kajian.Service.KajianApi;
import uinbdg.skripsi.kajian.Util.CommonUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MosqueActivity extends AppCompatActivity {


    List<DataItemMosque> dataItemTeamList;

    AdapterMosque adapterKajian;
    AdapterKajianByLocation adapterKajianByLoacation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_kajian)
    RecyclerView recyclerViewKajian;

    Retrofit retrofit;
    KajianApi kajianApi;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque);
        ButterKnife.bind(this);
        initView();
        getMosque();
    }

    private void initView() {
        retrofit = ApiClient.newInstance();
        kajianApi = retrofit.create(KajianApi.class);
        dataItemTeamList = new ArrayList<>();
        recyclerViewKajian.setLayoutManager(new LinearLayoutManager(this));

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

    void getMosque() {
        openDialog();
        kajianApi.tampilMosque().enqueue(new Callback<MosqueResponse>() {
            @Override
            public void onResponse(Call<MosqueResponse> call, Response<MosqueResponse> response) {
                closeDialog();
                if (response.code() == AppConstans.HTTP_OK) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        dataItemTeamList.add(response.body().getData().get(i));
                    }
                    adapterKajian = new AdapterMosque(MosqueActivity.this, dataItemTeamList);
                    recyclerViewKajian.setAdapter(adapterKajian);
                    recyclerViewKajian.setHasFixedSize(true);

                } else {

                }
            }

            @Override
            public void onFailure(Call<MosqueResponse> call, Throwable t) {
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



}
