package helpers;

import org.bson.Document;

public class ChildBuilder {
    public static Document build(String firstName, String lastName, int age, String team) {
        Document child = new Document("firstName", firstName)
                .append("lastName", lastName)
                .append("age", age);

        if (team != null && !team.trim().isEmpty()) {
            child.append("team", team);
        }

        return child;
    }
}
