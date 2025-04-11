package ds.nutrition.model;

import org.bson.Document;
import java.util.Date;

/**
 * Represents a log entry to be stored in MongoDB.
 * 
 * This class tracks all API requests and responses in our system, storing details like
 * request/response timestamps, device information, query details, and performance metrics.
 * It serves as the foundation for our analytics dashboard, allowing us to monitor system
 * usage and performance.
 * 
 * The class includes methods to convert between MongoDB Document objects and LogEntry
 * instances, making database operations more convenient.
 * 
 * @author Ashay Koradia
 * AndrewId: akoradia
 */
public class LogEntry {
    private String id;
    private String requestId;
    private String deviceModel;
    private String query;
    private Date requestTimestamp;
    private Date responseTimestamp;
    private long apiResponseTime;
    private int foodItemsCount;
    private double totalCalories;
    private String responseStatus;
    private String ipAddress;

    public LogEntry() {
    }

    public LogEntry(String requestId, String deviceModel, String query, 
                   Date requestTimestamp, Date responseTimestamp, long apiResponseTime,
                   int foodItemsCount, double totalCalories, String responseStatus, String ipAddress) {
        this.requestId = requestId;
        this.deviceModel = deviceModel;
        this.query = query;
        this.requestTimestamp = requestTimestamp;
        this.responseTimestamp = responseTimestamp;
        this.apiResponseTime = apiResponseTime;
        this.foodItemsCount = foodItemsCount;
        this.totalCalories = totalCalories;
        this.responseStatus = responseStatus;
        this.ipAddress = ipAddress;
    }

    // Convert to MongoDB Document
    public Document toDocument() {
        return new Document("requestId", requestId)
                .append("deviceModel", deviceModel)
                .append("query", query)
                .append("requestTimestamp", requestTimestamp)
                .append("responseTimestamp", responseTimestamp)
                .append("apiResponseTime", apiResponseTime)
                .append("foodItemsCount", foodItemsCount)
                .append("totalCalories", totalCalories)
                .append("responseStatus", responseStatus)
                .append("ipAddress", ipAddress);
    }

    // Create from MongoDB Document
    public static LogEntry fromDocument(Document doc) {
        LogEntry entry = new LogEntry();
        entry.id = doc.getObjectId("_id").toString();
        entry.requestId = doc.getString("requestId");
        entry.deviceModel = doc.getString("deviceModel");
        entry.query = doc.getString("query");
        entry.requestTimestamp = doc.getDate("requestTimestamp");
        entry.responseTimestamp = doc.getDate("responseTimestamp");
        entry.apiResponseTime = doc.getLong("apiResponseTime");
        entry.foodItemsCount = doc.getInteger("foodItemsCount");
        entry.totalCalories = doc.getDouble("totalCalories");
        entry.responseStatus = doc.getString("responseStatus");
        entry.ipAddress = doc.getString("ipAddress");
        return entry;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(Date requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public Date getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(Date responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public long getApiResponseTime() {
        return apiResponseTime;
    }

    public void setApiResponseTime(long apiResponseTime) {
        this.apiResponseTime = apiResponseTime;
    }

    public int getFoodItemsCount() {
        return foodItemsCount;
    }

    public void setFoodItemsCount(int foodItemsCount) {
        this.foodItemsCount = foodItemsCount;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
