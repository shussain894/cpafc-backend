package app;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SeedData {

    public static void populate(MongoDatabase database) {
        MongoCollection<Document> childrenCollection = database.getCollection("children");
        MongoCollection<Document> coachCollection = database.getCollection("coaches");

        childrenCollection.deleteMany(new Document());
        coachCollection.deleteMany(new Document());

        List<String> teams = Arrays.asList(
                "U6 Lions", "U6 Tigers", "U8 Lions", "U8 Tigers",
                "U10 Lions", "U10 Tigers", "U13 Lions", "U13 Tigers"
        );

        List<Document> coaches = Arrays.asList(
                new Document("firstName", "Shah").append("lastName", "Hussain").append("team", "U13 Tigers"),
                new Document("firstName", "Alice").append("lastName", "Brown").append("team", "U6 Lions"),
                new Document("firstName", "Maya").append("lastName", "Murray").append("team", "U8 Tigers"),
                new Document("firstName", "James").append("lastName", "Owen").append("team", "U10 Lions"),
                new Document("firstName", "Liam").append("lastName", "Clark").append("team", "U13 Lions")
        );
        coachCollection.insertMany(coaches);

        // Children
        String[] firstNames = {"Amir", "Lena", "Kaylen", "Rio", "Lamar", "Zara", "Ethan", "Noah", "Sophia", "Emily", "Mason", "Ava", "Lucas", "Mila", "Logan", "Nina", "Leo", "Maya", "Zain", "Ellie"};
        String[] lastNames = {"Smith", "Brown", "Jackson", "Khan", "Taylor", "Patel", "Clark", "Lewis", "Hall", "Young"};

        Random rand = new Random();

        for (int i = 0; i < 20; i++) {
            String firstName = firstNames[rand.nextInt(firstNames.length)];
            String lastName = lastNames[rand.nextInt(lastNames.length)];
            int age = rand.nextInt(8) + 6;
            String team = rand.nextBoolean() ? teams.get(rand.nextInt(teams.size())) : "Unassigned";

            Document child = new Document("firstName", firstName)
                    .append("lastName", lastName)
                    .append("age", age)
                    .append("team", team);

            childrenCollection.insertOne(child);
        }

        System.out.println("✅ Seeded 5 coaches, 20 children, and 8 teams (some assigned, some unassigned).");
    }
}
