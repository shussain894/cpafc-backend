package coaches;

import children.Child;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class CoachService {
    private final MongoCollection<Document> coachCollection;
    private final MongoCollection<Document> childrenCollection;

    public CoachService(MongoDatabase database) {
        this.coachCollection = database.getCollection("coaches");
        this.childrenCollection = database.getCollection("children");
    }

    public boolean registerCoach(String firstName, String lastName, String team) {
        Document coach = new Document("firstName", firstName)
                .append("lastName", lastName)
                .append("team", team);
        coachCollection.insertOne(coach);
        System.out.println("Coach registered: " + firstName + " " + lastName + " for team " + team);
        return true;
    }

    public List<Child> getChildrenForCoach(String coachFirstName, String coachLastName) {
        Document coach = coachCollection.find(and(
                eq("firstName", coachFirstName),
                eq("lastName", coachLastName)
        )).first();

        if (coach == null) {
            System.out.println("Coach not found.");
            return new ArrayList<>();
        }

        String assignedTeam = coach.getString("team");
        List<Child> children = new ArrayList<>();
        for (Document doc : childrenCollection.find(eq("team", assignedTeam))) {
            ObjectId id = doc.getObjectId("_id");
            String firstName = doc.getString("firstName");
            String lastName = doc.getString("lastName");
            int age = doc.getInteger("age");
            String team = doc.getString("team");
            children.add(new Child(id, firstName, lastName, age, team));
        }

        return children;
    }

    public boolean updateChildTeamByCoach(String coachFirstName, String coachLastName, String childId, String newTeam) {
        Document coach = coachCollection.find(and(
                eq("firstName", coachFirstName),
                eq("lastName", coachLastName)
        )).first();

        if (coach == null) {
            System.out.println("❌ Coach not found.");
            return false;
        }

        String coachTeam = coach.getString("team");

        Document child = childrenCollection.find(eq("_id", new ObjectId(childId))).first();

        if (child == null) {
            System.out.println("❌ Child not found.");
            return false;
        }

        String childTeam = child.getString("team");
        if (childTeam == null || !childTeam.equals(coachTeam)) {
            System.out.println("❌ Coach cannot update this child. They are not in your team.");
            return false;
        }

        Document update = new Document("$set", new Document("team", newTeam));
        childrenCollection.updateOne(eq("_id", new ObjectId(childId)), update);
        System.out.println("✅ Child moved from " + childTeam + " ➝ " + newTeam);
        return true;
    }

}
