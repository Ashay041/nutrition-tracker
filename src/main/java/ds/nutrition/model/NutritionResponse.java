package ds.nutrition.model;

import java.util.List;

/**
 * Represents a nutrition response from the Nutritionix API.
 * 
 * This class encapsulates the nutritional data returned from the Nutritionix API.
 * It contains a list of Food objects with detailed nutrition information, along with
 * metadata about the request such as request ID, timestamp, device model, and API
 * response time for performance tracking.
 * 
 * The toString() method provides a formatted summary of all foods in the response,
 * including a calculation of total nutritional intake across all food items.
 * 
 * @author Ashay Koradia
 */
public class NutritionResponse {
    private List<Food> foods;
    private String requestId;
    private String timestamp;
    private String deviceModel;
    private long apiResponseTime;

    public NutritionResponse() {
    }

    public NutritionResponse(List<Food> foods, String requestId, String timestamp, 
                            String deviceModel, long apiResponseTime) {
        this.foods = foods;
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.deviceModel = deviceModel;
        this.apiResponseTime = apiResponseTime;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public long getApiResponseTime() {
        return apiResponseTime;
    }

    public void setApiResponseTime(long apiResponseTime) {
        this.apiResponseTime = apiResponseTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        double totalCalories = 0;
        double totalFat = 0;
        double totalCarbs = 0;
        double totalProtein = 0;

        for (Food food : foods) {
            sb.append("Food: ").append(food.getFoodName()).append("\n")
                    .append("  Calories: ").append(food.getCalories()).append("\n")
                    .append("  Total Fat: ").append(food.getTotalFat()).append("\n")
                    .append("  Carbohydrates: ").append(food.getTotalCarbohydrate()).append("\n")
                    .append("  Protein: ").append(food.getProtein()).append("\n")
                    .append("--------------------------\n");

            totalCalories += food.getCalories();
            totalFat += food.getTotalFat();
            totalCarbs += food.getTotalCarbohydrate();
            totalProtein += food.getProtein();
        }

        sb.append("TOTAL INTAKE:\n")
                .append("  Calories: ").append(totalCalories).append("\n")
                .append("  Total Fat: ").append(totalFat).append("\n")
                .append("  Carbohydrates: ").append(totalCarbs).append("\n")
                .append("  Protein: ").append(totalProtein);

        return sb.toString();
    }
}
