package helpers;

import org.bson.Document;

public class TeamBuilder {
    public static Document build(String teamName) {
        return new Document("teamName", teamName);
    }
}
