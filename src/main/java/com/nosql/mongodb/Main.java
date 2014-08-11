package com.nosql.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.nosql.mongodb.db.MongoDB;

public class Main {

    public static void main(String[] args) throws Exception {
        MongoClient client = MongoDB.connect();
        DBCollection users = client.getDB("payment").getCollection("users");
        BasicDBObject doc = new BasicDBObject("name", "MongoDB").append("type", "database")
                .append("count", 1)
                .append("info", new BasicDBObject("x", 203).append("y", 102));
        users.insert(doc);
        client.close();
    }

}
