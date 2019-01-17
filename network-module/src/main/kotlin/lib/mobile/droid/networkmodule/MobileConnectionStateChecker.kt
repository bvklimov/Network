package lib.mobile.droid.networkmodule

import android.content.Context
import android.net.ConnectivityManager
import android.provider.Settings

class MobileConnectionStateChecker(private val context: Context): IConnectionStateChecker {
    private val manager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    private val internetConnected: Boolean
        get() {
            if (manager == null) return false
            val info = manager.activeNetworkInfo
            return info != null
                    && info.isAvailable
                    && info.isConnected
        }
    private val airplaneModeOn: Boolean
        get() {
            return Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0
        }

    override fun checkForConnectionState() {
        when {
            !internetConnected -> throw InternetConnectionException()
            airplaneModeOn -> throw  AirplaneException()
        }
    }
}