package com.cpafc.controller;

import com.cpafc.model.Team;
import com.cpafc.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Team> getTeamById(@PathVariable String id) {
        return teamRepository.findById(id);
    }

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamRepository.save(team);
    }

    @PutMapping("/{id}")
    public Team updateTeam(@PathVariable String id, @RequestBody Team updatedTeam) {
        return teamRepository.findById(id).map(team -> {
            team.setName(updatedTeam.getName());
            return teamRepository.save(team);
        }).orElseGet(() -> {
            updatedTeam.setId(id);
            return teamRepository.save(updatedTeam);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable String id) {
        teamRepository.deleteById(id);
    }
}
