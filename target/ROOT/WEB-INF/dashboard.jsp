<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nutrition Tracker Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 20px;
            background-color: #f8f9fa;
        }
        .card {
            margin-bottom: 20px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        .card-header {
            background-color: #6c757d;
            color: white;
            font-weight: bold;
        }
        .table-responsive {
            max-height: 400px;
            overflow-y: auto;
        }
        h1 {
            color: #343a40;
            margin-bottom: 30px;
        }
        .analytics-card {
            height: 100%;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center">Nutrition Tracker Dashboard</h1>
        
        <div class="row mb-4">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        Operations Analytics
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <!-- Top Searched Foods -->
                            <div class="col-md-4 mb-3">
                                <div class="card analytics-card">
                                    <div class="card-header">Top Searched Foods</div>
                                    <div class="card-body">
                                        <table class="table table-striped table-sm">
                                            <thead>
                                                <tr>
                                                    <th>Food Query</th>
                                                    <th>Count</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${topSearchedFoods}" var="food">
                                                    <tr>
                                                        <td>${food.query}</td>
                                                        <td>${food.count}</td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${empty topSearchedFoods}">
                                                    <tr>
                                                        <td colspan="2" class="text-center">No data available</td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Top Device Models -->
                            <div class="col-md-4 mb-3">
                                <div class="card analytics-card">
                                    <div class="card-header">Top Device Models</div>
                                    <div class="card-body">
                                        <table class="table table-striped table-sm">
                                            <thead>
                                                <tr>
                                                    <th>Device Model</th>
                                                    <th>Count</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${topDeviceModels}" var="device">
                                                    <tr>
                                                        <td>${device.deviceModel}</td>
                                                        <td>${device.count}</td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${empty topDeviceModels}">
                                                    <tr>
                                                        <td colspan="2" class="text-center">No data available</td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- API Performance -->
                            <div class="col-md-4 mb-3">
                                <div class="card analytics-card">
                                    <div class="card-header">API Performance</div>
                                    <div class="card-body">
                                        <div class="mb-3">
                                            <h5>Average API Response Time</h5>
                                            <p class="h3">${avgResponseTime} ms</p>
                                        </div>
                                        <div>
                                            <h5>Average Calories Per Request</h5>
                                            <p class="h3">${avgCalories} cal</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Logs Table -->
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        Request Logs
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th>Request ID</th>
                                        <th>Device Model</th>
                                        <th>Query</th>
                                        <th>Request Time</th>
                                        <th>Response Time</th>
                                        <th>API Response Time (ms)</th>
                                        <th>Food Items</th>
                                        <th>Total Calories</th>
                                        <th>Status</th>
                                        <th>IP Address</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${logs}" var="log">
                                        <tr>
                                            <td>${log.requestId}</td>
                                            <td>${log.deviceModel}</td>
                                            <td>${log.query}</td>
                                            <td><fmt:formatDate value="${log.requestTimestamp}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td><fmt:formatDate value="${log.responseTimestamp}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>${log.apiResponseTime}</td>
                                            <td>${log.foodItemsCount}</td>
                                            <td>${log.totalCalories}</td>
                                            <td>${log.responseStatus}</td>
                                            <td>${log.ipAddress}</td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty logs}">
                                        <tr>
                                            <td colspan="10" class="text-center">No logs available</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
