package com.cpafc.init;

import app.MongoConnection;
import app.SeedData;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SeedDataRunner {

    @PostConstruct
    public void init() {
        MongoDatabase db = MongoConnection.getDatabase();
        SeedData.populate(db);
        System.out.println("✅ Database seeded via SeedDataRunner.");
    }
}
