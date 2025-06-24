package children;
import children.ChildData;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

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
            if (team == null) team = "Unassigned";
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

    public boolean updateChildTeamById(String id, String newTeam) {
        try {
            ObjectId objectId = new ObjectId(id);
            Document child = childrenCollection.find(eq("_id", objectId)).first();

            if (child == null) {
                System.out.println("No matching child found with _id: " + id);
                return false;
            }

            String fullName = child.getString("firstName") + " " + child.getString("lastName");
            String oldTeam = child.getString("team");

            UpdateResult updateResult = childrenCollection.updateOne(
                    eq("_id", objectId),
                    set("team", newTeam)
            );

            if (updateResult.getModifiedCount() > 0) {
                System.out.println("Team updated for " + fullName);
                System.out.println(fullName + ": " + oldTeam + " ➝ " + newTeam);
                return true;
            } else {
                System.out.println("No changes made for " + fullName);
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error updating child: " + e.getMessage());
            return false;
        }
    }


    public boolean deleteChildById(String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            DeleteResult result = childrenCollection.deleteOne(eq("_id", objectId));

            if (result.getDeletedCount() > 0) {
                System.out.println("Deleted child with _id: " + id);
                return true;
            } else {
                System.out.println("No child found to delete with _id: " + id);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error deleting child: " + e.getMessage());
            return false;
        }
    }

    public void clearChildren() {
        childrenCollection.deleteMany(new Document());
    }

    public boolean assignChildToTeamById(String childId, String team) {
        return updateChildTeamById(childId, team);
    }

    public static void main(String[] args) {
        ChildService service = new ChildService();

        service.clearChildren();

        service.insertChildren(ChildData.getSampleChildren());

        System.out.println("All children:");
        for (Child child : service.getAllChildren()) {
            System.out.println(child);
        }

        String testId = "68556297dd0f74523dae7e22";

        boolean success = service.assignChildToTeamById(testId, "U13 Tigers");
        System.out.println("Assign success: " + success);

        System.out.println("Coach view for U13 Tigers");
        List<Child> coachChildren = service.getAllChildren();
        for (Child c : coachChildren) {
            System.out.println(c.getFirstName() + " " + c.getLastName() + ", Age: " + c.getAge() + ", Team: " + c.getTeam());
        }

        service.getAllChildren().forEach(System.out::println);
    }
}
