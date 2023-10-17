/*package com.example.googlemapapi

import java.util.UUID
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import aws.sdk.kotlin.services.dynamodb.model.DynamoDbException
import kotlin.system.exitProcess

class Database {
    suspend fun putItemInTable(
        ddb: DynamoDbClient,
        tableNameVal: String,
        uuid: UUID = UUID.randomUUID(),
        name: String,
        departurePoint: String,
        destination: String,
        departureTime: String,
        capacity: String
    ) {
        val itemValues = mutableMapOf<String, AttributeValue>()

        // Add all content to the table.
        itemValues["uuid"] = AttributeValue.S(uuid.toString())
        itemValues["name"] = AttributeValue.S(name)
        itemValues["departurePoint"] = AttributeValue.S(departurePoint)
        itemValues["destination"] = AttributeValue.S(destination)
        itemValues["departureTime"] = AttributeValue.S(departureTime)
        itemValues["capacity"] = AttributeValue.S(capacity)

        val request = PutItemRequest {
            tableName = tableNameVal
            item = itemValues
        }

        try {
            ddb.putItem(request)
            println("A new item was placed into $tableNameVal.")

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }
    }
}*/


/*
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import java.util.UUID

class Database {
    fun putItemInTable(
        ddb: DynamoDbClient,
        tableNameVal: String,
        uuid: UUID = UUID.randomUUID(),
        name: String,
        departurePoint: String,
        destination: String,
        departureTime: String,
        capacity: String
    ) {
        val itemValues = mutableMapOf<String, AttributeValue>()

        // Add all content to the table.
        itemValues["uuid"] = AttributeValue.builder().s(uuid.toString()).build()
        itemValues["name"] = AttributeValue.builder().s(name).build()
        itemValues["departurePoint"] = AttributeValue.builder().s(departurePoint).build()
        itemValues["destination"] = AttributeValue.builder().s(destination).build()
        itemValues["departureTime"] = AttributeValue.builder().s(departureTime).build()
        itemValues["capacity"] = AttributeValue.builder().s(capacity).build()

        val request = PutItemRequest.builder()
            .tableName(tableNameVal)
            .item(itemValues)
            .build()

        try {
            ddb.putItem(request)
            println("A new item was placed into $tableNameVal.")

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            System.exit(0)
        }
    }
}*/

package com.example.googlemapapi

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.okHttpClient
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.UUID

class DataBase {

    private val apolloClient = ApolloClient.Builder()
        .serverUrl("http://10.0.2.2:8080/graphql")
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
