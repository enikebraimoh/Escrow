package ng.adashi.network

import android.content.Context
import android.content.SharedPreferences
import com.adashi.escrow.R
import ng.adashi.utils.App

class SessionManager(context : Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val LOGINSTATE = "login_state"
    }

    /**
     * Function to save auth token
     */

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putBoolean(LOGINSTATE, true)
        editor.apply()
        App.token = token
    }

    fun clearAuthToken() {
        val editor = prefs.edit().clear()
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */

    fun fetchAuthToken(): String? {
        App.token = prefs.getString(USER_TOKEN, null)
        return prefs.getString(USER_TOKEN, null)
    }

}