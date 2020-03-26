package xyz.palaocorona.base.data.local

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class AssetsDataSource @Inject constructor() {
    
    private fun loadJSONObjectFromAsset(context: Context, fileName: String): JSONObject? {
        var json: String? = null
        var jsonObject: JSONObject? = null
        json = try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        try {
            jsonObject = JSONObject(json)
        } catch (e: JSONException) {
            e.printStackTrace()
            throw e
        }
        return jsonObject
    }
    
    private fun loadJSONArrayFromAsset(context: Context, fileName: String): JSONArray? {
        var json: String? = null
        var jsonArray: JSONArray? = null
        json = try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        try {
            jsonArray = JSONArray(json)
        } catch (e: JSONException) {
            e.printStackTrace()
            throw e
        }
        return jsonArray
    }
}