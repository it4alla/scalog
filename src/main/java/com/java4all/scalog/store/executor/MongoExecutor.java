package com.java4all.scalog.store.executor;

import com.java4all.scalog.annotation.LoadLevel;
import com.java4all.scalog.store.LogInfoDto;
import com.java4all.scalog.utils.BaseHelper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.java4all.scalog.configuration.Constants.DEFAULT_MONGO_DB;
import static com.java4all.scalog.configuration.Constants.DEFAULT_MONGO_DOCUMENT;

/**
 * MongoDB Executor
 */
@LoadLevel(name = "mongodb")
public class MongoExecutor implements BaseSqlExecutor{

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoExecutor.class);

    private MongoClient mongoClient = null;

    public MongoExecutor() {
    }

    public MongoExecutor(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void insert(LogInfoDto dto) throws Exception {
        MongoDatabase database = mongoClient.getDatabase(DEFAULT_MONGO_DB);
        //获取集合
        MongoCollection<Document> collection = database.getCollection(DEFAULT_MONGO_DOCUMENT);
        //要插入的数据
        Map<String, Object> map = BaseHelper.objectToMap(dto);
        Document document = new Document(map);
        //插入一个文档
        collection.insertOne(document);
    }
}
