package br.org.maanaim.checkpray.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Silvinho Cedrim on 12/07/2017.
 */

public class MySaveSharedPreference {

    static final String PREF_USER_NAME= "username";

    static final String PREF_USER_ID = "id";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = getSharedPreferences(context);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public static void setUserName(Context ctx, String data)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, data);
        editor.commit();
    }

    public static void setUserId(Context ctx, long data)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putLong(PREF_USER_ID, data);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static long getUserId(Context ctx)
    {
        return getSharedPreferences(ctx).getLong(PREF_USER_ID, 0);
    }
}
