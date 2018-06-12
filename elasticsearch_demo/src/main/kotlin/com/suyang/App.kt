package com.suyang

import org.apache.http.HttpHost
import org.elasticsearch.action.admin.indices.alias.Alias
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentType
import java.awt.event.ActionListener


fun main(args: Array<String>) {
    val client = RestHighLevelClient(
            RestClient.builder(
                    HttpHost("127.0.0.1", 9200, "http")))

    //创建索引
    val request = CreateIndexRequest("twitter")
    //索引的设置
    request.settings(Settings.builder()
            .put("index.number_of_shards", 3)
            .put("index.number_of_replicas", 2)
    )
    //索引mapping
    request.mapping("tweet",
            "  {\n" +
                    "    \"tweet\": {\n" +
                    "      \"properties\": {\n" +
                    "        \"message\": {\n" +
                    "          \"type\": \"text\"\n" +
                    "        }\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }",
            XContentType.JSON)

    //别名
    request.alias(
            Alias("twitter_alias")
    )

    //timeout
    request.timeout(TimeValue.timeValueMinutes(2))
    request.timeout("2m")
    val createIndexResponse = client.indices().create(request)
    println(createIndexResponse.index())
    println(createIndexResponse.isAcknowledged)

    client.close()
}