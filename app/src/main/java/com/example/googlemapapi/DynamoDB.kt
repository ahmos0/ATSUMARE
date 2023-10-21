package com.example.googlemapapi

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DataBase {

    private val apolloClient = ApolloClient.Builder()
        .serverUrl(BuildConfig.serverEndPoint)
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .build()

    suspend fun executeMutation(uuid: String,
                                name: String,
                                departure: String,
                                destination: String,
                                time: String,
                                capacity: Int) {
        val mutation = PutItemMutation(
            uuid = uuid,
            name = name,
            departure = departure,
            destination = destination,
            time = time,
            capacity = capacity
        )

        try {
            println("ok")
            val call: ApolloCall<PutItemMutation.Data> = apolloClient.mutate(mutation)
            val response: ApolloResponse<PutItemMutation.Data> = call.execute()
            val item = response.data?.putItem
            println(item)
        } catch (e: ApolloException) {
            // Handle the error
        }
    }
}
