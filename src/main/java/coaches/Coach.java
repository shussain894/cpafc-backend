package coaches;

public class Coach {
    private String firstName;
    private String lastName;
    private String team;

    public Coach(String firstName, String lastName, String team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getTeam() { return team; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - Team: " + team;
    }
}
