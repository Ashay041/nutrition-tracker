package ds.nutrition.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ds.nutrition.model.Food;
import ds.nutrition.model.NutritionRequest;
import ds.nutrition.model.NutritionResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Client for communicating with the Nutritionix API.
 * 
 * This class handles HTTP communication with the Nutritionix API to retrieve
 * detailed nutrition information for food items. It converts natural language
 * food queries into structured nutrition data.
 * 
 * The client measures API response time for performance monitoring and transforms
 * the JSON response into our application's domain model objects. It maintains the
 * request metadata (device model, timestamps, etc.) throughout the process.
 * 
 * Note: In production, API keys should be stored in environment variables or a
 * secure configuration system, not hardcoded.
 * 
 * @author Ashay Koradia
 */
public class NutritionixApiClient {
    private static final String API_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";
    private static final String APP_ID = "443d2308"; // Nutritionix API app ID
    private static final String API_KEY = "7e4d03afa5672515d7673a0598bdbec8"; // Nutritionix API key
    
    /**
     * Get nutrition information for a food query
     * @param request The nutrition request
     * @return The nutrition response
     * @throws IOException If there is an error communicating with the API
     */
    public NutritionResponse getNutritionInfo(NutritionRequest request) throws IOException {
        long startTime = System.currentTimeMillis();
        
        try {
            // Create connection using URI (non-deprecated method)
            URI uri = new URI(API_URL);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("x-app-id", APP_ID);
            connection.setRequestProperty("x-app-key", API_KEY);
            connection.setDoOutput(true);
            
            // Create request JSON
            JsonObject requestJson = new JsonObject();
            requestJson.addProperty("query", request.getQuery());
            
            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestJson.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            // Read response
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            
            // Parse response
            String responseStr = response.toString();
            JsonObject jsonResponse = JsonParser.parseString(responseStr).getAsJsonObject();
            List<Food> foods = new ArrayList<>();
            
            if (jsonResponse.has("foods")) {
                jsonResponse.getAsJsonArray("foods").forEach(element -> {
                    JsonObject foodJson = element.getAsJsonObject();
                    Food food = new Food();
                    food.setFoodName(foodJson.get("food_name").getAsString());
                    food.setCalories(foodJson.get("nf_calories").getAsDouble());
                    food.setTotalFat(foodJson.get("nf_total_fat").getAsDouble());
                    food.setTotalCarbohydrate(foodJson.get("nf_total_carbohydrate").getAsDouble());
                    food.setProtein(foodJson.get("nf_protein").getAsDouble());
                    food.setTimestamp(request.getTimestamp());
                    food.setDeviceModel(request.getDeviceModel());
                    food.setRequestId(request.getRequestId());
                    foods.add(food);
                });
            }
            
            // Create response object
            NutritionResponse nutritionResponse = new NutritionResponse();
            nutritionResponse.setFoods(foods);
            nutritionResponse.setRequestId(request.getRequestId());
            nutritionResponse.setTimestamp(request.getTimestamp());
            nutritionResponse.setDeviceModel(request.getDeviceModel());
            nutritionResponse.setApiResponseTime(responseTime);
            
            return nutritionResponse;
        } catch (URISyntaxException e) {
            throw new IOException("Invalid API URL: " + e.getMessage(), e);
        }
    }
}
