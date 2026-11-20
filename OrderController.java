package com.canteenpro.controller;
import com.canteenpro.model.Order;import com.canteenpro.dto.CheckoutRequest;import com.canteenpro.service.OrderService;import org.springframework.web.bind.annotation.*;import java.util.*;

@GetMapping
    public ResponseEntity<Map<String, Object>> getAllFoods(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        logger.info("Fetching all foods - page: {}, size: {}, sort: {}", page, size, sortBy);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Food> foodPage = foodService.getAllPaged(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("data", foodPage.getContent());
        response.put("currentPage", foodPage.getNumber());
        response.put("totalItems", foodPage.getTotalElements());
        response.put("totalPages", foodPage.getTotalPages());
        response.put("hasNext", foodPage.hasNext());
        response.put("hasPrevious", foodPage.hasPrevious());
        response.put("isFirst", foodPage.isFirst());
        response.put("isLast", foodPage.isLast());
        response.put("success", true);
        response.put("message", "Foods fetched successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Food>> getAllSimple() {
        logger.info("Fetching simple list of all foods");
        List<Food> foods = foodService.all();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        logger.info("Fetching food with id: {}", id);
        Food food = foodService.get(id);
        if (food == null) {
            throw new FoodNotFoundException("Food not found with id: " + id);
        }
        return ResponseEntity.ok(food);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFoods(@RequestParam String q) {
        logger.info("Searching foods with query: {}", q);
        if (q == null || q.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Food> result = foodService.search(q.trim());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/advanced")
    public ResponseEntity<List<Food>> advancedSearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean available) {

        logger.info("Advanced search - name: {}, category: {}", name, category);
        List<Food> result = foodService.advancedSearch(name, category, minPrice, maxPrice, available);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<Food>> getByCategory(@PathVariable String name) {
        logger.info("Fetching foods by category: {}", name);
        List<Food> foods = foodService.category(name);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        logger.info("Fetching all categories");
        List<String> categories = foodService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

package com.example.foodapp.controller;

import com.example.foodapp.dto.CheckoutRequest;
import com.example.foodapp.dto.OrderResponse;
import com.example.foodapp.dto.OrderStatusUpdateRequest;
import com.example.foodapp.entity.Order;
import com.example.foodapp.exception.OrderNotFoundException;
import com.example.foodapp.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@Validated
@CrossOrigin(origins = "*")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        logger.info("Fetching all orders - page: {}, size: {}, sort: {}", page, size, sortBy);
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orderPage = orderService.getAllPaged(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", orderPage.getContent());
        response.put("currentPage", orderPage.getNumber());
        response.put("totalItems", orderPage.getTotalElements());
        response.put("totalPages", orderPage.getTotalPages());
        response.put("hasNext", orderPage.hasNext());
        response.put("hasPrevious", orderPage.hasPrevious());
        response.put("isFirst", orderPage.isFirst());
        response.put("isLast", orderPage.isLast());
        response.put("success", true);
        response.put("message", "Orders fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllSimple() {
        logger.info("Fetching simple list of all orders");
        List<Order> orders = orderService.all();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        logger.info("Fetching order with id: {}", id);
        Order order = orderService.get(id);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable Long id) {
        logger.info("Fetching order details for id: {}", id);
        OrderResponse response = orderService.getOrderDetails(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<Map<String, Object>>> getOrderItems(@PathVariable Long id) {
        logger.info("Fetching items for order id: {}", id);
        List<Map<String, Object>> items = orderService.getOrderItems(id);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<Order>> getByCustomer(@RequestParam String email) {
        logger.info("Fetching orders for customer: {}", email);
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Order> orders = orderService.byEmail(email.trim());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<List<Order>> getByCustomerPath(@PathVariable String email) {
        logger.info("Fetching orders for customer path: {}", email);
        List<Order> orders = orderService.byEmail(email);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{email}/paged")
    public ResponseEntity<Map<String, Object>> getByCustomerPaged(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching paged orders for customer: {}", email);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Order> orderPage = orderService.byEmailPaged(email, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", orderPage.getContent());
        response.put("currentPage", orderPage.getNumber());
        response.put("totalItems", orderPage.getTotalElements());
        response.put("totalPages", orderPage.getTotalPages());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{email}/count")
    public ResponseEntity<Map<String, Long>> getCustomerOrderCount(@PathVariable String email) {
        logger.info("Counting orders for customer: {}", email);
        long count = orderService.countByEmail(email);
        return ResponseEntity.ok(Map.of("totalOrders", count));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getByStatus(@PathVariable String status) {
        logger.info("Fetching orders by status: {}", status);
        List<Order> orders = orderService.byStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}/paged")
    public ResponseEntity<List<Order>> getByStatusPaged(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching paged orders by status: {}", status);
        List<Order> orders = orderService.byStatusPaged(status, page, size);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/payment/{paymentStatus}")
    public ResponseEntity<List<Order>> getByPayment(@PathVariable String paymentStatus) {
        logger.info("Fetching orders by payment: {}", paymentStatus);
        List<Order> orders = orderService.byPayment(paymentStatus);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/payment/{paymentStatus}/count")
    public ResponseEntity<Map<String, Long>> getPaymentCount(@PathVariable String paymentStatus) {
        logger.info("Counting orders by payment: {}", paymentStatus);
        long count = orderService.countByPayment(paymentStatus);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Order>> searchOrders(@RequestParam String q) {
        logger.info("Searching orders with query: {}", q);
        if (q == null || q.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Order> orders = orderService.search(q.trim());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Order>> filterOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String paymentStatus,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount) {
        logger.info("Filtering orders - status: {}, payment: {}, email: {}", status, paymentStatus, email);
        List<Order> orders = orderService.filter(status, paymentStatus, email, minAmount, maxAmount);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Order>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        logger.info("Fetching orders from {} to {}", from, to);
        List<Order> orders = orderService.byDateRange(from, to);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/today")
    public ResponseEntity<List<Order>> getTodayOrders() {
        logger.info("Fetching today orders");
        List<Order> orders = orderService.todayOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Order>> getRecentOrders(
            @RequestParam(defaultValue = "5") int limit) {
        logger.info("Fetching recent orders, limit: {}", limit);
        List<Order> orders = orderService.recentOrders(limit);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Order>> getPendingOrders() {
        logger.info("Fetching pending orders");
        List<Order> orders = orderService.byStatus("PENDING");
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Order>> getCompletedOrders() {
        logger.info("Fetching completed orders");
        List<Order> orders = orderService.byStatus("COMPLETED");
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/cancelled")
    public ResponseEntity<List<Order>> getCancelledOrders() {
        logger.info("Fetching cancelled orders");
        List<Order> orders = orderService.byStatus("CANCELLED");
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getOrderCount() {
        long count = orderService.count();
        Map<String, Long> response = Map.of("totalOrders", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/status")
    public ResponseEntity<Map<String, Long>> getCountByStatus() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("pending", orderService.countByStatus("PENDING"));
        counts.put("confirmed", orderService.countByStatus("CONFIRMED"));
        counts.put("delivered", orderService.countByStatus("DELIVERED"));
        counts.put("cancelled", orderService.countByStatus("CANCELLED"));
        counts.put("completed", orderService.countByStatus("COMPLETED"));
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<Map<String, Double>> getTotalRevenue() {
        double revenue = orderService.getTotalRevenue();
        Map<String, Double> response = Map.of("totalRevenue", revenue);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/revenue/today")
    public ResponseEntity<Map<String, Double>> getTodayRevenue() {
        double revenue = orderService.getTodayRevenue();
        return ResponseEntity.ok(Map.of("todayRevenue", revenue));
    }

    @GetMapping("/revenue/month")
    public ResponseEntity<Map<String, Double>> getMonthRevenue(
            @RequestParam int year,
            @RequestParam int month) {
        double revenue = orderService.getMonthRevenue(year, month);
        return ResponseEntity.ok(Map.of("monthRevenue", revenue));
    }

    @PostMapping
    public ResponseEntity<Order> checkout(@Valid @RequestBody CheckoutRequest request) {
        logger.info("Checkout request for customer: {}", request.getCustomerEmail());
        Order created = orderService.checkout(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Order>> createBulk(@Valid @RequestBody List<CheckoutRequest> requests) {
        logger.info("Bulk checkout, count: {}", requests.size());
        List<Order> created = new ArrayList<>();
        for (CheckoutRequest req : requests) {
            created.add(orderService.checkout(req));
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody Order order) {
        logger.info("Updating order with id: {}", id);
        Order updated = orderService.update(id, order);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long id,
            @RequestParam String value) {
        logger.info("Updating status for order id: {}, value: {}", id, value);
        Order updated = orderService.status(id, value);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status-body")
    public ResponseEntity<Order> updateStatusBody(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateRequest request) {
        logger.info("Updating status via body for id: {}", id);
        Order updated = orderService.status(id, request.getStatus());
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/payment")
    public ResponseEntity<Order> updatePayment(
            @PathVariable Long id,
            @RequestParam String value) {
        logger.info("Updating payment for order id: {}, value: {}", id, value);
        Order updated = orderService.payment(id, value);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/payment-paid")
    public ResponseEntity<Order> markAsPaid(@PathVariable Long id) {
        logger.info("Marking order as paid, id: {}", id);
        Order updated = orderService.payment(id, "PAID");
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable Long id) {
        logger.info("Cancelling order with id: {}", id);
        orderService.cancel(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order cancelled successfully");
        response.put("cancelledId", String.valueOf(id));
        response.put("success", "true");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Order> confirmOrder(@PathVariable Long id) {
        logger.info("Confirming order with id: {}", id);
        Order updated = orderService.confirm(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/prepare")
    public ResponseEntity<Order> prepareOrder(@PathVariable Long id) {
        logger.info("Preparing order with id: {}", id);
        Order updated = orderService.status(id, "PREPARING");
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/deliver")
    public ResponseEntity<Order> deliverOrder(@PathVariable Long id) {
        logger.info("Marking order as delivered, id: {}", id);
        Order updated = orderService.deliver(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Order> completeOrder(@PathVariable Long id) {
        logger.info("Completing order with id: {}", id);
        Order updated = orderService.status(id, "COMPLETED");
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/refund")
    public ResponseEntity<Order> refundOrder(@PathVariable Long id) {
        logger.info("Refunding order with id: {}", id);
        Order updated = orderService.refund(id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Long id) {
        logger.info("Deleting order with id: {}", id);
        orderService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Order deleted successfully"));
    }

    @DeleteMapping("/customer/{email}")
    public ResponseEntity<Map<String, String>> deleteByCustomer(@PathVariable String email) {
        logger.info("Deleting orders for customer: {}", email);
        orderService.deleteByEmail(email);
        return ResponseEntity.ok(Map.of("message", "Orders deleted for customer"));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("controller", "OrderController");
        status.put("message", "Order API is running");
        status.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(status);
    }
}






