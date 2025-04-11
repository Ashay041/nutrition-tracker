package ds.nutrition.servlet;

import ds.nutrition.db.LogRepository;
import ds.nutrition.model.LogEntry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Servlet for displaying the analytics dashboard.
 * 
 * This servlet handles HTTP GET requests to display the admin dashboard with
 * various analytics metrics. It retrieves data from the LogRepository including
 * top searched foods, device usage statistics, average response times, and
 * calorie information.
 * 
 * The servlet aggregates this data and forwards it to the dashboard JSP for
 * rendering. It formats numerical values (like response times and calorie counts)
 * for better readability in the UI.
 * 
 * This dashboard provides administrators with insights into system usage patterns
 * and performance metrics to help monitor and improve the service.
 * 
 * @author Ashay Koradia
 * AndrewId: akoradia
 */
public class DashboardServlet extends HttpServlet {
    private final LogRepository logRepository = new LogRepository();
    private final DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get analytics data
        List<Map<String, Object>> topSearchedFoods = logRepository.getTopSearchedFoods(10);
        double avgResponseTime = logRepository.getAverageApiResponseTime();
        List<Map<String, Object>> topDeviceModels = logRepository.getTopDeviceModels(5);
        double avgCalories = logRepository.getAverageCaloriesPerRequest();
        List<Map<String, Object>> requestsByDay = logRepository.getRequestsByDay(7);
        
        // Get all logs
        List<LogEntry> logs = logRepository.getAllLogs();
        
        // Set attributes for JSP
        request.setAttribute("topSearchedFoods", topSearchedFoods);
        request.setAttribute("avgResponseTime", df.format(avgResponseTime));
        request.setAttribute("topDeviceModels", topDeviceModels);
        request.setAttribute("avgCalories", df.format(avgCalories));
        request.setAttribute("requestsByDay", requestsByDay);
        request.setAttribute("logs", logs);
        
        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }
}
