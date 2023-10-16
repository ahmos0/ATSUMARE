package com.example.googlemapapi

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
}
