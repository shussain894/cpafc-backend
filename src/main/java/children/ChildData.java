package children;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class ChildData {
    public static List<Document> getSampleChildren() {
        List<Document> children = new ArrayList<>();

        children.add(new Document("_id", new ObjectId("68556297dd0f74523dae7e22"))
                .append("firstName", "Rio")
                .append("lastName", "Lacey")
                .append("age", 12));

        children.add(new Document("firstName", "Amir")
                .append("lastName", "Khan")
                .append("age", 12));

        children.add(new Document("firstName", "Kaylen")
                .append("lastName", "Smith")
                .append("age", 12));

        children.add(new Document("firstName", "Lamar")
                .append("lastName", "Jackson")
                .append("age", 12));

        children.add(new Document("firstName", "Lena")
                .append("lastName", "Smith")
                .append("age", 10));

        return children;
    }
}
