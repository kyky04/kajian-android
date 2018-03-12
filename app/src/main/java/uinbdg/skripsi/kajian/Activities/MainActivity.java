package uinbdg.skripsi.kajian.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uinbdg.skripsi.kajian.R;
import uinbdg.skripsi.kajian.Util.CommonUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_kajian)
    RelativeLayout btnKajian;
    @BindView(R.id.btn_mosque)
    RelativeLayout btnMosque;
    @BindView(R.id.btn_search)
    RelativeLayout btnSearch;
    @BindView(R.id.btn_tentang)
    RelativeLayout btnTentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        CommonUtil.exit(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                CommonUtil.logout(this);
                break;
            case R.id.menu_exit:
                CommonUtil.exit(this);
                break;
            case R.id.menu_edit_profil:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_kajian, R.id.btn_mosque, R.id.btn_search, R.id.btn_tentang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_kajian:
                startActivity(new Intent(this, KajianByDateActivity.class));
                break;
            case R.id.btn_mosque:
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.btn_search:
                startActivity(new Intent(this, KajianActivity.class));
                break;
            case R.id.btn_tentang:
                break;
        }
    }
}
