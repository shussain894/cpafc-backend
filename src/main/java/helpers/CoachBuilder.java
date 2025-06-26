package helpers;

import org.bson.Document;

public class CoachBuilder {
    public static Document build(String firstName, String lastName, String team) {
        Document coach = new Document("firstName", firstName)
                .append("lastName", lastName);

        if (team != null && !team.trim().isEmpty()) {
            coach.append("team", team);
        }

        return coach;
    }
}
