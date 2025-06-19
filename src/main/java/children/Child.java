package children;

public class Child {
    private String firstName;
    private String lastName;
    private int age;
    private String team;

    public Child(String firstName, String lastName, int age, String team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.team = team;
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
        return firstName + " " + lastName + ", Age: " + age + ", Team: " + team;
    }
}