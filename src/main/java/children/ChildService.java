package children;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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

    public void insertChild(Child child) {
        Document doc = new Document("firstName", child.getFirstName())
                .append("lastName", child.getLastName())
                .append("age", child.getAge())
                .append("team", child.getTeam());
        childrenCollection.insertOne(doc);
        System.out.println("Inserted: " + child);
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

        Child rio = new Child("Rio", "Lacey", 12, "U13 Lions");
        service.insertChild(rio);

        List<Child> allChildren = service.getAllChildren();
        for (Child child : allChildren) {
            System.out.println("child added " + child);
        }
    }

}