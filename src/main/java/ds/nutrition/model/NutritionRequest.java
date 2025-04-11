package ds.nutrition.model;

/**
 * Represents a nutrition request to the Nutritionix API.
 * 
 * This class encapsulates the data sent from mobile clients to our nutrition service.
 * It contains the food query text, device information, and request metadata like
 * timestamps and IDs for tracking purposes.
 * 
 * The server deserializes JSON from client requests into this object before processing.
 * If certain fields like requestId or timestamp are missing, they'll be auto-generated
 * by the server.
 * 
 * @author Ashay Koradia
 */
public class NutritionRequest {
    private String query;
    private String deviceModel;
    private String timestamp;
    private String requestId;

    public NutritionRequest() {
    }

    public NutritionRequest(String query, String deviceModel, String timestamp, String requestId) {
        this.query = query;
        this.deviceModel = deviceModel;
        this.timestamp = timestamp;
        this.requestId = requestId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
