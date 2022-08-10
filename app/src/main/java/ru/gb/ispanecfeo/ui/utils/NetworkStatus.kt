package ru.gb.ispanecfeo.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject


class NetworkStatus(
    private val context: Context,
    private val status: PublishSubject<Boolean>
) {
    init {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()


        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    status.onNext(true)
                }

                override fun onUnavailable() {
                    status.onNext(false)
                }

                override fun onLost(network: Network) {
                    status.onNext(false)
                }

            })
    }

    fun isOnline(): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        capabilities?.let {
            return it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }

        return false
    }

    fun getStatusConnected(): Observable<Boolean> = status

}