package com.qlmat.android.smartsupplier.arch

import android.content.Context

class SharedPreferencesRepo(context: Context) : SharedPreferencesContract {
    override val MY_PREFS_NAME = "my_preferences_file"
    override val KEY_USER_ID = "user_id"
    override val KEY_USER_EMAIL = "user_name"
    override val KEY_USER_PHONE_NUM = "user_phone_number"
    override val KEY_USER_PASS = "user_password"

    private val prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)

    fun getUserId(): String {
        return prefs.getString(KEY_USER_ID, NO_VALUE) ?: NO_VALUE
    }

    fun setUserId(id: String) {
        prefs.edit().putString(KEY_USER_ID, id).apply()
    }

    fun getUserEmail(): String {
        return prefs.getString(KEY_USER_EMAIL, NO_VALUE) ?: NO_VALUE
    }

    fun setUserEmail(email: String) {
        prefs.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun getUserPhoneNumber(): String {
        return prefs.getString(KEY_USER_PHONE_NUM, NO_VALUE) ?: NO_VALUE
    }

    fun setUserPhoneNumber(phoneNumber: String) {
        prefs.edit().putString(KEY_USER_PHONE_NUM, phoneNumber).apply()
    }

    fun getUserPassword(): String? {
        return prefs.getString(KEY_USER_PASS, NO_VALUE) ?: NO_VALUE
    }


    fun setUserPassword(password: String) {
        prefs.edit().putString(KEY_USER_PASS, password).apply()
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }

    companion object {
        const val NO_VALUE = ""
    }

}