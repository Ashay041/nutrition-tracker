package ds.nutrition.servlet;

import com.google.gson.Gson;
import ds.nutrition.api.NutritionixApiClient;
import ds.nutrition.db.LogRepository;
import ds.nutrition.model.LogEntry;
import ds.nutrition.model.NutritionRequest;
import ds.nutrition.model.NutritionResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

/**
 * Servlet for handling nutrition API requests...
 * 
 * This servlet processes incoming HTTP POST requests from mobile clients seeking
 * nutrition information. It deserializes the JSON request, validates and completes
 * any missing fields (like request ID, timestamp, or device model), and forwards
 * the request to the Nutritionix API.
 * 
 * The servlet also handles logging of all requests and responses for analytics
 * purposes, capturing metadata like device models, response times, and calorie counts.
 * Both successful responses and errors are logged to MongoDB.
 * 
 * Note: This servlet is configured via web.xml rather than annotations to avoid
 * mapping conflicts.
 * 
 * @author Ashay Koradia
 */
public class NutritionServlet extends HttpServlet {
    private final NutritionixApiClient apiClient = new NutritionixApiClient();
    private final LogRepository logRepository = new LogRepository();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Read request body
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        
        // Get client IP address
        String ipAddress = request.getRemoteAddr();
        
        // Parse request
        NutritionRequest nutritionRequest = gson.fromJson(buffer.toString(), NutritionRequest.class);
        
        // Generate request ID if not provided
        if (nutritionRequest.getRequestId() == null || nutritionRequest.getRequestId().isEmpty()) {
            nutritionRequest.setRequestId(UUID.randomUUID().toString());
        }
        
        // Set timestamp if not provided
        if (nutritionRequest.getTimestamp() == null || nutritionRequest.getTimestamp().isEmpty()) {
            nutritionRequest.setTimestamp(new Date().toString());
        }
        
        // Set default device model if not provided
        if (nutritionRequest.getDeviceModel() == null || nutritionRequest.getDeviceModel().isEmpty()) {
            nutritionRequest.setDeviceModel("Unknown Device");
        }
        
        Date requestTimestamp = new Date();
        
        try {
            // Call Nutritionix API
            NutritionResponse nutritionResponse = apiClient.getNutritionInfo(nutritionRequest);
            
            Date responseTimestamp = new Date();
            
            // Calculate total calories
            double totalCalories = nutritionResponse.getFoods().stream()
                    .mapToDouble(food -> food.getCalories())
                    .sum();
            
            // Log the request and response
            LogEntry logEntry = new LogEntry(
                    nutritionRequest.getRequestId(),
                    nutritionRequest.getDeviceModel(),
                    nutritionRequest.getQuery(),
                    requestTimestamp,
                    responseTimestamp,
                    nutritionResponse.getApiResponseTime(),
                    nutritionResponse.getFoods().size(),
                    totalCalories,
                    "SUCCESS",
                    ipAddress
            );
            
            logRepository.saveLog(logEntry);
            
            // Send response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(nutritionResponse));
            out.flush();
            
        } catch (Exception e) {
            // Log error
            LogEntry logEntry = new LogEntry(
                    nutritionRequest.getRequestId(),
                    nutritionRequest.getDeviceModel(),
                    nutritionRequest.getQuery(),
                    requestTimestamp,
                    new Date(),
                    0,
                    0,
                    0,
                    "ERROR: " + e.getMessage(),
                    ipAddress
            );
            
            logRepository.saveLog(logEntry);
            
            // Send error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
            out.flush();
        }
    }
}
