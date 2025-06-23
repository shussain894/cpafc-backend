package app;

import coaches.CoachService;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Main {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("soccerDB");

        CoachService coachService = new CoachService(database);

        boolean success = coachService.registerCoach("Shah", "Hussain", "U13 Tigers");

        if (success) {
            System.out.println("✅ Coach registration successful!");
        } else {
            System.out.println("❌ Coach registration failed.");
        }
    }
}
