package com.java4all.scalog.store.executor;

import com.alibaba.fastjson.JSON;
import com.java4all.scalog.annotation.LoadLevel;
import com.java4all.scalog.store.LogInfoDto;
import com.java4all.scalog.store.MongoLogInfo;
import com.java4all.scalog.utils.BaseHelper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.java4all.scalog.configuration.Constants.DEFAULT_MONGO_DB;

/**
 * MongoDB Executor
 */
@LoadLevel(name = "mongodb")
public class MongoExecutor implements BaseSqlExecutor{

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoExecutor.class);

    protected MongoDatabase dataSource = null;

    public MongoExecutor() {
    }

    public MongoExecutor(MongoDatabase dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(LogInfoDto dto) throws Exception {
        //获取集合
        MongoCollection<Document> collection = dataSource.getCollection(DEFAULT_MONGO_DB);

        //要插入的数据
        MongoLogInfo logInfo = BaseHelper.r2t(dto, MongoLogInfo.class);
        Map map = JSON.parseObject(JSON.toJSONString(logInfo),Map.class);
        Document document = new Document(map);
        //插入一个文档
        collection.insertOne(document);
    }
}
