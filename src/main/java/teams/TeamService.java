package teams;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class TeamService {

    private MongoCollection<Document> teamsCollection;

    public TeamService() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("cpafc-test");

        teamsCollection = database.getCollection("teams");
    }

    public void insertTeams(List<Teams> teams) {
        for (Teams team : teams) {
            Document doc = new Document("name", team.getName())
                    .append("ageGroup", team.getAgeGroup());
            teamsCollection.insertOne(doc);
            System.out.println("Inserted team: " + team.getName());
        }
    }

    public List<Teams> getAllTeams() {
        List<Teams> teams = new ArrayList<>();

        for (Document doc : teamsCollection.find()) {
            String name = doc.getString("name");
            String ageGroup = doc.getString("ageGroup");

            Teams team = new Teams(name, ageGroup);
            teams.add(team);
        }

        return teams;
    }

    public static void main(String[] args) {
        TeamService service = new TeamService();

        List<Teams> teams = java.util.Arrays.asList(
                new Teams("Lions", "U6"),
                new Teams("Tigers", "U6"),
                new Teams("Lions", "U7"),
                new Teams("Tigers", "U7")
        );

        service.insertTeams(teams);

        List<Teams> allTeams = service.getAllTeams();
        for (Teams team : allTeams) {
            System.out.println("Age Group: " + team.getAgeGroup() + " " + team.getName());
        }
    }

}