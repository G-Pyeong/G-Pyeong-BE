package com.gpyeong.core.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MongoDbInitializer implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing MongoDB collections...");
        
        // Create collections
        createCollectionIfNotExists("years");
        createCollectionIfNotExists("members");
        createCollectionIfNotExists("universities");
        createCollectionIfNotExists("departments");
        createCollectionIfNotExists("subjects");
        createCollectionIfNotExists("subject_categories");
        createCollectionIfNotExists("timetables");
        createCollectionIfNotExists("timetable_items");
        createCollectionIfNotExists("graduation_requirements");
        createCollectionIfNotExists("required_subjects");
        
        log.info("MongoDB initialization completed!");
    }

    private void createCollectionIfNotExists(String collectionName) {
        try {
            if (!mongoTemplate.collectionExists(collectionName)) {
                mongoTemplate.createCollection(collectionName);
                log.info("Created collection: {}", collectionName);
            } else {
                log.info("Collection already exists: {}", collectionName);
            }
        } catch (Exception e) {
            log.warn("Error creating collection {}: {}", collectionName, e.getMessage());
        }
    }
}
