package com.cpafc.init;

import app.SeedData;
import com.cpafc.repository.ChildRepository;
import com.cpafc.repository.CoachRepository;
import com.cpafc.repository.TeamRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SeedDataRunner {

    private final CoachRepository coachRepo;
    private final ChildRepository childRepo;
    private final TeamRepository teamRepo;

    public SeedDataRunner(CoachRepository coachRepo, ChildRepository childRepo, TeamRepository teamRepo) {
        this.coachRepo = coachRepo;
        this.childRepo = childRepo;
        this.teamRepo = teamRepo;
    }

    @PostConstruct
    public void init() {
        SeedData seedData = new SeedData(coachRepo, childRepo, teamRepo);
        seedData.run();
        System.out.println("✅ Database seeded via SeedDataRunner.");
    }
}