package io.github.hyuwah.muslimcompanionapp.data;

// Singleton SharedPreferences Manager

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {

  private static final String SETTINGS_NAME = "default_settigs";
  private static SharedPrefsManager sSharedPrefs;
  private SharedPreferences mPref;
  private SharedPreferences.Editor mEditor;
  private boolean mBulkUpdate = false;

  public enum Key {
    EDITION_KEY,
    CURRENT_AYAH_ID_INT
  }

  private SharedPrefsManager(Context context) {
    mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
  }

  // This must be invoked on oncreate atleast once
  public static SharedPrefsManager getInstance(Context context) {
    if(sSharedPrefs == null){
      sSharedPrefs = new SharedPrefsManager(context.getApplicationContext());
    }
    return sSharedPrefs;
  }

  // So this can be used on presenter
  public static  SharedPrefsManager getInstance() {
    if(sSharedPrefs!=null){
      return sSharedPrefs;
    }
    throw new IllegalArgumentException("Should use getInstance(Context) at least once");
  }

  public void put(Key key, String val) {
    doEdit();
    mEditor.putString(key.name(), val);
    doCommit();
  }

  public void put(Key key, int val) {
    doEdit();
    mEditor.putInt(key.name(), val);
    doCommit();
  }

  public void put(Key key, boolean val) {
    doEdit();
    mEditor.putBoolean(key.name(), val);
    doCommit();
  }

  public void put(Key key, float val) {
    doEdit();
    mEditor.putFloat(key.name(), val);
    doCommit();
  }

  public void put(Key key, double val) {
    doEdit();
    mEditor.putString(key.name(), String.valueOf(val));
    doCommit();
  }

  public void put(Key key, long val) {
    doEdit();
    mEditor.putLong(key.name(), val);
    doCommit();
  }

  public String getString(Key key, String defaultValue) {
    return mPref.getString(key.name(), defaultValue);
  }

  public String getString(Key key) {
    return mPref.getString(key.name(), null);
  }

  public int getInt(Key key) {
    return mPref.getInt(key.name(), 0);
  }

  public int getInt(Key key, int defaultValue) {
    return mPref.getInt(key.name(), defaultValue);
  }

  public long getLong(Key key) {
    return mPref.getLong(key.name(), 0);
  }

  public long getLong(Key key, long defaultValue) {
    return mPref.getLong(key.name(), defaultValue);
  }

  public float getFloat(Key key) {
    return mPref.getFloat(key.name(), 0);
  }

  public float getFloat(Key key, float defaultValue) {
    return mPref.getFloat(key.name(), defaultValue);
  }

  public double getDouble(Key key) {
    return getDouble(key, 0);
  }

  public double getDouble(Key key, double defaultValue) {
    try {
      return Double.valueOf(mPref.getString(key.name(), String.valueOf(defaultValue)));
    } catch (NumberFormatException nfe) {
      return defaultValue;
    }
  }

  public boolean getBoolean(Key key, boolean defaultValue) {
    return mPref.getBoolean(key.name(), defaultValue);
  }

  public boolean getBoolean(Key key) {
    return mPref.getBoolean(key.name(), false);
  }

  public void remove(Key... keys) {
    doEdit();
    for (Key key : keys) {
      mEditor.remove(key.name());
    }
    doCommit();
  }

  public void clear() {
    doEdit();
    mEditor.clear();
    doCommit();
  }

  public void edit() {
    mBulkUpdate = true;
    mEditor = mPref.edit();
  }

  public void commit() {
    mBulkUpdate = false;
    mEditor.commit();
    mEditor = null;
  }

  private void doEdit() {
    if (!mBulkUpdate && mEditor == null) {
      mEditor = mPref.edit();
    }
  }

  private void doCommit() {
    if (!mBulkUpdate && mEditor != null) {
      mEditor.commit();
      mEditor = null;
    }
  }

}
