package skullper.place.saver.data;

import android.support.annotation.NonNull;

/**
 * Created by skullper on 30.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class User {

    private final String email;
    private final String imageUrl;

    public User(@NonNull String email, @NonNull String imageUrl) {
        this.email = email;
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getEmail(){
        return email;
    }

    @NonNull
    public String getImageUrl(){
        return imageUrl;
    }
}
