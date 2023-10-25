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
                                capacity: Int,
                                passenger: Int) {
        val mutation = PutItemMutation(
            uuid = uuid,
            name = name,
            departure = departure,
            destination = destination,
            time = time,
            capacity = capacity,
            passenger = passenger
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

    suspend fun fetchAllItems(): List<AllItemsQuery.AllItem>? {
        val query = AllItemsQuery()

        try {
            val call: ApolloCall<AllItemsQuery.Data> = apolloClient.query(query)
            val response: ApolloResponse<AllItemsQuery.Data> = call.execute()
            return response.data?.allItems?.filterNotNull()
        } catch (e: ApolloException) {
            // Handle the error
            e.printStackTrace()
            return null
        }
    }

    suspend fun incrementPassenger(uuid: String, name: String) {
        val mutation = IncrementPassengerMutation(uuid, name)

        try {
            val call: ApolloCall<IncrementPassengerMutation.Data> = apolloClient.mutate(mutation)
            val response: ApolloResponse<IncrementPassengerMutation.Data> = call.execute()
            val updatedItem = response.data?.incrementPassenger
            println("Updated Passenger Count: ${updatedItem?.passenger}")
        } catch (e: ApolloException) {
            // エラー処理
            e.printStackTrace()
        }
    }

}
