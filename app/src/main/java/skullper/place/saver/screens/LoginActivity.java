package skullper.place.saver.screens;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.OnClick;
import skullper.place.saver.R;
import skullper.place.saver.base.activity.BaseActivity;
import skullper.place.saver.mvp.presenters.LoginPresenter;
import skullper.place.saver.mvp.views.LoginView;
import skullper.place.saver.providers.impl.Toaster;

/**
 * Created by skullper on 28.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    private static final int RC_SIGN_IN = 154;

    private GoogleSignInClient googleClient;
    private FirebaseAuth       firebaseAuth;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initViews() {
        getWindow().setBackgroundDrawableResource(R.drawable.bg_splash);
        getSupportActionBar().setTitle(R.string.app_name);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            onLoggedIn();
        } else {
            initGoogleSignInClient();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            presenter.handleSignInResult(task);
        }
    }

    @Override
    public void onLoginError(String message) {
        Toaster.getInstance().toast(message);
    }

    @Override
    public void firebaseAuthWithGoogle(AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    presenter.saveUser(user.getUid(), user.getEmail(), user.getPhotoUrl());
                }
                onLoggedIn();
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    onLoginError(exception.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.login_btn)
    void onClick() {
        Intent signInIntent = googleClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //
                .requestIdToken(getString(R.string.web_client_id)).requestEmail() //
                .build();
        googleClient = GoogleSignIn.getClient(this, gso);
    }

    private void onLoggedIn() {
        startAffinity(new Intent(this, MainActivity.class));
    }

}
