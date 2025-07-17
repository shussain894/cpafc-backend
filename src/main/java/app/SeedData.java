package app;

import com.cpafc.model.*;
import com.cpafc.repository.ChildRepository;
import com.cpafc.repository.CoachRepository;
import com.cpafc.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    private final CoachRepository coachRepo;
    private final ChildRepository childRepo;
    private final TeamRepository teamRepo;

    public SeedData(CoachRepository coachRepo, ChildRepository childRepo, TeamRepository teamRepo) {
        this.coachRepo = coachRepo;
        this.childRepo = childRepo;
        this.teamRepo = teamRepo;
    }

    public void run(String... args) {
        coachRepo.deleteAll();
        childRepo.deleteAll();
        teamRepo.deleteAll();

        List<String> teamNames = List.of(
                "U6 Lions", "U6 Tigers", "U7 Lions", "U7 Tigers", "U8 Lions", "U8 Tigers",
                "U9 Lions", "U9 Tigers", "U10 Lions", "U10 Tigers", "U11 Lions", "U11 Tigers",
                "U12 Lions", "U12 Tigers", "U13 Lions", "U13 Tigers", "U14 Lions"
        );

        List<Team> teams = teamNames.stream().map(Team::new).toList();
        teamRepo.saveAll(teams);

        List<Coach> coaches = List.of(
                new Coach("Shah", "Hussain", "shah@cpafc.org", "U13 Tigers", Role.ADMIN),
                new Coach("Alice", "Brown", "alice@cpafc.org", "U6 Lions", Role.COACH),
                new Coach("Maya", "Murray", "maya@cpafc.org", "U8 Tigers", Role.COACH),
                new Coach("James", "Owen", "james@cpafc.org", "U10 Lions", Role.COACH),
                new Coach("Liam", "Clark", "liam@cpafc.org", "U13 Lions", Role.COACH),
                new Coach("Jordan", "Blake", "jordan@cpafc.org", null, Role.ADMIN),
                new Coach("Riley", "Adams", "riley@cpafc.org", null, Role.COACH)
        );
        coachRepo.saveAll(coaches);

        List<Child> children = List.of(
                new Child("Amir", "Smith", 6, "U8 Tigers"),
                new Child("Lena", "Brown", 11, "U13 Tigers"),
                new Child("Kaylen", "Jackson", 6, null),
                new Child("Rio", "Khan", 13, "U6 Lions"),
                new Child("Lamar", "Taylor", 10, "U13 Tigers"),
                new Child("Zara", "Patel", 8, null),
                new Child("Ethan", "Clark", 12, "U13 Tigers"),
                new Child("Noah", "Lewis", 9, null),
                new Child("Sophia", "Hall", 10, "U8 Tigers"),
                new Child("Emily", "Young", 11, "U13 Tigers")
        );
        childRepo.saveAll(children);

        System.out.println("✅ Seeded coaches, children and teams.");
    }
}
