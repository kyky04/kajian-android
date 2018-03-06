package uinbdg.skirpsi.kajian.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uinbdg.skirpsi.kajian.Adapter.AdapterKajian;
import uinbdg.skirpsi.kajian.Model.DataItemKajian;
import uinbdg.skirpsi.kajian.Model.KajianResponse;
import uinbdg.skirpsi.kajian.R;
import uinbdg.skirpsi.kajian.Service.ApiClient;
import uinbdg.skirpsi.kajian.Service.AppConstans;
import uinbdg.skirpsi.kajian.Service.KajianApi;
import uinbdg.skirpsi.kajian.Util.CommonUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class KajianByDateActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_bar)
    CardView searchBar;
    @BindView(R.id.recycler_view_kajian)
    RecyclerView recyclerViewPeserta;

    List<DataItemKajian> dataItemTeamList;

    AdapterKajian adapterKajian;

    Retrofit retrofit;
    KajianApi kajianApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kajian_hari_ini);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        getKajian();
    }

    private void initView() {
        retrofit = ApiClient.newInstance();
        kajianApi = retrofit.create(KajianApi.class);
        dataItemTeamList = new ArrayList<>();
        toolbar.setTitle(CommonUtil.getCurrentDate());
        recyclerViewPeserta.setLayoutManager(new LinearLayoutManager(this));

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

    void getKajian(){
        kajianApi.tampilKajianByCurrentDate(CommonUtil.getCurrentDate()).enqueue(new Callback<KajianResponse>() {
            @Override
            public void onResponse(Call<KajianResponse> call, Response<KajianResponse> response) {
                if(response.code() == AppConstans.HTTP_OK){
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        dataItemTeamList.add(response.body().getData().get(i));
                    }
                    adapterKajian = new AdapterKajian(KajianByDateActivity.this,dataItemTeamList);
                    recyclerViewPeserta.setAdapter(adapterKajian);
                    recyclerViewPeserta.setHasFixedSize(true);

                }
            }

            @Override
            public void onFailure(Call<KajianResponse> call, Throwable t) {

            }
        });
    }
}
