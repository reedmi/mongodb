package com.nosql.mongodb.db;

import com.mongodb.MongoClient;
import com.nosql.mongodb.util.PropsUtil;

public class MongoDB {

    static MongoClient client = null;

    public static MongoClient connect() {
        String host = PropsUtil.getProperty("mongo.ip");
        int port = Integer.parseInt(PropsUtil.getProperty("mongo.port"));
        try {
            client = new MongoClient(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

}
