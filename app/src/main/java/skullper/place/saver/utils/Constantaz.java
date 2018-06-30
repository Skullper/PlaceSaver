package skullper.place.saver.utils;

/*
 * Created by skullper on 30.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

/**
 * Stores all in app constants
 */
public final class Constantaz{

    private Constantaz(){
        throw new IllegalStateException("U cannot create instance of this class");
    }

    public static final String TABLE_USERS = "users";
    public static final String TABLE_PLACES = "places";
    public static final String TABLE_USER_PLACES = "user-places";
    public static final String PLACES_LOCATION = "/" + TABLE_PLACES + "/";
    public static final String USER_PLACES_LOCATION = "/" + TABLE_USER_PLACES + "/";
}
