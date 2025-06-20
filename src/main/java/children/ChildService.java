package children;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

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
        }
    }

    public List<Child> getAllChildren() {
        List<Child> children = new ArrayList<>();
        for (Document doc : childrenCollection.find()) {
            ObjectId id = doc.getObjectId("_id");
            String firstName = doc.getString("firstName");
            String lastName = doc.getString("lastName");
            int age = doc.getInteger("age");
            String team = doc.getString("team");
            children.add(new Child(id, firstName, lastName, age, team));
        }
        return children;
    }

    public Document findChildById(String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            return childrenCollection.find(eq("_id", objectId)).first();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean updateChildTeam(String firstName, String lastName, String newTeam) {
        Document filter = new Document("firstName", firstName)
                .append("lastName", lastName);

        Document update = new Document("$set", new Document("team", newTeam));

        UpdateResult result = childrenCollection.updateOne(filter, update);
        return result.getModifiedCount() > 0;
    }

    public boolean deleteChild(String firstName, String lastName) {
        Document filter = new Document("firstName", firstName)
                .append("lastName", lastName);

        DeleteResult result = childrenCollection.deleteOne(filter);
        return result.getDeletedCount() > 0;
    }

    public boolean assignChildToTeam(String firstName, String lastName, String team) {
        return updateChildTeam(firstName, lastName, team);
    }

    public static void main(String[] args) {
        ChildService service = new ChildService();

        service.childrenCollection.drop();
        service.insertChildren(ChildData.getSampleChildren());

        service.updateChildTeam("Rio", "Lacey", "U13 Tigers");
        service.assignChildToTeam("Kaylen", "Smith", "Under 13 Tigers");
        service.deleteChild("Amir", "Khan");

        service.getAllChildren().forEach(System.out::println);
    }
}
