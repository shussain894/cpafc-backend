package app;

import com.mongodb.client.MongoDatabase;

public class App {
    public static void main(String[] args) {
        MongoDatabase db = MongoConnection.getDatabase();
        SeedData.populate(db);
    }
}

