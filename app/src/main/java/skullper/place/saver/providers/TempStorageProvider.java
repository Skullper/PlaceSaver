package skullper.place.saver.providers;

import android.support.annotation.NonNull;

/**
 * Created by pugman on 18.01.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public interface TempStorageProvider {

    String PREF_EMAIL = "pref.email";
    String PREF_IMAGE_URL = "pref.image.url";

	void clearAllPreferences();

	void saveEmail(@NonNull String email);
	@NonNull
	String getEmail();

	void saveAvatarPhotoUrl(@NonNull String url);
	@NonNull
	String getAvatarPhotoUrl();

}
