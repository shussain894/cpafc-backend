import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoTest {
    public static void main(String[] args) {
     try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

        System.out.println("Connected to the database successfully");

        MongoDatabase database = mongoClient.getDatabase("cpafc-test");

        MongoCollection<Document> collection = database.getCollection("children");

        Document child = new Document("firstName", "Rio")
                .append("lastName", "Lacey")
                .append("age", 12)
                .append("team", "Under 13 Lions");

        collection.insertOne(child);

        System.out.println("Inserted child" + child.toJson());
     }
    }
}
