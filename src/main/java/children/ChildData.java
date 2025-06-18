package children;

import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class ChildData {
    public static List<Document> getSampleChildren() {
        List<Document> children = new ArrayList<>();

        children.add(new Document("firstName", "Rio")
                .append("lastName", "Lacey")
                .append("age", 12)
                .append("team", "U13 Tigers"));

        children.add(new Document("firstName", "Amir")
                .append("lastName", "Khan")
                .append("age", 12)
                .append("team", "U13 Tigers"));

        children.add(new Document("firstName", "Kaylen")
                .append("lastName", "Smith")
                .append("age", 12)
                .append("team", "U13 Tigers"));

        children.add(new Document("firstName", "Lamar")
                .append("lastName", "Jackson")
                .append("age", 12)
                .append("team", "U13 Tigers"));

        children.add(new Document("firstName", "Lena")
                .append("lastName", "Smith")
                .append("age", 10)
                .append("team", "Under 11 Lions"));

        return children;
    }
}
