package com.example.googlemapapi
import java.util.UUID
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import aws.sdk.kotlin.services.dynamodb.model.DynamoDbException
import kotlin.system.exitProcess

/*fun main(){
    val randomUUID = UUID.randomUUID()
    println("UUID: $randomUUID")
}*/

class Database {

    suspend fun putItemInTable(
        ddb: DynamoDbClient,
        tableNameVal: String,
        key: String,
        keyVal: String,
        moneyTotal: String,
        moneyTotalValue: String,
        name: String,
        nameValue: String,
        email: String,
        emailVal: String
    ) {
        val itemValues = mutableMapOf<String, AttributeValue>()

        // Add all content to the table.
        itemValues[key] = AttributeValue.S(keyVal)
        itemValues[moneyTotal] =  AttributeValue.S(moneyTotalValue)
        itemValues[name] = AttributeValue.S(nameValue)
        itemValues[email] = AttributeValue.S(emailVal)

        val request = PutItemRequest {
            tableName=tableNameVal
            item = itemValues
        }

        try {
            ddb.putItem(request)
            println(" A new item was placed into $tableNameVal.")

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }
    }
}


