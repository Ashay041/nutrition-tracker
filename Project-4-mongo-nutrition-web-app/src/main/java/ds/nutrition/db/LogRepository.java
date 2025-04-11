package ds.nutrition.db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import ds.nutrition.model.LogEntry;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

/**
 * Repository for logging data to MongoDB.
 * 
 * This class handles all database operations related to logging and analytics.
 * It provides methods to save log entries and retrieve various analytics data
 * such as top searched foods, device usage statistics, and performance metrics.
 * 
 * The repository uses MongoDB's aggregation framework to perform complex
 * data analysis operations directly in the database, improving performance
 * for dashboard displays and reports.
 * 
 * @author Ashay Koradia
 */
public class LogRepository {
    private static final String COLLECTION_NAME = "logs";
    private final MongoCollection<Document> collection;

    public LogRepository() {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        this.collection = database.getCollection(COLLECTION_NAME);
    }

    /**
     * Save a log entry to MongoDB
     * @param logEntry The log entry to save
     * @return The ID of the saved document
     */
    public String saveLog(LogEntry logEntry) {
        Document document = logEntry.toDocument();
        collection.insertOne(document);
        return document.getObjectId("_id").toString();
    }

    /**
     * Get all log entries
     * @return List of all log entries
     */
    public List<LogEntry> getAllLogs() {
        List<LogEntry> logs = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().sort(Sorts.descending("requestTimestamp")).iterator()) {
            while (cursor.hasNext()) {
                logs.add(LogEntry.fromDocument(cursor.next()));
            }
        }
        return logs;
    }

    /**
     * Get top N searched food items
     * @param limit Number of items to return
     * @return Map of food query to count
     */
    public List<Map<String, Object>> getTopSearchedFoods(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        
        List<Bson> pipeline = Arrays.asList(
            Aggregates.group("$query", Accumulators.sum("count", 1)),
            Aggregates.sort(Sorts.descending("count")),
            Aggregates.limit(limit)
        );
        
        try (MongoCursor<Document> cursor = collection.aggregate(pipeline).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Map<String, Object> item = new HashMap<>();
                item.put("query", doc.getString("_id"));
                item.put("count", doc.getInteger("count"));
                result.add(item);
            }
        }
        
        return result;
    }

    /**
     * Get average API response time
     * @return Average response time in milliseconds
     */
    public double getAverageApiResponseTime() {
        List<Bson> pipeline = Arrays.asList(
            Aggregates.group(null, Accumulators.avg("avgResponseTime", "$apiResponseTime"))
        );
        
        Document result = collection.aggregate(pipeline).first();
        return result != null ? result.getDouble("avgResponseTime") : 0;
    }

    /**
     * Get top device models making requests
     * @param limit Number of items to return
     * @return Map of device model to count
     */
    public List<Map<String, Object>> getTopDeviceModels(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        
        List<Bson> pipeline = Arrays.asList(
            Aggregates.group("$deviceModel", Accumulators.sum("count", 1)),
            Aggregates.sort(Sorts.descending("count")),
            Aggregates.limit(limit)
        );
        
        try (MongoCursor<Document> cursor = collection.aggregate(pipeline).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Map<String, Object> item = new HashMap<>();
                item.put("deviceModel", doc.getString("_id"));
                item.put("count", doc.getInteger("count"));
                result.add(item);
            }
        }
        
        return result;
    }

    /**
     * Get average calories per request
     * @return Average calories
     */
    public double getAverageCaloriesPerRequest() {
        List<Bson> pipeline = Arrays.asList(
            Aggregates.group(null, Accumulators.avg("avgCalories", "$totalCalories"))
        );
        
        Document result = collection.aggregate(pipeline).first();
        return result != null ? result.getDouble("avgCalories") : 0;
    }

    /**
     * Get requests count by day
     * @param days Number of days to look back
     * @return Map of date to count
     */
    public List<Map<String, Object>> getRequestsByDay(int days) {
        List<Map<String, Object>> result = new ArrayList<>();
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        Date startDate = cal.getTime();
        
        Bson dateFilter = Filters.gte("requestTimestamp", startDate);
        
        List<Bson> pipeline = Arrays.asList(
            Aggregates.match(dateFilter),
            Aggregates.group(new Document("year", new Document("$year", "$requestTimestamp"))
                    .append("month", new Document("$month", "$requestTimestamp"))
                    .append("day", new Document("$dayOfMonth", "$requestTimestamp")),
                    Accumulators.sum("count", 1)),
            Aggregates.sort(Sorts.ascending("_id.year", "_id.month", "_id.day"))
        );
        
        try (MongoCursor<Document> cursor = collection.aggregate(pipeline).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Document id = (Document) doc.get("_id");
                
                Map<String, Object> item = new HashMap<>();
                String dateStr = id.getInteger("year") + "-" + 
                                 id.getInteger("month") + "-" + 
                                 id.getInteger("day");
                item.put("date", dateStr);
                item.put("count", doc.getInteger("count"));
                result.add(item);
            }
        }
        
        return result;
    }
}
