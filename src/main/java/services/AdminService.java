package services;

import children.Child;
import coaches.Coach;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private final MongoCollection<Document> childrenCollection;
    private final MongoCollection<Document> coachesCollection;
    private final MongoCollection<Document> teamsCollection;

    public AdminService(MongoDatabase database) {
        this.childrenCollection = database.getCollection("children");
        this.coachesCollection = database.getCollection("coaches");
        this.teamsCollection = database.getCollection("teams");
    }

    public List<Document> getAllChildren() {
        return childrenCollection.find().into(new ArrayList<>());
    }

    public List<Document> getAllCoaches() {
        return coachesCollection.find().into(new ArrayList<>());
    }

    public List<String> getAllTeams() {
        List<String> teams = new ArrayList<>();
        for (Document doc : teamsCollection.find()) {
            teams.add(doc.getString("name"));
        }
        return teams;
    }

    public void addTeam(String teamName) {
        teamsCollection.insertOne(new Document("name", teamName));
    }

    public boolean assignChildToTeam(String childId, String teamName) {
        UpdateResult result = childrenCollection.updateOne(eq("_id", new org.bson.types.ObjectId(childId)),
                new Document("$set", new Document("team", teamName)));
        return result.getModifiedCount() > 0;
    }

    public boolean deleteChild(String childId) {
        DeleteResult result = childrenCollection.deleteOne(eq("_id", new org.bson.types.ObjectId(childId)));
        return result.getDeletedCount() > 0;
    }

    // Add other admin methods like create/update/delete coach as needed
}
