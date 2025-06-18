import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class TeamsList {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("cpafc-test");
            MongoCollection<Document> teamsCollection = database.getCollection("teams");

            teamsCollection.drop();

            List<Document> teams = new ArrayList<>();

            for (int age = 6; age <= 15; age++) {
                String ageGroup = "U" + age;
                teams.add(new Document("name", ageGroup + " Lions").append("ageGroup", ageGroup));
                teams.add(new Document("name", ageGroup + " Tigers").append("ageGroup", ageGroup));
            }

            teamsCollection.insertMany(teams);

            System.out.println("✅ Inserted teams:");
            for (Document doc : teamsCollection.find()) {
                System.out.println("" + doc.toJson());
            }
        }
    }
}
