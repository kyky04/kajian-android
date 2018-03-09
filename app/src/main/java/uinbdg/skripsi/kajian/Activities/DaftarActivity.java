package uinbdg.skripsi.kajian.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import uinbdg.skripsi.kajian.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DaftarActivity extends AppCompatActivity {


    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.btn_daftar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_daftar:
                dialogDaftar();
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void dialogDaftar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        title = "Anda Berhasil Melakukan Pendaftaran";
        builder.setTitle(title);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }

    @OnClick(R.id.btn_back_to_login)
    public void onViewClicked() {
        onBackPressed();
    }
}
