package com.kueshi.api_my_finances.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@EnableMongoAuditing
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Value("${MONGO_URI:mongodb://localhost:27017/my_finances}")
    private String mongoUri;

    @Value("${MONGO_USERNAME:admin}")
    private String username;

    @Value("${MONGO_PASSWORD:admin}")
    private String password;

    @Primary
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        ConnectionString connectionString = new ConnectionString(mongoUri);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .credential(com.mongodb.MongoCredential.createCredential(username, "admin", password.toCharArray()))
                .build();

        return new SimpleMongoClientDatabaseFactory(MongoClients.create(settings), "my_finances");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDatabaseFactory());
    }

    @Override
    protected String getDatabaseName() {
        return "my_finances";
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
