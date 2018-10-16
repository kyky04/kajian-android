package uinbdg.skripsi.pesantren.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uinbdg.skripsi.pesantren.R;
import uinbdg.skripsi.pesantren.Util.CommonUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.tv_akun)
    TextView tvAkun;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_reset_pass)
    TextView tvResetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick({R.id.img_logo, R.id.btn_login, R.id.tv_akun, R.id.tv_register, R.id.tv_reset_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_logo:
                break;
            case R.id.btn_login:
                CommonUtil.showProgress(LoginActivity.this, "Authenticating . . .");
                new Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                // onLoginFailed();
                                CommonUtil.hideDialog();
                            }
                        }, 3000);
                break;
            case R.id.tv_akun:
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, DaftarActivity.class));
                break;
            case R.id.tv_reset_pass:

                break;
        }
    }


}
