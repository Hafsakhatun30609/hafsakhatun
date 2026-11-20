package com.canteenpro.controller;

import com.canteenpro.model.Food;
import com.canteenpro.model.Order;
import com.canteenpro.model.Review;
import com.canteenpro.service.FoodService;
import com.canteenpro.service.OrderService;
import com.canteenpro.service.ReviewService;
import com.canteenpro.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private final FoodService foodService;
    private final UserService userService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    public DashboardController(FoodService foodService, UserService userService, OrderService orderService, ReviewService reviewService) {
        this.foodService = foodService;
        this.userService = userService;
        this.orderService = orderService;
        this.reviewService = reviewService;
    }

    public DashboardController(FoodService foodService, UserService userService, OrderService orderService) {
        this.foodService = foodService;
        this.userService = userService;
        this.orderService = orderService;
        this.reviewService = null;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> stats() {
        logger.info("Fetching dashboard stats");
        List<Order> orders = orderService.all();
        double revenue = calculateRevenue(orders);
        long pending = countByStatus(orders, "PENDING");
        long completed = countByStatus(orders, "COMPLETED");
        long cancelled = countByStatus(orders, "CANCELLED");
        long processing = countByStatus(orders, "PROCESSING");
        long confirmed = countByStatus(orders, "CONFIRMED");
        long delivered = countByStatus(orders, "DELIVERED");
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("foodCount", foodService.all().size());
        result.put("userCount", userService.all().size());
        result.put("orderCount", orders.size());
        result.put("pendingOrders", pending);
        result.put("processingOrders", processing);
        result.put("completedOrders", completed);
        result.put("cancelledOrders", cancelled);
        result.put("confirmedOrders", confirmed);
        result.put("deliveredOrders", delivered);
        result.put("totalRevenue", revenue);
        result.put("todayRevenue", calculateTodayRevenue(orders));
        result.put("averageOrderValue", calculateAverageOrderValue(orders));
        result.put("success", true);
        result.put("message", "Dashboard stats fetched successfully");
        result.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> summary() {
        logger.info("Fetching dashboard summary");
        List<Order> orderList = orderService.all();
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("totalFoods", foodService.all().size());
        summary.put("totalUsers", userService.all().size());
        summary.put("totalOrders", orderList.size());
        summary.put("revenue", calculateRevenue(orderList));
        summary.put("pending", countByStatus(orderList, "PENDING"));
        summary.put("processing", countByStatus(orderList, "PROCESSING"));
        summary.put("completed", countByStatus(orderList, "COMPLETED"));
        summary.put("cancelled", countByStatus(orderList, "CANCELLED"));
        summary.put("confirmed", countByStatus(orderList, "CONFIRMED"));
        summary.put("delivered", countByStatus(orderList, "DELIVERED"));
        summary.put("availableFoods", countAvailableFoods());
        summary.put("outOfStockFoods", countOutOfStockFoods());
        summary.put("lowStockFoods", countLowStockFoods());
        summary.put("success", true);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/stats/overview")
    public ResponseEntity<Map<String, Object>> overview() {
        List<Order> orders = orderService.all();
        List<Food> foods = foodService.all();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalFoods", foods.size());
        data.put("totalUsers", userService.all().size());
        data.put("totalOrders", orders.size());
        data.put("totalRevenue", calculateRevenue(orders));
        data.put("totalReviews", reviewService != null ? reviewService.all().size() : 0);
        data.put("averageRating", calculateAverageRating());
        data.put("todayOrders", countTodayOrders(orders));
        data.put("todayRevenue", calculateTodayRevenue(orders));
        data.put("weekOrders", countWeekOrders(orders));
        data.put("monthOrders", countMonthOrders(orders));
        data.put("monthRevenue", calculateMonthRevenue(orders));
        data.put("yearRevenue", calculateYearRevenue(orders));
        return ResponseEntity.ok(data);
    }

    @GetMapping("/stats/cards")
    public ResponseEntity<List<Map<String, Object>>> statCards() {
        List<Order> orders = orderService.all();
        List<Map<String, Object>> cards = new ArrayList<>();
        cards.add(buildCard("Total Revenue", calculateRevenue(orders), "BDT", "up", "+12.5%"));
        cards.add(buildCard("Total Orders", (double) orders.size(), "orders", "up", "+8.2%"));
        cards.add(buildCard("Total Foods", (double) foodService.all().size(), "items", "up", "+3.1%"));
        cards.add(buildCard("Total Users", (double) userService.all().size(), "users", "up", "+15.0%"));
        cards.add(buildCard("Pending Orders", (double) countByStatus(orders, "PENDING"), "orders", "down", "-2.0%"));
        cards.add(buildCard("Completed Orders", (double) countByStatus(orders, "COMPLETED"), "orders", "up", "+10.5%"));
        cards.add(buildCard("Cancelled Orders", (double) countByStatus(orders, "CANCELLED"), "orders", "down", "-1.2%"));
        cards.add(buildCard("Avg Order Value", calculateAverageOrderValue(orders), "BDT", "up", "+5.4%"));
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        logger.info("Fetching orders by status: {}", status);
        List<Order> filtered = orderService.all().stream()
                .filter(order -> status.equalsIgnoreCase(order.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/orders/status/{status}/count")
    public ResponseEntity<Map<String, Object>> orderCountByStatus(@PathVariable String status) {
        long count = countByStatus(orderService.all(), status.toUpperCase());
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("count", count);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/recent")
    public ResponseEntity<List<Order>> recentOrders() {
        List<Order> orderList = orderService.all();
        List<Order> recent = orderList.stream()
                .sorted(Comparator.comparing(Order::getId).reversed())
                .limit(10)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recent);
    }

    @GetMapping("/orders/recent/{limit}")
    public ResponseEntity<List<Order>> recentOrdersLimit(@PathVariable int limit) {
        List<Order> orderList = orderService.all();
        List<Order> recent = orderList.stream()
                .sorted(Comparator.comparing(Order::getId).reversed())
                .limit(limit)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recent);
    }

    @GetMapping("/orders/today")
    public ResponseEntity<List<Order>> todayOrders() {
        List<Order> orders = orderService.all();
        List<Order> today = orders.stream()
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().toLocalDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(today);
    }

    @GetMapping("/orders/pending")
    public ResponseEntity<List<Order>> pendingOrders() {
        List<Order> filtered = orderService.all().stream()
                .filter(o -> "PENDING".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/orders/completed")
    public ResponseEntity<List<Order>> completedOrders() {
        List<Order> filtered = orderService.all().stream()
                .filter(o -> "COMPLETED".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/orders/cancelled")
    public ResponseEntity<List<Order>> cancelledOrders() {
        List<Order> filtered = orderService.all().stream()
                .filter(o -> "CANCELLED".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/orders/processing")
    public ResponseEntity<List<Order>> processingOrders() {
        List<Order> filtered = orderService.all().stream()
                .filter(o -> "PROCESSING".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/orders/paged")
    public ResponseEntity<Map<String, Object>> pagedOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Order> orderPage = orderService.getAllPaged(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", orderPage.getContent());
        response.put("currentPage", orderPage.getNumber());
        response.put("totalItems", orderPage.getTotalElements());
        response.put("totalPages", orderPage.getTotalPages());
        response.put("hasNext", orderPage.hasNext());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/revenue")
    public ResponseEntity<Map<String, Object>> revenue() {
        List<Order> orders = orderService.all();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalRevenue", calculateRevenue(orders));
        data.put("todayRevenue", calculateTodayRevenue(orders));
        data.put("weekRevenue", calculateWeekRevenue(orders));
        data.put("monthRevenue", calculateMonthRevenue(orders));
        data.put("yearRevenue", calculateYearRevenue(orders));
        data.put("averageOrderValue", calculateAverageOrderValue(orders));
        data.put("pendingRevenue", calculateRevenueByStatus(orders, "PENDING"));
        data.put("completedRevenue", calculateRevenueByStatus(orders, "COMPLETED"));
        data.put("cancelledRevenue", calculateRevenueByStatus(orders, "CANCELLED"));
        return ResponseEntity.ok(data);
    }

    @GetMapping("/revenue/chart")
    public ResponseEntity<List<Map<String, Object>>> revenueChart() {
        List<Order> orders = orderService.all();
        List<Map<String, Object>> chart = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            double dayRevenue = orders.stream()
                    .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().toLocalDate().equals(date))
                    .filter(o -> !"CANCELLED".equalsIgnoreCase(o.getStatus()))
                    .mapToDouble(Order::getTotal)
                    .sum();
            long dayCount = orders.stream()
                    .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().toLocalDate().equals(date))
                    .count();
            Map<String, Object> point = new LinkedHashMap<>();
            point.put("date", date.toString());
            point.put("label", date.format(DateTimeFormatter.ofPattern("EEE")));
            point.put("revenue", dayRevenue);
            point.put("orders", dayCount);
            chart.add(point);
        }
        return ResponseEntity.ok(chart);
    }

    @GetMapping("/revenue/monthly")
    public ResponseEntity<List<Map<String, Object>>> monthlyRevenue() {
        List<Order> orders = orderService.all();
        List<Map<String, Object>> list = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            final int month = m;
            double rev = orders.stream()
                    .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().getMonthValue() == month && o.getCreatedAt().getYear() == LocalDate.now().getYear())
                    .filter(o -> !"CANCELLED".equalsIgnoreCase(o.getStatus()))
                    .mapToDouble(Order::getTotal)
                    .sum();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("month", month);
            item.put("monthName", java.time.Month.of(month).name());
            item.put("revenue", rev);
            list.add(item);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/foods/top-selling")
    public ResponseEntity<List<Map<String, Object>>> topSellingFoods(
            @RequestParam(defaultValue = "5") int limit) {
        List<Order> orders = orderService.all();
        Map<Long, Long> foodCount = new HashMap<>();
        Map<Long, String> foodNameMap = new HashMap<>();
        Map<Long, Double> foodRevenue = new HashMap<>();
        for (Order order : orders) {
            if (order.getItems() == null) {
                continue;
            }
            for (var item : order.getItems()) {
                foodCount.put(item.getFoodId(), foodCount.getOrDefault(item.getFoodId(), 0L) + item.getQuantity());
                foodNameMap.put(item.getFoodId(), item.getFoodName());
                foodRevenue.put(item.getFoodId(), foodRevenue.getOrDefault(item.getFoodId(), 0.0) + item.getSubtotal());
            }
        }
        List<Map<String, Object>> result = foodCount.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(limit)
                .map(e -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("foodId", e.getKey());
                    m.put("foodName", foodNameMap.get(e.getKey()));
                    m.put("quantitySold", e.getValue());
                    m.put("revenue", foodRevenue.get(e.getKey()));
                    return m;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/foods/popular")
    public ResponseEntity<List<Food>> popularFoods(
            @RequestParam(defaultValue = "5") int limit) {
        List<Food> foods = foodService.all().stream()
                .sorted(Comparator.comparing(Food::getSoldCount, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(limit)
                .collect(Collectors.toList());
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/foods/low-stock")
    public ResponseEntity<List<Food>> lowStockFoods() {
        List<Food> filtered = foodService.all().stream()
                .filter(f -> f.getStock() <= 10 && f.getStock() > 0)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/foods/out-of-stock")
    public ResponseEntity<List<Food>> outOfStockFoods() {
        List<Food> filtered = foodService.all().stream()
                .filter(f -> f.getStock() <= 0 || !f.isAvailable())
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/foods/category-stats")
    public ResponseEntity<List<Map<String, Object>>> categoryStats() {
        List<Food> foods = foodService.all();
        Map<String, Long> grouped = foods.stream()
                .collect(Collectors.groupingBy(Food::getCategory, Collectors.counting()));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Long> e : grouped.entrySet()) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("category", e.getKey());
            m.put("count", e.getValue());
            result.add(m);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/recent")
    public ResponseEntity<List<Object>> recentUsers() {
        List<Object> users = userService.all().stream()
                .sorted(Comparator.comparing(o -> o.toString()).reversed())
                .limit(10)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/count")
    public ResponseEntity<Map<String, Object>> userCount() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalUsers", userService.all().size());
        data.put("success", true);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/users/top-customers")
    public ResponseEntity<List<Map<String, Object>>> topCustomers(
            @RequestParam(defaultValue = "5") int limit) {
        List<Order> orders = orderService.all();
        Map<String, Long> countMap = new HashMap<>();
        Map<String, Double> spentMap = new HashMap<>();
        for (Order o : orders) {
            String email = o.getCustomerEmail() != null ? o.getCustomerEmail() : "Unknown";
            countMap.put(email, countMap.getOrDefault(email, 0L) + 1);
            if (!"CANCELLED".equalsIgnoreCase(o.getStatus())) {
                spentMap.put(email, spentMap.getOrDefault(email, 0.0) + o.getTotal());
            }
        }
        List<Map<String, Object>> result = countMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(e -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("email", e.getKey());
                    m.put("orderCount", e.getValue());
                    m.put("totalSpent", spentMap.getOrDefault(e.getKey(), 0.0));
                    return m;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reviews/summary")
    public ResponseEntity<Map<String, Object>> reviewSummary() {
        Map<String, Object> data = new LinkedHashMap<>();
        if (reviewService == null) {
            data.put("totalReviews", 0);
            data.put("averageRating", 0.0);
            return ResponseEntity.ok(data);
        }
        List<Review> reviews = reviewService.all();
        double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        data.put("totalReviews", reviews.size());
        data.put("averageRating", avg);
        data.put("fiveStar", reviews.stream().filter(r -> r.getRating() == 5).count());
        data.put("fourStar", reviews.stream().filter(r -> r.getRating() == 4).count());
        data.put("threeStar", reviews.stream().filter(r -> r.getRating() == 3).count());
        data.put("twoStar", reviews.stream().filter(r -> r.getRating() == 2).count());
        data.put("oneStar", reviews.stream().filter(r -> r.getRating() == 1).count());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/reviews/recent")
    public ResponseEntity<List<Review>> recentReviews() {
        if (reviewService == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        List<Review> reviews = reviewService.all().stream()
                .sorted(Comparator.comparing(Review::getId).reversed())
                .limit(10)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/chart/orders-by-status")
    public ResponseEntity<List<Map<String, Object>>> ordersByStatusChart() {
        List<Order> orders = orderService.all();
        String[] statuses = {"PENDING", "PROCESSING", "CONFIRMED", "DELIVERED", "COMPLETED", "CANCELLED"};
        List<Map<String, Object>> chart = new ArrayList<>();
        for (String s : statuses) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("status", s);
            item.put("count", countByStatus(orders, s));
            item.put("revenue", calculateRevenueByStatus(orders, s));
            chart.add(item);
        }
        return ResponseEntity.ok(chart);
    }

    @GetMapping("/chart/orders-by-day")
    public ResponseEntity<List<Map<String, Object>>> ordersByDay() {
        List<Order> orders = orderService.all();
        List<Map<String, Object>> chart = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            long count = orders.stream()
                    .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().toLocalDate().equals(date))
                    .count();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("date", date.toString());
            item.put("label", date.format(DateTimeFormatter.ofPattern("EEE dd")));
            item.put("orders", count);
            chart.add(item);
        }
        return ResponseEntity.ok(chart);
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<Map<String, Object>>> alerts() {
        List<Map<String, Object>> alerts = new ArrayList<>();
        long lowStock = countLowStockFoods();
        if (lowStock > 0) {
            alerts.add(buildAlert("LOW_STOCK", "Low Stock Alert", lowStock + " foods are low in stock", "warning"));
        }
        long outOfStock = countOutOfStockFoods();
        if (outOfStock > 0) {
            alerts.add(buildAlert("OUT_OF_STOCK", "Out of Stock", outOfStock + " foods are out of stock", "danger"));
        }
        long pending = countByStatus(orderService.all(), "PENDING");
        if (pending > 0) {
            alerts.add(buildAlert("PENDING_ORDERS", "Pending Orders", pending + " orders are waiting", "info"));
        }
        long cancelled = countByStatus(orderService.all(), "CANCELLED");
        if (cancelled > 5) {
            alerts.add(buildAlert("HIGH_CANCEL", "High Cancellation", cancelled + " orders cancelled", "danger"));
        }
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/export")
    public ResponseEntity<Map<String, Object>> exportData() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("foods", foodService.all());
        data.put("orders", orderService.all());
        data.put("users", userService.all());
        data.put("exportedAt", LocalDateTime.now().toString());
        data.put("success", true);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("controller", "DashboardController");
        status.put("message", "Dashboard API is running");
        status.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(status);
    }

    private double calculateRevenue(List<Order> orderList) {
        return orderList.stream()
                .filter(order -> !"CANCELLED".equals(order.getStatus()))
                .mapToDouble(Order::getTotal)
                .sum();
    }

    private double calculateRevenueByStatus(List<Order> orderList, String status) {
        return orderList.stream()
                .filter(order -> status.equalsIgnoreCase(order.getStatus()))
                .mapToDouble(Order::getTotal)
                .sum();
    }

    private long countByStatus(List<Order> orderList, String status) {
        return orderList.stream()
                .filter(order -> status.equals(order.getStatus()))
                .count();
    }

    private double calculateAverageOrderValue(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return 0.0;
        }
        return calculateRevenue(orders) / orders.size();
    }

    private double calculateTodayRevenue(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().toLocalDate().equals(LocalDate.now()))
                .filter(o -> !"CANCELLED".equalsIgnoreCase(o.getStatus()))
                .mapToDouble(Order::getTotal)
                .sum();
    }

    private double calculateWeekRevenue(List<Order> orders) {
        LocalDate weekAgo = LocalDate.now().minusDays(7);
        return orders.stream()
                .filter(o -> o.getCreatedAt() != null && !o.getCreatedAt().toLocalDate().isBefore(weekAgo))
                .filter(o -> !"CANCELLED".equalsIgnoreCase(o.getStatus()))
                .mapToDouble(Order::getTotal)
                .sum();
    }

    private double calculateMonthRevenue(List<Order> orders) {
        LocalDate now = LocalDate.now();
        return orders.stream()
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().getMonthValue() == now.getMonthValue() && o.getCreatedAt().getYear() == now.getYear())
                .filter(o -> !"CANCELLED".equalsIgnoreCase(o.getStatus()))
                .mapToDouble(Order::getTotal)
                .sum();
    }

    private double calculateYearRevenue(List<Order> orders) {
        int year = LocalDate.now().getYear();
        return orders.stream()
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().getYear() == year)
                .filter(o -> !"CANCELLED".equalsIgnoreCase(o.getStatus()))
                .mapToDouble(Order::getTotal)
                .sum();
    }

    private long countTodayOrders(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().toLocalDate().equals(LocalDate.now()))
                .count();
    }

    private long countWeekOrders(List<Order> orders) {
        LocalDate weekAgo = LocalDate.now().minusDays(7);
        return orders.stream()
                .filter(o -> o.getCreatedAt() != null && !o.getCreatedAt().toLocalDate().isBefore(weekAgo))
                .count();
    }

    private long countMonthOrders(List<Order> orders) {
        LocalDate now = LocalDate.now();
        return orders.stream()
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().getMonthValue() == now.getMonthValue() && o.getCreatedAt().getYear() == now.getYear())
                .count();
    }

    private long countAvailableFoods() {
        return foodService.all().stream()
                .filter(Food::isAvailable)
                .count();
    }

    private long countOutOfStockFoods() {
        return foodService.all().stream()
                .filter(f -> f.getStock() <= 0 || !f.isAvailable())
                .count();
    }

    private long countLowStockFoods() {
        return foodService.all().stream()
                .filter(f -> f.getStock() > 0 && f.getStock() <= 10)
                .count();
    }

    private double calculateAverageRating() {
        if (reviewService == null) {
            return 0.0;
        }
        return reviewService.all().stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    private Map<String, Object> buildCard(String title, double value, String unit, String trend, String change) {
        Map<String, Object> card = new LinkedHashMap<>();
        card.put("title", title);
        card.put("value", value);
        card.put("unit", unit);
        card.put("trend", trend);
        card.put("change", change);
        return card;
    }

    private Map<String, Object> buildAlert(String type, String title, String message, String level) {
        Map<String, Object> alert = new LinkedHashMap<>();
        alert.put("type", type);
        alert.put("title", title);
        alert.put("message", message);
        alert.put("level", level);
        alert.put("timestamp", LocalDateTime.now().toString());
        return alert;
    }
}