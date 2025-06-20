package children;

import org.bson.types.ObjectId;

public class Child {
    private ObjectId id;
    private String firstName;
    private String lastName;
    private int age;
    private String team;

    public Child(ObjectId id, String firstName, String lastName, int age, String team) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.team = team;
    }

    public ObjectId getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", Age: " + age + ", Team: " + team + ", ID: " + id.toHexString();
    }
}