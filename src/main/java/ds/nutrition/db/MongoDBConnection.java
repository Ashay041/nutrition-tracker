package ds.nutrition.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Singleton class to manage MongoDB connection.
 * 
 * This class implements the Singleton pattern to ensure we maintain only one
 * connection to MongoDB throughout the application's lifecycle. It handles the
 * connection setup to MongoDB Atlas, including authentication and server API
 * configuration.
 * 
 * The class provides access to the MongoDB database instance and includes a
 * method to properly close the connection when needed.
 * 
 * Note: In production, credentials should be stored in environment variables
 * or a secure configuration system, not hardcoded.
 * 
 * @author Ashay Koradia
 * AndrewId: akoradia
 */
public class MongoDBConnection {
    private static MongoDBConnection instance;
    private MongoClient mongoClient;
    private MongoDatabase database;
    
    // MongoDB Atlas connection details
    private static final String DATABASE_NAME = "nutrition-app-db";
    
    private MongoDBConnection() {
        String connectionString = "mongodb://akoradia:ashay1234@cluster0-shard-00-00.ej44t.mongodb.net:27017,cluster0-shard-00-01.ej44t.mongodb.net:27017,cluster0-shard-00-02.ej44t.mongodb.net:27017/nutrition-app-db?w=majority&retryWrites=true&tls=true&authMechanism=SCRAM-SHA-1";
        // Configure MongoDB client settings
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        
        // Create MongoDB client and get database
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(DATABASE_NAME);
    }
    
    public static synchronized MongoDBConnection getInstance() {
        if (instance == null) {
            instance = new MongoDBConnection();
        }
        return instance;
    }
    
    public MongoDatabase getDatabase() {
        return database;
    }
    
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
