import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

public class MongoTest {
    public static void main(String[] args) {
     try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

        System.out.println("Connected to the database successfully");

        MongoDatabase database = mongoClient.getDatabase("cpafc-test");

        MongoCollection<Document> collection = database.getCollection("children");
         collection.drop();

        Document child = new Document("firstName", "Rio")
                .append("lastName", "Lacey")
                .append("age", 12)
                .append("team", "U13 Lions");

        collection.insertOne(child);
        System.out.println("Inserted child" + child.toJson());

        // Find a child by first name + last name
        Document query = new Document("firstName", "Rio").append("lastName", "Lacey");
        // Update the team
        Document update = new Document("$set", new Document("team", "U13 Tigers"));

        collection.updateOne(query, update);

        System.out.println("✅ Updated Rio's team to U13 Tigers");

         // DELETE child by first name and last name
         Document deleteQuery = new Document("firstName", "Rio").append("lastName", "Lacey");
         DeleteResult deleteResult = collection.deleteOne(deleteQuery);

         System.out.println("🗑️ Deleted count: " + deleteResult.getDeletedCount());

         for (Document doc : collection.find()) {
             System.out.println("👦 Child found: " + doc.toJson());
         }

     }
    }
}
