package skullper.place.saver.mvp.presenters;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import skullper.place.saver.R;
import skullper.place.saver.base.presentation.BasePresenter;
import skullper.place.saver.data.User;
import skullper.place.saver.mvp.views.LoginView;

import static skullper.place.saver.utils.Constantaz.TABLE_USERS;
import static skullper.place.saver.utils.StringUtils.getString;

/**
 * Created by skullper on 28.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private final DatabaseReference database;

    public LoginPresenter(LoginView view) {
        super(view);
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            view.firebaseAuthWithGoogle(credential);
        } catch (ApiException e) {
            view.onLoginError(handleGoogleSignInStatusCode(e.getStatusCode()));
        }
    }

    public void saveUser(String userId, String email, Uri image) {
        if (userId != null && email != null && image != null) {
            database.child(TABLE_USERS).child(userId).setValue(new User(email, image.toString()));
        }
    }

    private String handleGoogleSignInStatusCode(int statusCode) {
        int message;
        switch (statusCode) {
            case GoogleSignInStatusCodes.NETWORK_ERROR:
                message = R.string.no_connection_error;
                break;
            case GoogleSignInStatusCodes.SIGN_IN_CANCELLED:
                message = R.string.auth_canceled;
                break;
            case GoogleSignInStatusCodes.SIGN_IN_CURRENTLY_IN_PROGRESS:
                message = R.string.auth_in_progress;
                break;
            case GoogleSignInStatusCodes.SIGN_IN_FAILED:
                message = R.string.auth_failed;
                break;
            default:
                message = R.string.auth_developer_error;//8 or 10 status codes. Check google and firebase console configuration
        }
        return getString(message);
    }

}
