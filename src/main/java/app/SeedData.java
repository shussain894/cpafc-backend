package app;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

public class SeedData {

    public static void populate(MongoDatabase database) {
        MongoCollection<Document> childrenCollection = database.getCollection("children");
        MongoCollection<Document> coachCollection = database.getCollection("coaches");
        MongoCollection<Document> teamsCollection = database.getCollection("teams");

        childrenCollection.deleteMany(new Document());
        coachCollection.deleteMany(new Document());

        teamsCollection.deleteMany(new Document());

        List<String> teams = Arrays.asList(
                "U6 Lions", "U6 Tigers", "U7 Lions", "U7 Tigers", "U8 Lions", "U8 Tigers", "U9 Lions", "U9 Tigers",
                "U10 Lions", "U10 Tigers", "U11 Lions", "U11 Tigers", "U12 Lions", "U12 Tigers", "U13 Lions", "U13 Tigers",
                "U14 Lions"
        );

        List<Document> teamDocs = teams.stream()
                .map(teamName -> new Document("name", teamName))
                .toList();

        teamsCollection.insertMany(teamDocs);

        List<Document> coaches = Arrays.asList(
                new Document("_id", new ObjectId("665a92c79c1fa42801759001"))
                        .append("firstName", "Shah").append("lastName", "Hussain").append("team", "U13 Tigers"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759002"))
                        .append("firstName", "Alice").append("lastName", "Brown").append("team", "U6 Lions"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759003"))
                        .append("firstName", "Maya").append("lastName", "Murray").append("team", "U8 Tigers"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759004"))
                        .append("firstName", "James").append("lastName", "Owen").append("team", "U10 Lions"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759005"))
                        .append("firstName", "Liam").append("lastName", "Clark").append("team", "U13 Lions")
        );
        coachCollection.insertMany(coaches);

        List<Document> children = Arrays.asList(
                new Document("_id", new ObjectId("665a92c79c1fa42801759401"))
                        .append("firstName", "Amir").append("lastName", "Smith").append("age", 6).append("team", "U8 Tigers"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759402"))
                        .append("firstName", "Lena").append("lastName", "Brown").append("age", 11).append("team", "U13 Tigers"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759403"))
                        .append("firstName", "Kaylen").append("lastName", "Jackson").append("age", 6).append("team", "Unassigned"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759404"))
                        .append("firstName", "Rio").append("lastName", "Khan").append("age", 13).append("team", "U6 Lions"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759405"))
                        .append("firstName", "Lamar").append("lastName", "Taylor").append("age", 10).append("team", "U13 Tigers"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759406"))
                        .append("firstName", "Zara").append("lastName", "Patel").append("age", 8).append("team", "Unassigned"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759407"))
                        .append("firstName", "Ethan").append("lastName", "Clark").append("age", 12).append("team", "U13 Tigers"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759408"))
                        .append("firstName", "Noah").append("lastName", "Lewis").append("age", 9).append("team", "Unassigned"),
                new Document("_id", new ObjectId("665a92c79c1fa42801759409"))
                        .append("firstName", "Sophia").append("lastName", "Hall").append("age", 10).append("team", "U8 Tigers"),
                new Document("_id", new ObjectId("665a92c79c1fa4280175940a"))
                        .append("firstName", "Emily").append("lastName", "Young").append("age", 11).append("team", "U13 Tigers")
        );

        childrenCollection.insertMany(children);

        System.out.println("✅ Seeded 5 coaches, 10 children, and 8 teams (some assigned, some unassigned).");
    }
}
