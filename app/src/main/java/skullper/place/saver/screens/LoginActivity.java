package skullper.place.saver.screens;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import butterknife.OnClick;
import skullper.place.saver.R;
import skullper.place.saver.base.activity.BaseActivity;
import skullper.place.saver.mvp.presenters.LoginPresenter;
import skullper.place.saver.mvp.views.LoginView;

/**
 * Created by skullper on 28.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    private GoogleSignInClient googleClient;
    private static final int RC_SIGN_IN = 154;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initViews() {
        initGoogleSignInClient();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            presenter.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data));
        }
    }

    @Override
    public void onLoggedIn() {
        startAffinity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.login_btn)
    void onClick() {
        Intent signInIntent = googleClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //
                .requestEmail() //
                .build();
        googleClient = GoogleSignIn.getClient(this, gso);
    }
}
