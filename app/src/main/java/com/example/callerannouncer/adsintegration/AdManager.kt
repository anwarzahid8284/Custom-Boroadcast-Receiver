package com.example.callerannouncer.adsintegration

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

private const val PRIORITY_ONLY_ADMOB = 0
private const val PRIORITY_ONLY_FACEBOOK = 1
private const val PRIORITY_ADMOB_TO_FACEBOOK = 2
private const val PRIORITY_FACEBOOK_TO_ADMOB = 3
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.loadInterstitialAd(
    interstitialAdPriority: Int,
    interstitialAdUnitID: String,
    onAdLoaded: () -> Unit,
    onAdFailedToLoad: () -> Unit
) {

}

fun Context.loadNativeAd(
    nativeAdPriority: Int,
    nativeAdUnitID: String,
    nativeAdLayout: () -> Unit,
    onAdLoaded: () -> Unit,
    onAdFailedToLoad: () -> Unit
) {
    if (isNetworkAvailable(this)) {
        when (nativeAdPriority) {
            PRIORITY_ONLY_ADMOB -> {
                //loadAdmobNativeAd(nativeAdUnitID)
            }
            PRIORITY_ONLY_FACEBOOK -> {
                loadFbNativeAd()
            }
            PRIORITY_ADMOB_TO_FACEBOOK -> {
               // loadAdmobNativeAd()

            }
            PRIORITY_FACEBOOK_TO_ADMOB -> {
                loadFbNativeAd()
            }

        }
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    var isConnected = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        isConnected = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            activeNetworkInfo?.run {
                isConnected = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return false
}