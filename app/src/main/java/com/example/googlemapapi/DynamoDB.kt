package com.example.googlemapapi

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.okHttpClient
import com.example.googlemapapi.type.PassengerInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import software.amazon.awssdk.services.dynamodb.endpoints.internal.Value.Str

class DataBase {

    private val apolloClient = ApolloClient.Builder()
        .serverUrl(BuildConfig.serverEndPoint)
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .build()

    suspend fun executeMutation(
        uuid: String,
        name: String,
        departure: String,
        destination: String,
        time: String,
        capacity: Int,
        passenger: Int,
        passengers: List<PassengerInput>? = null
    ): Result<PutItemMutation.PutItem?> {

        val convertedPassengers = passengers?.map { PassengerInput(it.namelist, it.comment) }

        val mutation = PutItemMutation(
            uuid = uuid,
            name = name,
            departure = departure,
            destination = destination,
            time = time,
            capacity = capacity,
            passenger = passenger,
            passengers = convertedPassengers?.let { Optional.Present(it) } ?: Optional.Absent
        )

        return try {
            val call: ApolloCall<PutItemMutation.Data> = apolloClient.mutation(mutation)
            val response: ApolloResponse<PutItemMutation.Data> = call.execute()
            val item = response.data?.putItem
            Result.success(item)

        } catch (e: ApolloException) {
            Result.failure(e)
        }
    }

    suspend fun fetchAllItems(): List<AllItemsQuery.AllItem>? {
        val query = AllItemsQuery()

        return withContext(Dispatchers.IO) {
            try {
                val response: ApolloResponse<AllItemsQuery.Data> = apolloClient.query(query).execute()
                response.data?.allItems?.filterNotNull()
            } catch (e: ApolloException) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun incrementPassenger(uuid: String, name: String, namelist: String,comment: String) {
        val newPassengerInput = PassengerInput(namelist, comment)
        val passengerInputList = listOf(newPassengerInput)
        val mutation = IncrementPassengerMutation(uuid, name, Optional.Present(passengerInputList))

        try {
            val call: ApolloCall<IncrementPassengerMutation.Data> = apolloClient.mutation(mutation)
            val response: ApolloResponse<IncrementPassengerMutation.Data> = call.execute()
            val updatedItem = response.data?.incrementPassenger
        } catch (e: ApolloException) {
            // エラー処理
            e.printStackTrace()
        }
    }

}
