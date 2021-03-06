package ng.adashi.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ng.adashi.domain_models.login.LoginResponse
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {

    class LoginState(context: Context) {
        private val data = context.createDataStore("login_response")
        private val login = context.createDataStore("login_key")

        companion object {
            val DATA = preferencesKey<LoginResponse>("data_value")
            val LOGIN_STATE = preferencesKey<Boolean>("state_value")
        }

        suspend fun saveLoginState(login_state: Boolean) {
            login.edit {
                it[LOGIN_STATE] = login_state
            }
        }
        suspend fun saveAccessToken(data_value: LoginResponse) {
            data.edit {
                it[DATA] = data_value
            }
        }

        val loginStateFlow: Flow<Boolean> = login.data.map {
            it[LOGIN_STATE] ?: false
        }

        val tokenStateFlow: Flow<Boolean> = data.data.map {
            it[LOGIN_STATE] ?: false
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun convertStringToDate(time: String): OffsetDateTime {
    val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH)
    return OffsetDateTime.parse(time, dtf)
}

