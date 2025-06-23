package app;

import coaches.CoachService;
import children.Child;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {
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
                ? "\nCoach registration successful!"
                : "\nCoach registration failed.");

        System.out.println("\nChildren for Coach Shah Hussain:");
        for (Child child : coachService.getChildrenForCoach("Shah", "Hussain")) {
            System.out.println(child.getFirstName() + " " + child.getLastName()
                    + ", Age: " + child.getAge() + ", Team: " + child.getTeam());
        }
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
