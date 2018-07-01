package br.com.firstsoft.opentheater.util

import br.com.firstsoft.opentheater.model.AppResponse
import br.com.firstsoft.opentheater.repository.BaseRepository
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * Created by danilolemes on 28/02/2018.
 */
class HttpHelper {

    companion object {

        fun <T> callback(success: (Response<T>) -> Unit, failure: (t: Throwable) -> Unit): Callback<T>? {
            return object : Callback<T> {
                override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) = success(response)
                override fun onFailure(call: Call<T>, t: Throwable) = failure(t)
            }
        }

        fun parseResponse(listener: (AppResponse?, Throwable?) -> Unit): Callback<AppResponse>? {
            return callback(
                    { response ->
                        if (response.body() != null) listener(response.body(), null) else listener(null, Throwable(response.errorBody()?.string()))
                    },
                    { throwable -> listener(null, throwable) }
            )
        }

//        fun parseResponse(listener: (Movie?, Throwable?) -> Unit): Callback<AppResponse>? {
//            return callback(
//                    { response ->
//                        if (response.body() != null) listener(response.body(), null) else listener(null, Throwable(response.errorBody()?.string()))
//                    },
//                    { throwable -> listener(null, throwable) }
//            )
//        }

        fun <T> parseList(lista: List<*>, classOfT: Class<T>): ArrayList<T> {
            val list = ArrayList<T>()

            lista.map { it as LinkedTreeMap<*, *> }
                    .map { BaseRepository.gson.toJsonTree(it).asJsonObject }
                    .mapTo(list) { BaseRepository.gson.fromJson(it, classOfT as Type) }

            return list
        }

        fun <T> parseObject(obj: Any, classOfT: Class<T>): T {
            val treeMap = obj as LinkedTreeMap<*, *>
            val jsonObject = BaseRepository.gson.toJsonTree(treeMap).asJsonObject
            return BaseRepository.gson.fromJson(jsonObject, classOfT as Type)
        }
    }
}