package children;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import children.ChildData;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ChildService {
    private MongoCollection<Document> childrenCollection;

    public ChildService() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("cpafc-test");
        childrenCollection = database.getCollection("children");
    }

    public void insertChildren(List<Document> children) {
        for (Document doc : children) {
            childrenCollection.insertOne(doc);
            System.out.println("child added " + doc.getString("firstName") + " " + doc.getString("lastName"));
        }
    }


    public List<Child> getAllChildren() {
        List<Child> children = new ArrayList<>();
        for (Document doc : childrenCollection.find()) {
            String firstName = doc.getString("firstName");
            String lastName = doc.getString("lastName");
            int age = doc.getInteger("age");
            String team = doc.getString("team");
            children.add(new Child(firstName, lastName, age, team));
        }
        return children;
    }

    public static void main(String[] args) {
        ChildService service = new ChildService();

        service.childrenCollection.drop();

        service.insertChildren(ChildData.getSampleChildren());

        boolean updated = service.updateChildTeam("Rio", "Lacey", "U13 Tigers");
        System.out.println("Update success: " + updated);

        boolean assigned = service.assignChildToTeam("Kaylen", "Smith", "Under 13 Tigers");
        System.out.println("Assign success: " + assigned);

        boolean deleted = service.deleteChild("Amir", "Khan");
        System.out.println("Delete success: " + deleted);

        System.out.println("All children in DB:");
        service.getAllChildren().forEach(System.out::println);
    }


    public boolean updateChildTeam(String firstName, String lastName, String newTeam) {
        Document filter = new Document("firstName", firstName)
                .append("lastName", lastName);

        Document update = new Document("$set", new Document("team", newTeam));

        UpdateResult result = childrenCollection.updateOne(filter, update);

        if (result.getModifiedCount() > 0) {
            System.out.println("Updated " + firstName + "'s team to " + newTeam);
            return true;
        } else {
            System.out.println("No matching child found to update.");
            return false;
        }
    }

    public boolean deleteChild(String firstName, String lastName) {
        Document filter = new Document("firstName", firstName)
                .append("lastName", lastName);

        DeleteResult result = childrenCollection.deleteOne(filter);

        if (result.getDeletedCount() > 0) {
            System.out.println("Deleted child: " + firstName + " " + lastName);
            return true;
        } else {
            System.out.println("No matching child found to delete.");
            return false;
        }
    }

    public boolean assignChildToTeam(String firstName, String lastName, String team) {
        return updateChildTeam(firstName, lastName, team);
    }

}