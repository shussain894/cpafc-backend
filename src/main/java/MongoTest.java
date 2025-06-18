import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import java.util.List;


public class MongoTest {
    public static void main(String[] args) {
     try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

        System.out.println("Connected to the database successfully");

        MongoDatabase database = mongoClient.getDatabase("cpafc-test");

        MongoCollection<Document> collection = database.getCollection("children");
         collection.drop();

         List<Document> children = ChildData.getSampleChildren();
         collection.insertMany(children);
         System.out.println("Sample children inserted!");

        // Find a child by first name + last name
        Document query = new Document("firstName", "Rio").append("lastName", "Lacey");
        // Update the team
        Document update = new Document("$set", new Document("team", "U13 Tigers"));

        collection.updateOne(query, update);

        System.out.println("Updated Rio's team to U13 Tigers");

         // DELETE child by first name and last name
         Document deleteQuery = new Document("firstName", "Rio").append("lastName", "Lacey");
         DeleteResult deleteResult = collection.deleteOne(deleteQuery);

         System.out.println("Deleted count: " + deleteResult.getDeletedCount());

         for (Document doc : collection.find()) {
             System.out.println("Child found: " + doc.toJson());
         }

         // FIND children by team
         String teamToFind = "U13 Tigers";
         FindIterable<Document> teamChildren = collection.find(new Document("team", teamToFind));

         System.out.println("Children playing for " + teamToFind + ":");
         for (Document doc : teamChildren) {
             System.out.println("Child found: " + doc.toJson());
         }

     }
    }
}
