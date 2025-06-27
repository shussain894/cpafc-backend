package app;

import coaches.CoachService;
import services.AdminService;
import children.Child;
import helpers.ChildBuilder;
import helpers.CoachBuilder;
import helpers.TeamBuilder;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

public class AdminManualTester {
    public static void main(String[] args) {

        MongoDatabase database = MongoConnection.getDatabase();
        MongoCollection<Document> childrenCollection = database.getCollection("children");
        MongoCollection<Document> coachesCollection = database.getCollection("coaches");

        SeedData.populate(database);

        printAllChildren(childrenCollection);
        printAllCoaches(coachesCollection);

        CoachService coachService = new CoachService(database);
        boolean success = coachService.registerCoach("Shah", "Hussain", "U13 Tigers");

        System.out.println(success
                ? "Coach registration successful!"
                : "Coach registration failed.");

        System.out.println("\nChildren for Coach Shah Hussain:");
        for (Child child : coachService.getChildrenForCoach("Shah", "Hussain")) {
            System.out.println(child.getFirstName() + " " + child.getLastName()
                    + ", Age: " + child.getAge() + ", Team: " + child.getTeam());
        }

        String coachFirstName = "Shah";
        String coachLastName = "Hussain";
        String childIdToMove = "";

        for (Document doc : childrenCollection.find(eq("team", "U13 Tigers"))) {
            childIdToMove = doc.getObjectId("_id").toHexString();
            break;
        }

        if (!childIdToMove.isEmpty()) {
            boolean moveSuccess = coachService.updateChildTeamByCoach(coachFirstName, coachLastName, childIdToMove, "U13 Lions");

            System.out.println(moveSuccess
                    ? "Child transfer successful."
                    : "Child transfer failed.");
        } else {
            System.out.println("No child found in U13 Tigers to test transfer.");
        }

        AdminService adminService = new AdminService(database);

        System.out.println("\nAdmin view of all children:");
        adminService.getAllChildren().forEach(System.out::println);

        adminService.addTeam("U14 Wolves");

        boolean AssignSuccess = adminService.assignChildToTeam("665a92c79c1fa42801759403", "U14 Wolves");
        System.out.println(AssignSuccess ? "Child assigned to team." : "Assignment failed.");

        // === TEAM TEST ===
        adminService.addTeam("Test Team");
        System.out.println("Team added.");

        boolean teamUpdated = adminService.updateTeamName("Test Team", "Updated Team");
        System.out.println("Team updated: " + teamUpdated);

        boolean teamDeleted = adminService.deleteTeam("Updated Team");
        System.out.println("Team deleted: " + teamDeleted);

        // === CHILD TEST ===
        Document child = helpers.ChildBuilder.build("Jamie", "Tyson", 8, null);
        adminService.addChild(child);
        System.out.println("Child added.");

        String childId = adminService.getAllChildren().get(0).getObjectId("_id").toHexString();

        boolean childUpdated = adminService.updateChild(childId, new Document("age", 9));
        System.out.println("Child updated: " + childUpdated);

        boolean childDeleted = adminService.deleteChild(childId);
        System.out.println("Child deleted: " + childDeleted);

        // === COACH TEST ===
        Document coach = helpers.CoachBuilder.build("Michael", "Boola", "None");
        adminService.addCoach(coach);
        System.out.println("Coach added.");

        String coachId = adminService.getAllCoaches().get(0).getObjectId("_id").toHexString();

        boolean coachUpdated = adminService.updateCoach(coachId, new Document("team", "Updated Team"));
        System.out.println("Coach updated: " + coachUpdated);

        boolean coachDeleted = adminService.deleteCoach(coachId);
        System.out.println("Coach deleted: " + coachDeleted);

        // === ADMIN ACCESS TEST ===
        String email = "admin@example.com";
        adminService.grantAdminAccess(email);
        System.out.println("Admin access granted.");

        adminService.revokeAdminAccess(email);
        System.out.println("Admin access revoked.");
    }

    private static void printAllChildren(MongoCollection<Document> childrenCollection) {
        System.out.println("\nAll Children:");
        for (Document child : childrenCollection.find()) {
            String id = child.getObjectId("_id").toHexString();
            String name = child.getString("firstName") + " " + child.getString("lastName");
            int age = child.getInteger("age");
            String team = child.getString("team") != null ? child.getString("team") : "Unassigned";
            System.out.println(name + " (Age: " + age + ", Team: " + team + ", ID: " + id + ")");
        }
    }

    private static void printAllCoaches(MongoCollection<Document> coachesCollection) {
        System.out.println("\nAll Coaches:");
        for (Document coach : coachesCollection.find()) {
            String name = coach.getString("firstName") + " " + coach.getString("lastName");
            String team = coach.getString("team");
            System.out.println(name + " (Assigned to: " + team + ")");
        }
    }
}
