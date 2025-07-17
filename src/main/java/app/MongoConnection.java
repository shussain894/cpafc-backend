//package app;
//
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoDatabase;
//
//public class MongoConnection {
//
//    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
//    private static final String DB_NAME = "cpafc-test";
//
//    private static final MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
//    private static final MongoDatabase database = mongoClient.getDatabase(DB_NAME);
//
//    public static MongoDatabase getDatabase() {
//        return database;
//    }
//}
