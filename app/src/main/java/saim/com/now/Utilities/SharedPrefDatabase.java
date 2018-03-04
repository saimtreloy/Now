package saim.com.now.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Set;

import saim.com.now.Model.ModelMainUser;

/**
 * Created by saim on 3/1/17.
 */

public class SharedPrefDatabase {

    public static final String USER_LOCATION = "USER_LOCATION";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_MOBILE = "USER_MOBILE";
    public static final String USER_PASS = "USER_PASS";
    public static final String USER_IMAGE = "USER_IMAGE";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public SharedPrefDatabase(Context ctx) {
        this.context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();
    }

    public void StoreUserLocation(String data){
        editor.putString(USER_LOCATION, data);
        editor.commit();
    }
    public String RetriveUserLocation(){
        String text = sharedPreferences.getString(USER_LOCATION, null);
        return text;
    }

    public void StoreUserID(String data){
        editor.putString(USER_ID, data);
        editor.commit();
    }
    public String RetriveUserID(){
        String text = sharedPreferences.getString(USER_ID, null);
        return text;
    }


    public void StoreUserName(String data){
        editor.putString(USER_NAME, data);
        editor.commit();
    }
    public String RetriveUserName(){
        String text = sharedPreferences.getString(USER_NAME, null);
        return text;
    }


    public void StoreUserEmail(String data){
        editor.putString(USER_EMAIL, data);
        editor.commit();
    }
    public String RetriveUserEmail(){
        String text = sharedPreferences.getString(USER_EMAIL, null);
        return text;
    }

    public void StoreUserMobile(String data){
        editor.putString(USER_MOBILE, data);
        editor.commit();
    }
    public String RetriveUserMobile(){
        String text = sharedPreferences.getString(USER_MOBILE, null);
        return text;
    }

    public void StoreUserPass(String data){
        editor.putString(USER_PASS, data);
        editor.commit();
    }
    public String RetriveUserPass(){
        String text = sharedPreferences.getString(USER_PASS, null);
        return text;
    }

    public void StoreUserImage(String data){
        editor.putString(USER_IMAGE, data);
        editor.commit();
    }
    public String RetriveUserImage(){
        String text = sharedPreferences.getString(USER_IMAGE, null);
        return text;
    }
}
