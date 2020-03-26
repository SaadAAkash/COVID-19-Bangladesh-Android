package xyz.palaocorona.util

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.UnknownHostException
import java.nio.channels.IllegalBlockingModeException

class ConnectivityManager(val host: String) {

    companion object {
        //-------------------------------------------   Connectivity  --------------------------------------------------------------------------
        /**
         * Gets the minimum speed for fast connection
         * @return The minimum speed for fast connection
         */
        /**
         * The minimum speed for the connection to be considered fast
         *
         */
        private const val minimumSpeedForFastConnection = 3072

        /**
         * Get the active network's info.
         *
         * @param context The Context.
         * @return The active NetworkInfo.
         */
        private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
            var networkInfo: NetworkInfo? = null
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm != null) {
                networkInfo = cm.activeNetworkInfo
            }
            return networkInfo
        }

        /**
         * Get active network.
         *
         * @param context The Context.
         * @return The active NetworkInfo.
         */
        private fun getActiveNetwork(context: Context): Network? {
            var networkInfo: Network? = null
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    networkInfo = cm.activeNetwork
                } else {
                    Log.e("UnusableMethod", "Cannot use this method for the current API Level")
                }
            }
            return networkInfo
        }

        /**
         * Get the network info.
         *
         * @param context The Context.
         * @return The active NetworkInfo.
         */
        private fun getActiveNetworkInfo(context: Context, network: Network?): NetworkInfo? {
            var networkInfo: NetworkInfo? = null
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm != null && network != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    networkInfo = cm.getNetworkInfo(network)
                } else {
                    Log.e("UnusableMethod", "Cannot use this method for the current API Level")
                }
            }
            return networkInfo
        }

        /**
         * Gets the info of all networks
         * @param context The context
         * @return An array of @link {NetworkInfo}
         */
        private fun getAllNetworkInfo(context: Context?): Array<NetworkInfo?> {
            val cm = context
                ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var nwInfo = arrayOfNulls<NetworkInfo>(0)
            if (cm != null) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    nwInfo = cm.allNetworkInfo
                } else {
                    Log.e("UnusableMethod", "Cannot use this method for the current API Level")
                }
            }
            return nwInfo
        }

        /**
         * Gives the connectivity manager
         * @param context The context
         * @return the @code{[ConnectivityManager]}
         */
        private fun getConnectivityManager(context: Context?): ConnectivityManager {
            return context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        /**
         * Tells whether the network is facilitating fast network switching
         * @param context The context.
         * @param network The network.
         * @return @code{true} if the network is facilitating fast network switching
         */
        private fun isNetworkFacilitatingFastNetworkSwitching(context: Context,
                                                              network: Network): Boolean {
            var isNetworkFacilitatingFastNetworkSwitching = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) {
                    isNetworkFacilitatingFastNetworkSwitching = true
                }
            } else {
                Log.e("UnusableMethod", null)
            }
            return isNetworkFacilitatingFastNetworkSwitching
        }

        /**
         * Tells whether the network can be used by apps
         * @param context The context.
         * @param network The network.
         * @return @code{true} if the network can by used by apps
         */
        private fun isNetworkUsableByApps(context: Context, network: Network): Boolean {
            var isNetworkUsableByApps = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) {
                    isNetworkUsableByApps = true
                }
            } else {
                Log.e("UnusableMethod", null)
            }
            return isNetworkUsableByApps
        }

        private fun isNetworkSuspended(context: Context, network: Network): Boolean {
            var isNetworkSuspended = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)) {
                    isNetworkSuspended = true
                }
            } else {
                Log.e("UnusableMethod", null)
            }
            return isNetworkSuspended
        }

        /**
         * Check if there is any connectivity at all to a specific network.
         *
         * @param context The Context.
         * @return @code{true} if we are connected to a network, false otherwise.
         */
        fun isActiveNetworkConnected(context: Context): Boolean {
            var isConnected = false
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val network = getActiveNetwork(context)
                if (network != null) {
                    val networkCapabilities = connectivityManager
                        .getNetworkCapabilities(network)
                    if (networkCapabilities != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            if (network != null) {
                                if (networkCapabilities != null) {
                                    if (networkCapabilities.hasCapability(
                                            NetworkCapabilities.NET_CAPABILITY_INTERNET) /* API >= 21*/
                                        && networkCapabilities.hasCapability(
                                            NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/
                                        && networkCapabilities.hasCapability(
                                            NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED) /*API >= 28*/
                                        && networkCapabilities.hasCapability(
                                            NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) /*API >= 28*/ {
                                        isConnected = true
                                    }
                                }
                            }
                        } else {
                            if (network != null) {
                                if (networkCapabilities != null) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (networkCapabilities.hasCapability(
                                                NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/
                                            && networkCapabilities.hasCapability(
                                                NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/) {
                                            isConnected = true
                                        }
                                    } else {
                                        if (networkCapabilities.hasCapability(
                                                NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/) {
                                            isConnected = true
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Log.e("NullNetworkCapabilities", null)
                    }
                } else {
                    Log.e("NullNetwork", null)
                }
            } else {
                val info = getActiveNetworkInfo(context)
                /*
			    Works on emulator and devices. Note the use of @link{NetworkInfo
			    .isAvailable} - without this, @link{NetworkInfo.isConnected} can return
			    @code{true} when Wi-Fi is disabled (http://stackoverflow.com/a/2937915)
			 */isConnected = info != null && info.isAvailable && info.isConnected
            }
            return isConnected
        }

        /**
         * Check if there is any connectivity at all to a specific network.
         *
         * @param context The Context.
         * @param network The network
         * @return @code{true} if we are connected to a network, false otherwise.
         */
        fun isConnected(context: Context, network: Network?): Boolean {
            var isConnected = false
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networkCapabilities = connectivityManager
                    .getNetworkCapabilities(network)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (network != null) {
                        if (networkCapabilities != null) {
                            if (networkCapabilities
                                    .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /* API >= 21*/
                                && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/
                                && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED) /*API >= 28*/
                                && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) /*API >= 28*/ {
                                isConnected = true
                            }
                        }
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (networkCapabilities
                                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true /*API >= 21*/
                            && networkCapabilities.hasCapability(
                                NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/) {
                            isConnected = true
                        }
                    } else {
                        if (networkCapabilities
                                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true /*API >= 21*/) {
                            isConnected = true
                        }
                    }
                }
            } else {
                val info = getActiveNetworkInfo(context)
                /*
			    Works on emulator and devices. Note the use of @link{NetworkInfo
			    .isAvailable} - without this, @link{NetworkInfo.isConnected} can return
			    @code{true} when Wi-Fi is disabled (http://stackoverflow.com/a/2937915)
			 */isConnected = info != null && info.isAvailable && info.isConnected
            }
            return isConnected
        }

        /**
         * Check if there is any connectivity at all.
         *
         * @param context the Context.
         * @return @code{true} if we are connected to a network, false otherwise.
         */
        @JvmStatic
        fun isConnected(context: Context?): Boolean {
            var isConnected = false
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networks = connectivityManager.allNetworks
                for (network in networks) {
                    if (network != null) {
                        val networkCapabilities = connectivityManager
                            .getNetworkCapabilities(network)
                        if (networkCapabilities != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                if ((networkCapabilities
                                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /* API >= 21*/
                                            && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_VALIDATED) /* API >= 23*/
                                            && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)) /* API >= 28*/
                                    && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) /* API >= 28*/ {
                                    isConnected = true
                                    break
                                }
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (networkCapabilities
                                            .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/
                                        && networkCapabilities.hasCapability(
                                            NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/) {
                                        isConnected = true
                                        break
                                    }
                                } else {
                                    if (networkCapabilities
                                            .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/) {
                                        isConnected = true
                                        break
                                    }
                                }
                            }
                        } else {
                            Log.e("NullNetworkCapabilities", null)
                        }
                    } else {
                        Log.e("NullNetwork", null)
                    }
                }
            } else {
                val infos = getAllNetworkInfo(context)
                for (info in infos) { /*
				    Works on emulator and devices. Note the use of @link{NetworkInfo
				    .isAvailable} - without this, @link{NetworkInfo.isConnected} can
				    return @code{true} when Wi-Fi is disabled (http://stackoverflow
				    .com/a/2937915)
				 */
                    isConnected = info != null && info.isAvailable && info.isConnected
                    if (isConnected) {
                        break
                    }
                }
            }
            return isConnected
        }

        /**
         * Check if there is any connectivity to a Wifi network.
         *
         * @param context the Context.
         * @return @code{true} if we are connected to a Wifi network, false otherwise.
         */
        fun isConnectedWifi(context: Context?): Boolean {
            var isConnectedWifi = false
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networks = connectivityManager.allNetworks
                for (network in networks) {
                    if (network != null) {
                        val networkCapabilities = connectivityManager
                            .getNetworkCapabilities(network)
                        if (networkCapabilities != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                if (network != null) {
                                    if (networkCapabilities != null) {
                                        if ((networkCapabilities.hasCapability(
                                                NetworkCapabilities.NET_CAPABILITY_INTERNET) /* API >= 21*/
                                                    && networkCapabilities
                                                .hasCapability(
                                                    NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API>=23*/
                                                    && networkCapabilities.hasCapability(
                                                NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)) /*API>=28*/
                                            && networkCapabilities.hasCapability(
                                                NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) /*API>=28*/ {
                                            if (networkCapabilities.hasTransport(
                                                    NetworkCapabilities.TRANSPORT_WIFI)) {
                                                isConnectedWifi = true
                                                break
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (network != null) {
                                    if (networkCapabilities != null) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (networkCapabilities.hasCapability(
                                                    NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/
                                                && networkCapabilities.hasCapability(
                                                    NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/) {
                                                if (networkCapabilities.hasTransport(
                                                        NetworkCapabilities.TRANSPORT_WIFI)) {
                                                    isConnectedWifi = true
                                                    break
                                                }
                                            }
                                        } else {
                                            if (networkCapabilities.hasCapability(
                                                    NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/) {
                                                if (networkCapabilities.hasTransport(
                                                        NetworkCapabilities.TRANSPORT_WIFI)) {
                                                    isConnectedWifi = true
                                                    break
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.e("NullNetworkCapabilities", null)
                        }
                    } else {
                        Log.e("NullNetwork", null)
                    }
                }
            } else {
                val infos = getAllNetworkInfo(context)
                for (info in infos) { /*
				    Works on emulator and devices. Note the use of @link{NetworkInfo
				    .isAvailable} - without this, @link{NetworkInfo.isConnected} can
				    return @code{true} when Wi-Fi is disabled (http://stackoverflow
				    .com/a/2937915)
				 */
                    isConnectedWifi = (info != null && info.isAvailable && info.isConnected
                            && info.type == ConnectivityManager.TYPE_WIFI)
                    if (isConnectedWifi) {
                        break
                    }
                }
            }
            return isConnectedWifi
        }

        /**
         * Check if there is any connectivity to a Wifi network on a specific network.
         *
         * @param context The context.
         * @param network The network
         * @return @code{true} if we are connected to a Wifi network, false otherwise.
         */
        fun isConnectedWifi(context: Context, network: Network?): Boolean {
            var isConnectedWifi = false
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networkCapabilities = connectivityManager
                    .getNetworkCapabilities(network)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (network != null) {
                        if (networkCapabilities != null) {
                            if ((networkCapabilities
                                    .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /* API >= 21*/
                                        && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API>=23*/
                                        && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)) /*API>=28*/
                                && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) /*API>=28*/ {
                                if (networkCapabilities
                                        .hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                                    isConnectedWifi = true
                                }
                            }
                        }
                    }
                } else {
                    if (network != null) {
                        if (networkCapabilities != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (networkCapabilities
                                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/
                                    && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/) {
                                    if (networkCapabilities
                                            .hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                                        isConnectedWifi = true
                                    }
                                }
                            } else {
                                if (networkCapabilities
                                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/) {
                                    if (networkCapabilities
                                            .hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                                        isConnectedWifi = true
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                val info = getActiveNetworkInfo(context,
                    network)
                /*
			    Works on emulator and devices. Note the use of @link{NetworkInfo
			    .isAvailable} - without this, @link{NetworkInfo.isConnected} can return
			    @code{true} when Wi-Fi is disabled (http://stackoverflow.com/a/2937915)
			 */isConnectedWifi = (info != null && info.isAvailable && info.isConnected
                        && info.type == ConnectivityManager.TYPE_WIFI)
            }
            return isConnectedWifi
        }

        /**
         * Check if there is any connectivity to a Wifi network with airplane mode on.
         *
         * @param context The context.
         * @return @code{true} if we are connected to a Wifi network over airplane mode
         */
        fun isConnectedWifiOverAirplaneMode(context: Context): Boolean {
            return isConnectedWifi(context) && isAirplaneModeOn(context)
        }

        /**
         * Check if there is any connectivity to a Wifi network on a specific network with airplane mode on.
         *
         * @param context The context.
         * @param network The network
         * @return @code{true} if we are connected to a specific Wifi network over
         * airplane mode.
         */
        fun isConnectedWifiOverAirplaneMode(context: Context, network: Network?): Boolean {
            return isConnectedWifi(context, network) && isAirplaneModeOn(context)
        }

        /**
         * Checks via @link{TelephonyManager} if there is mobile data connection
         * @return @code{true} if the device has mobile data connection
         * @param context the context
         */
        fun isConnectedMobileTelephonyManager(context: Context): Boolean {
            val tm = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var state = 0
            if (tm != null) {
                state = tm.dataState
            }
            return state == TelephonyManager.DATA_CONNECTED
        }

        /**
         * Check if there is any connectivity to a mobile network.
         *
         * @param context The Context.
         * @param network The network
         * @return @code{true} if we are connected to a mobile network.
         */
        fun isConnectedMobile(context: Context, network: Network?): Boolean {
            var isConnectedMobile = false
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networkCapabilities = connectivityManager
                    .getNetworkCapabilities(network)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (network != null) {
                        if (networkCapabilities != null) {
                            if ((networkCapabilities
                                    .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /* API >= 21*/
                                        && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API>=23*/
                                        && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)) /*API>=28*/
                                && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) /*API>=28*/ {
                                if (networkCapabilities
                                        .hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                                    isConnectedMobile = true
                                }
                            }
                        }
                    }
                } else {
                    if (network != null) {
                        if (networkCapabilities != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (networkCapabilities
                                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/
                                    && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/) {
                                    if (networkCapabilities
                                            .hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                                        isConnectedMobile = true
                                    }
                                }
                            } else {
                                if (networkCapabilities
                                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/) {
                                    if (networkCapabilities
                                            .hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                                        isConnectedMobile = true
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                val info = getActiveNetworkInfo(context,
                    network)
                /*
			    Works on emulator and devices. Note the use of @link{NetworkInfo
			    .isAvailable} - without this, @link{NetworkInfo.isConnected} can return
			    @code{true} when Wi-Fi is disabled (http://stackoverflow.com/a/2937915)
			 */isConnectedMobile = (info != null && info.isAvailable && info.isConnected
                        && info.type == ConnectivityManager.TYPE_MOBILE)
            }
            return isConnectedMobile
        }

        /**
         * Check if there is any connectivity to a mobile network.
         *
         * @param context the Context.
         * @return true if we are connected to a mobile network.
         */
        fun isConnectedMobile(context: Context?): Boolean {
            var isConnectedWifi = false
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networks = connectivityManager.allNetworks
                for (network in networks) {
                    val networkCapabilities = connectivityManager
                        .getNetworkCapabilities(network)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        if (network != null) {
                            if (networkCapabilities != null) {
                                if ((networkCapabilities
                                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /* API >= 21*/
                                            && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/
                                            && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)) /*API >= 28*/
                                    && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) /*API >= 28*/ {
                                    if (networkCapabilities.hasTransport(
                                            NetworkCapabilities.TRANSPORT_CELLULAR)) {
                                        isConnectedWifi = true
                                        break
                                    }
                                }
                            }
                        }
                    } else {
                        if (network != null) {
                            if (networkCapabilities != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (networkCapabilities
                                            .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/
                                        && networkCapabilities.hasCapability(
                                            NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/) {
                                        if (networkCapabilities.hasTransport(
                                                NetworkCapabilities.TRANSPORT_CELLULAR)) {
                                            isConnectedWifi = true
                                            break
                                        }
                                    }
                                } else {
                                    if (networkCapabilities
                                            .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/) {
                                        if (networkCapabilities.hasTransport(
                                                NetworkCapabilities.TRANSPORT_CELLULAR)) {
                                            isConnectedWifi = true
                                            break
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                val infos = getAllNetworkInfo(context)
                for (info in infos) { /*
				    Works on emulator and devices. Note the use of @link{NetworkInfo
				    .isAvailable} - without this, @link{NetworkInfo.isConnected} can
				    return @code{true} when Wi-Fi is disabled (http://stackoverflow
				    .com/a/2937915)
				 */
                    isConnectedWifi = (info != null && info.isAvailable && info.isConnected
                            && info.type == ConnectivityManager.TYPE_MOBILE)
                    if (isConnectedWifi) {
                        break
                    }
                }
            }
            return isConnectedWifi
        }

        /**
         * Check if there is any connectivity to a ethernet network.
         *
         * @param context the Context.
         * @return true if we are connected to a ethernet network.
         */
        fun isConnectedEthernet(context: Context?): Boolean {
            var isConnectedEthernet = false
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networks = connectivityManager.allNetworks
                for (network in networks) {
                    val networkCapabilities = connectivityManager
                        .getNetworkCapabilities(network)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        if (network != null) {
                            if (networkCapabilities != null) {
                                if ((networkCapabilities
                                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /* API >= 21*/
                                            && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/
                                            && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)) /*API >= 28*/
                                    && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) /*API >= 28*/ {
                                    if (networkCapabilities.hasTransport(
                                            NetworkCapabilities.TRANSPORT_ETHERNET)) {
                                        isConnectedEthernet = true
                                        break
                                    }
                                }
                            }
                        }
                    } else {
                        if (network != null) {
                            if (networkCapabilities != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (networkCapabilities
                                            .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/
                                        && networkCapabilities.hasCapability(
                                            NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/) {
                                        if (networkCapabilities.hasTransport(
                                                NetworkCapabilities.TRANSPORT_ETHERNET)) {
                                            isConnectedEthernet = true
                                            break
                                        }
                                    }
                                } else {
                                    if (networkCapabilities
                                            .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/) {
                                        if (networkCapabilities.hasTransport(
                                                NetworkCapabilities.TRANSPORT_ETHERNET)) {
                                            isConnectedEthernet = true
                                            break
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                val infos = getAllNetworkInfo(context)
                for (info in infos) { /*
				    Works on emulator and devices. Note the use of @link{NetworkInfo
				    .isAvailable} - without this, @link{NetworkInfo.isConnected} can
				    return @code{true} when Wi-Fi is disabled (http://stackoverflow
				    .com/a/2937915)
				 */
                    isConnectedEthernet = (info != null && info.isAvailable && info.isConnected
                            && info.type == ConnectivityManager.TYPE_ETHERNET)
                    if (isConnectedEthernet) {
                        break
                    }
                }
            }
            return isConnectedEthernet
        }

        /**
         * Check if a specific network has ethernet connectivity.
         *
         * @param context The Context.
         * @param network The network
         * @return true if we are connected to a ethernet network.
         */
        fun isConnectedEthernet(context: Context, network: Network?): Boolean {
            var isConnectedEthernet = false
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networkCapabilities = connectivityManager
                    .getNetworkCapabilities(network)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (network != null) {
                        if (networkCapabilities != null) {
                            if ((networkCapabilities
                                    .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /* API >= 21*/
                                        && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/
                                        && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)) /*API >= 28*/
                                && networkCapabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_FOREGROUND)) /*API >= 28*/ {
                                if (networkCapabilities
                                        .hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                                    isConnectedEthernet = true
                                }
                            }
                        }
                    }
                } else {
                    if (network != null) {
                        if (networkCapabilities != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (networkCapabilities
                                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/
                                    && networkCapabilities.hasCapability(
                                        NetworkCapabilities.NET_CAPABILITY_VALIDATED) /*API >= 23*/) {
                                    if (networkCapabilities
                                            .hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                                        isConnectedEthernet = true
                                    }
                                }
                            } else {
                                if (networkCapabilities
                                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) /*API >= 21*/) {
                                    if (networkCapabilities
                                            .hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                                        isConnectedEthernet = true
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                val info = getActiveNetworkInfo(context,
                    network)
                /*
			    Works on emulator and devices. Note the use of @link{NetworkInfo
			    .isAvailable} - without this, @link{NetworkInfo.isConnected} can return
			    @code{true} when Wi-Fi is disabled (http://stackoverflow.com/a/2937915)
			 */isConnectedEthernet = (info != null && info.isAvailable && info.isConnected
                        && info.type == ConnectivityManager.TYPE_ETHERNET)
            }
            return isConnectedEthernet
        }

        /**
         * Check if there is fast connectivity.
         *
         * @param context the Context.
         * @return true if we have "fast" connectivity.
         */
        fun isConnectedFast(context: Context): Boolean {
            var isConnectedFast = false
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                val networkInfos = getAllNetworkInfo(context)
                for (networkInfo in networkInfos) {
                    isConnectedFast = (networkInfo != null && networkInfo.isAvailable
                            && networkInfo.isConnected
                            && isConnectionFast(networkInfo.type, networkInfo.subtype))
                    if (isConnectedFast) {
                        break
                    }
                }
            } else {
                val connectivityManager = getConnectivityManager(context)
                val allNetworks = connectivityManager.allNetworks
                for (network in allNetworks) {
                    if (network != null && isConnected(context, network)) {
                        val networkCapabilities = connectivityManager
                            .getNetworkCapabilities(network)
                        if (networkCapabilities != null) {
                            val linkDownstreamBandwidthKbps = networkCapabilities
                                .linkDownstreamBandwidthKbps
                            val linkUpstreamBandwidthKbps = networkCapabilities
                                .linkUpstreamBandwidthKbps
                            if (linkDownstreamBandwidthKbps >= minimumSpeedForFastConnection
                                && linkUpstreamBandwidthKbps >= minimumSpeedForFastConnection) {
                                isConnectedFast = true
                                break
                            }
                        }
                    }
                }
            }
            return isConnectedFast
        }

        /**
         * Check if there is fast connectivity over a specific network.
         *
         * @param context The Context.
         * @param network The network
         * @return @code{true} if we have "fast" connectivity.
         */
        fun isConnectedFast(context: Context, network: Network?): Boolean {
            var isConnectedFast = false
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                val networkInfo = getActiveNetworkInfo(context, network)
                isConnectedFast = (networkInfo != null && networkInfo.isAvailable
                        && networkInfo.isConnected
                        && isConnectionFast(networkInfo.type, networkInfo.subtype))
            } else {
                val connectivityManager = getConnectivityManager(context)
                if (network != null && isConnected(context, network)) {
                    val networkCapabilities = connectivityManager
                        .getNetworkCapabilities(network)
                    if (networkCapabilities != null) {
                        val linkDownstreamBandwidthKbps = networkCapabilities
                            .linkDownstreamBandwidthKbps
                        val linkUpstreamBandwidthKbps = networkCapabilities
                            .linkUpstreamBandwidthKbps
                        if (linkDownstreamBandwidthKbps >= minimumSpeedForFastConnection
                            && linkUpstreamBandwidthKbps >= minimumSpeedForFastConnection) {
                            isConnectedFast = true
                        }
                    }
                }
            }
            return isConnectedFast
        }

        /**
         * Determines if the airplane mode is on
         * @param context The context
         * @return @code{true} if the airplane mode is on
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun isAirplaneModeOn(context: Context): Boolean {
            val isAirplaneModeOn: Boolean = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Settings.System.getInt(context.contentResolver,
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0
            } else {
                Settings.Global.getInt(context.contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0
            }
            return isAirplaneModeOn
        }

        /**
         * Check if the connection is fast
         * @param type The network type
         * @param subType The network subtype
         * @return @code {true} if the connection is fast
         */
        fun isConnectionFast(type: Int, subType: Int): Boolean {
            val connectedFast: Boolean
            connectedFast = if (type == ConnectivityManager.TYPE_WIFI
                || type == ConnectivityManager.TYPE_ETHERNET) {
                true
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                when (subType) {
                    TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_LTE -> true
                    TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_IDEN, TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
                    else -> false
                }
            } else {
                false
            }
            return connectedFast
        }

        /**
         * Tells whether there is Internet connection
         * @param context The context
         * @param hosts The hosts
         * @return @code {true} if there is Internet connection
         */
        fun isConnectedToInternet(context: Context, host: String): Boolean {
            var isAvailable = false
            if (isConnected(context)) {
                try {

                    if (isHostAvailable(host)) {
                        isAvailable = true
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return isAvailable
        }

        /**
         * Checks if the host is available
         * @param hostName The name of the host
         * @return @code{true} if the host is available
         * @throws IOException If it happens
         */
        @Throws(IOException::class, IllegalBlockingModeException::class, IllegalArgumentException::class)
        fun isHostAvailable(hostName: String): Boolean {
            var isHostAvailable: Boolean
            isHostAvailable = false
            try {
                Socket().use { socket ->
                    val port = 80
                    val socketAddress = InetSocketAddress(hostName, port)
                    socket.connect(socketAddress, 3000)
                    isHostAvailable = true
                }
            } catch (unknownHost: UnknownHostException) {
                isHostAvailable = false
            }
            return isHostAvailable
        }
    }
}