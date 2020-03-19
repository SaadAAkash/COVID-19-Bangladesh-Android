package ninja.saad.palaocorona.base.data.local

import android.content.Context
import io.reactivex.Single
import ninja.saad.palaocorona.data.faq.Faq
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class AssetsDataSource @Inject constructor() {
    
    fun getFaq(context: Context): Single<MutableList<Faq>> {
        return Single.create { emitter ->
            try {
                val jsonArray = loadJSONArrayFromAsset(context, "faq.json")
                val faqList = mutableListOf<Faq>()
                for (i in 0 until (jsonArray?.length() ?: 0)) {
                    val item = jsonArray?.getJSONObject(i)
                    val faq = Faq(item?.getString("title") ?: "", item?.getString("body") ?: "")
                    faqList.add(faq)
                }
                emitter.onSuccess(faqList)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
        
    }
    
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