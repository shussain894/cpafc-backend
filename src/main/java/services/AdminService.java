package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private final MongoCollection<Document> childrenCollection;
    private final MongoCollection<Document> coachesCollection;
    private final MongoCollection<Document> teamsCollection;
    private final MongoCollection<Document> adminsCollection;


    public AdminService(MongoDatabase database) {
        this.childrenCollection = database.getCollection("children");
        this.coachesCollection = database.getCollection("coaches");
        this.teamsCollection = database.getCollection("teams");
        this.adminsCollection = database.getCollection("admins");
    }

    // ========================
    // TEAM METHODS
    // ========================

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

    public boolean deleteTeam(String teamName) {
        DeleteResult result = teamsCollection.deleteOne(eq("name", teamName));
        return result.getDeletedCount() > 0;
    }

    public boolean updateTeamName(String oldName, String newName) {
        UpdateResult result = teamsCollection.updateOne(
                eq("name", oldName),
                new Document("$set", new Document("name", newName))
        );
        return result.getModifiedCount() > 0;
    }

    // ========================
    // PLAYER METHODS
    // ========================

    public List<Document> getAllChildren() {
        return childrenCollection.find().into(new ArrayList<>());
    }

    public void addChild(Document childDoc) {
        childrenCollection.insertOne(childDoc);
    }

    public boolean updateChild(String childId, Document updates) {
        UpdateResult result = childrenCollection.updateOne(
                eq("_id", new org.bson.types.ObjectId(childId)),
                new Document("$set", updates)
        );
        return result.getModifiedCount() > 0;
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

    // ========================
    // COACH METHODS
    // ========================


    public List<Document> getAllCoaches() {
        return coachesCollection.find().into(new ArrayList<>());
    }

    public void addCoach(Document coachDoc) {
        coachesCollection.insertOne(coachDoc);
    }

    public boolean deleteCoach(String coachId) {
        DeleteResult result = coachesCollection.deleteOne(eq("_id", new org.bson.types.ObjectId(coachId)));
        return result.getDeletedCount() > 0;
    }

    public boolean updateCoach(String coachId, Document updates) {
        UpdateResult result = coachesCollection.updateOne(eq("_id", new org.bson.types.ObjectId(coachId)),
                new Document("$set", updates));
        return result.getModifiedCount() > 0;
    }

    // ========================
    // ADMIN ACCESS CONTROL
    // ========================

    public void grantAdminAccess(String emailOrId) {
        Document existingAdmin = adminsCollection.find(
                or(
                        eq("_id", isValidObjectId(emailOrId) ? new ObjectId(emailOrId) : null),
                        eq("email", emailOrId)
                )
        ).first();

        if (existingAdmin == null) {
            Document newAdmin = new Document();
            if (isValidObjectId(emailOrId)) {
                newAdmin.append("_id", new ObjectId(emailOrId));
            } else {
                newAdmin.append("email", emailOrId);
            }
            adminsCollection.insertOne(newAdmin);
        }
    }

    public void revokeAdminAccess(String emailOrId) {
        if (isValidObjectId(emailOrId)) {
            adminsCollection.deleteOne(eq("_id", new ObjectId(emailOrId)));
        } else {
            adminsCollection.deleteOne(eq("email", emailOrId));
        }
    }

    private boolean isValidObjectId(String id) {
        if (id == null) return false;
        try {
            new ObjectId(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
