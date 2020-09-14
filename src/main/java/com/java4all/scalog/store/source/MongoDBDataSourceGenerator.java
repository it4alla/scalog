package com.java4all.scalog.store.source;

import com.java4all.scalog.annotation.LoadLevel;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.List;

import static com.java4all.scalog.configuration.Constants.DEFAULT_MONGO_PORT;
import static com.java4all.scalog.configuration.Constants.DEFAULT_MONGO_URL;
import static com.java4all.scalog.configuration.Constants.DEFAULT_MONGO_USERNAME;
import static com.java4all.scalog.configuration.Constants.DEFAULT_MONGO_DB;
import static com.java4all.scalog.configuration.Constants.DEFAULT_MONGO_PASSWORD;

/**
 * @author dsl
 */
@LoadLevel(name = "mongodb")
public class MongoDBDataSourceGenerator implements SourceGenerator{

    @Override
    public Object generateSource() {
        List<ServerAddress> adds = new ArrayList<>();

        ServerAddress serverAddress = new ServerAddress(DEFAULT_MONGO_URL, DEFAULT_MONGO_PORT);
        adds.add(serverAddress);

        List<MongoCredential> credentials = new ArrayList<>();

        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(DEFAULT_MONGO_USERNAME, DEFAULT_MONGO_DB, DEFAULT_MONGO_PASSWORD.toCharArray());
        credentials.add(mongoCredential);

        MongoClient mongoClient = new MongoClient(adds, credentials);
        return mongoClient;
    }
}
