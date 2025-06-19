package teams;

public class Team {
    private String ageGroup;
    private String name;

    public Team(String ageGroup, String name) {
        this.ageGroup = ageGroup;
        this.name = name;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return ageGroup + " " + name;
    }
}
