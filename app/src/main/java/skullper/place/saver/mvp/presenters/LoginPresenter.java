package skullper.place.saver.mvp.presenters;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import skullper.place.saver.R;
import skullper.place.saver.base.presentation.BasePresenter;
import skullper.place.saver.mvp.views.LoginView;
import skullper.place.saver.providers.impl.TempStorage;

import static skullper.place.saver.utils.StringUtils.getString;

/**
 * Created by skullper on 28.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(LoginView view) {
        super(view);
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            TempStorage.getInstance().saveEmail(account.getEmail() != null ? account.getEmail() : "");
            TempStorage.getInstance().saveAvatarPhotoUrl(account.getPhotoUrl() != null //
                    ? account.getPhotoUrl().toString() : "");
            view.onLoggedIn();
        } catch (ApiException e) {
            view.onLoginError(handleGoogleSignInStatusCode(e.getStatusCode()));
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
