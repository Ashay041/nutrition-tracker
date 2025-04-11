package ds.nutrition.model;

/**
 * Food model class representing nutrition information for a food item.
 * 
 * This class stores nutritional data for a single food item, including calories,
 * fats, carbohydrates, and protein content. It also maintains metadata like timestamp,
 * device model, and request ID to track where and when the data was requested.
 * 
 * Used primarily for deserializing API responses from Nutritionix and for displaying
 * nutrition information in the dashboard.
 * 
 * @author Ashay Koradia
 * AndrewId: akoradia
 */
public class Food {
    private String foodName;
    private double calories;
    private double totalFat;
    private double totalCarbohydrate;
    private double protein;
    private String timestamp;
    private String deviceModel;
    private String requestId;

    // Default constructor
    public Food() {
    }

    // Constructor with all fields
    public Food(String foodName, double calories, double totalFat, 
                double totalCarbohydrate, double protein, String timestamp, 
                String deviceModel, String requestId) {
        this.foodName = foodName;
        this.calories = calories;
        this.totalFat = totalFat;
        this.totalCarbohydrate = totalCarbohydrate;
        this.protein = protein;
        this.timestamp = timestamp;
        this.deviceModel = deviceModel;
        this.requestId = requestId;
    }

    // Getters and Setters
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public double getTotalCarbohydrate() {
        return totalCarbohydrate;
    }

    public void setTotalCarbohydrate(double totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ", calories=" + calories +
                ", totalFat=" + totalFat +
                ", totalCarbohydrate=" + totalCarbohydrate +
                ", protein=" + protein +
                ", timestamp='" + timestamp + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
