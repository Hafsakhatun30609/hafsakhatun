package com.example.foodapp.controller;

import com.example.foodapp.dto.FoodRequest;
import com.example.foodapp.dto.FoodResponse;
import com.example.foodapp.dto.StockUpdateRequest;
import com.example.foodapp.entity.Food;
import com.example.foodapp.exception.FoodNotFoundException;
import com.example.foodapp.service.FoodService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/foods")
@Validated
@CrossOrigin(origins = "*")
public class FoodController {

    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

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

    @GetMapping("/dto")
    public ResponseEntity<List<FoodResponse>> getAllDto() {
        logger.info("Fetching all foods as DTO");
        List<FoodResponse> list = foodService.getAllDto();
        return ResponseEntity.ok(list);
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

    @GetMapping("/{id}/dto")
    public ResponseEntity<FoodResponse> getFoodDtoById(@PathVariable Long id) {
        logger.info("Fetching food DTO with id: {}", id);
        FoodResponse dto = foodService.getDto(id);
        if (dto == null) {
            throw new FoodNotFoundException("Food not found with id: " + id);
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Map<String, Object>> getFoodDetails(@PathVariable Long id) {
        logger.info("Fetching food details for id: {}", id);
        Food food = foodService.get(id);
        if (food == null) {
            throw new FoodNotFoundException("Food not found with id: " + id);
        }
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("food", food);
        details.put("averageRating", foodService.getAverageRating(id));
        details.put("reviewCount", foodService.getReviewCount(id));
        details.put("soldCount", food.getSoldCount());
        details.put("isInStock", food.getStock() > 0);
        details.put("isLowStock", food.getStock() <= 10 && food.getStock() > 0);
        details.put("finalPrice", food.getFinalPrice());
        details.put("success", true);
        return ResponseEntity.ok(details);
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

    @GetMapping("/search/by-name")
    public ResponseEntity<List<Food>> searchByName(@RequestParam @NotBlank String name) {
        logger.info("Searching foods by name: {}", name);
        List<Food> result = foodService.searchByName(name);
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

    @GetMapping("/search/keyword")
    public ResponseEntity<List<Food>> searchByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Searching by keyword: {}", keyword);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Food> result = foodService.searchPaged(keyword, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", result.getContent());
        response.put("totalItems", result.getTotalElements());
        response.put("totalPages", result.getTotalPages());
        return ResponseEntity.ok(result.getContent());
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<Food>> getByCategory(@PathVariable String name) {
        logger.info("Fetching foods by category: {}", name);
        List<Food> foods = foodService.category(name);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/category/{name}/paged")
    public ResponseEntity<Map<String, Object>> getByCategoryPaged(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching paged foods by category: {}", name);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Food> foodPage = foodService.categoryPaged(name, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", foodPage.getContent());
        response.put("currentPage", foodPage.getNumber());
        response.put("totalItems", foodPage.getTotalElements());
        response.put("totalPages", foodPage.getTotalPages());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        logger.info("Fetching all categories");
        List<String> categories = foodService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories/count")
    public ResponseEntity<List<Map<String, Object>>> getCategoryCounts() {
        logger.info("Fetching category counts");
        List<Map<String, Object>> result = new ArrayList<>();
        for (String cat : foodService.getAllCategories()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("category", cat);
            item.put("count", foodService.category(cat).size());
            result.add(item);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Food>> getByPriceRange(
            @RequestParam @Min(0) double min,
            @RequestParam @Min(0) double max) {
        logger.info("Fetching foods in price range: {} - {}", min, max);
        if (min > max) {
            return ResponseEntity.badRequest().build();
        }
        List<Food> foods = foodService.findByPriceRange(min, max);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/price/under/{price}")
    public ResponseEntity<List<Food>> getUnderPrice(@PathVariable double price) {
        logger.info("Fetching foods under price: {}", price);
        List<Food> foods = foodService.findByPriceLessThan(price);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/price/above/{price}")
    public ResponseEntity<List<Food>> getAbovePrice(@PathVariable double price) {
        logger.info("Fetching foods above price: {}", price);
        List<Food> foods = foodService.findByPriceGreaterThan(price);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Food>> getAvailableFoods() {
        logger.info("Fetching available foods");
        List<Food> foods = foodService.getAvailableFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Food>> getUnavailableFoods() {
        logger.info("Fetching unavailable foods");
        List<Food> foods = foodService.getUnavailableFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Food>> getLowStock(
            @RequestParam(defaultValue = "10") int threshold) {
        logger.info("Fetching low stock foods, threshold: {}", threshold);
        List<Food> foods = foodService.getLowStockFoods(threshold);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Food>> getOutOfStock() {
        logger.info("Fetching out of stock foods");
        List<Food> foods = foodService.getOutOfStockFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/in-stock")
    public ResponseEntity<List<Food>> getInStock() {
        logger.info("Fetching in stock foods");
        List<Food> foods = foodService.getInStockFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Food>> getPopularFoods(
            @RequestParam(defaultValue = "10") int limit) {
        logger.info("Fetching popular foods, limit: {}", limit);
        List<Food> foods = foodService.getPopularFoods(limit);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Food>> getFeaturedFoods() {
        logger.info("Fetching featured foods");
        List<Food> foods = foodService.getFeaturedFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/best-seller")
    public ResponseEntity<List<Food>> getBestSellers(
            @RequestParam(defaultValue = "10") int limit) {
        logger.info("Fetching best sellers");
        List<Food> foods = foodService.getBestSellers(limit);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/new")
    public ResponseEntity<List<Food>> getNewFoods(
            @RequestParam(defaultValue = "10") int limit) {
        logger.info("Fetching new foods");
        List<Food> foods = foodService.getNewFoods(limit);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/veg")
    public ResponseEntity<List<Food>> getVegFoods() {
        logger.info("Fetching veg foods");
        List<Food> foods = foodService.getVegFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/non-veg")
    public ResponseEntity<List<Food>> getNonVegFoods() {
        logger.info("Fetching non-veg foods");
        List<Food> foods = foodService.getNonVegFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/spicy")
    public ResponseEntity<List<Food>> getSpicyFoods() {
        logger.info("Fetching spicy foods");
        List<Food> foods = foodService.getSpicyFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/discounted")
    public ResponseEntity<List<Food>> getDiscountedFoods() {
        logger.info("Fetching discounted foods");
        List<Food> foods = foodService.getDiscountedFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Food>> getTopRated(
            @RequestParam(defaultValue = "10") int limit) {
        logger.info("Fetching top rated foods");
        List<Food> foods = foodService.getTopRated(limit);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Food>> getRecentFoods(
            @RequestParam(defaultValue = "10") int limit) {
        logger.info("Fetching recent foods");
        List<Food> foods = foodService.getRecentFoods(limit);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/sorted/price-asc")
    public ResponseEntity<List<Food>> getSortedByPriceAsc() {
        logger.info("Fetching foods sorted by price asc");
        List<Food> foods = foodService.getSortedByPriceAsc();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/sorted/price-desc")
    public ResponseEntity<List<Food>> getSortedByPriceDesc() {
        logger.info("Fetching foods sorted by price desc");
        List<Food> foods = foodService.getSortedByPriceDesc();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/sorted/name-asc")
    public ResponseEntity<List<Food>> getSortedByNameAsc() {
        logger.info("Fetching foods sorted by name asc");
        List<Food> foods = foodService.getSortedByNameAsc();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/sorted/rating-desc")
    public ResponseEntity<List<Food>> getSortedByRatingDesc() {
        logger.info("Fetching foods sorted by rating desc");
        List<Food> foods = foodService.getSortedByRatingDesc();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getFoodCount() {
        long count = foodService.count();
        Map<String, Long> response = Map.of("totalFoods", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/category/{name}")
    public ResponseEntity<Map<String, Object>> getCountByCategory(@PathVariable String name) {
        long count = foodService.category(name).size();
        Map<String, Object> response = new HashMap<>();
        response.put("category", name);
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/status")
    public ResponseEntity<Map<String, Long>> getCountByStatus() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("total", foodService.count());
        counts.put("available", (long) foodService.getAvailableFoods().size());
        counts.put("unavailable", (long) foodService.getUnavailableFoods().size());
        counts.put("lowStock", (long) foodService.getLowStockFoods(10).size());
        counts.put("outOfStock", (long) foodService.getOutOfStockFoods().size());
        counts.put("featured", (long) foodService.getFeaturedFoods().size());
        counts.put("veg", (long) foodService.getVegFoods().size());
        counts.put("nonVeg", (long) foodService.getNonVegFoods().size());
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getFoodStats() {
        List<Food> foods = foodService.all();
        double avgPrice = foods.stream().mapToDouble(Food::getPrice).average().orElse(0.0);
        double maxPrice = foods.stream().mapToDouble(Food::getPrice).max().orElse(0.0);
        double minPrice = foods.stream().mapToDouble(Food::getPrice).min().orElse(0.0);
        int totalStock = foods.stream().mapToInt(Food::getStock).sum();
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalFoods", foods.size());
        stats.put("averagePrice", avgPrice);
        stats.put("maxPrice", maxPrice);
        stats.put("minPrice", minPrice);
        stats.put("totalStock", totalStock);
        stats.put("categories", foodService.getAllCategories().size());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Map<String, Boolean>> checkExists(@PathVariable Long id) {
        boolean exists = foodService.exists(id);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Map<String, Object>> getStock(@PathVariable Long id) {
        Food food = foodService.get(id);
        if (food == null) {
            throw new FoodNotFoundException("Food not found with id: " + id);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("foodId", id);
        response.put("foodName", food.getName());
        response.put("stock", food.getStock());
        response.put("available", food.isAvailable());
        response.put("isLowStock", food.getStock() <= 10);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/price")
    public ResponseEntity<Map<String, Object>> getPrice(@PathVariable Long id) {
        Food food = foodService.get(id);
        if (food == null) {
            throw new FoodNotFoundException("Food not found with id: " + id);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("foodId", id);
        response.put("price", food.getPrice());
        response.put("discountPrice", food.getDiscountPrice());
        response.put("finalPrice", food.getFinalPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/rating")
    public ResponseEntity<Map<String, Object>> getRating(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("foodId", id);
        response.put("averageRating", foodService.getAverageRating(id));
        response.put("reviewCount", foodService.getReviewCount(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<List<Food>> getSimilarFoods(@PathVariable Long id) {
        logger.info("Fetching similar foods for id: {}", id);
        List<Food> foods = foodService.getSimilarFoods(id);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<Food>> getRecommendations(@PathVariable Long id) {
        logger.info("Fetching recommendations for id: {}", id);
        List<Food> foods = foodService.getRecommendations(id);
        return ResponseEntity.ok(foods);
    }

    @PostMapping
    public ResponseEntity<Food> createFood(@Valid @RequestBody Food food) {
        logger.info("Creating new food: {}", food.getName());
        Food created = foodService.create(food);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/dto")
    public ResponseEntity<Food> createFoodDto(@Valid @RequestBody FoodRequest request) {
        logger.info("Creating food via DTO: {}", request.getName());
        Food created = foodService.createFromDto(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Food>> createBulk(@Valid @RequestBody List<Food> foods) {
        logger.info("Creating bulk foods, count: {}", foods.size());
        List<Food> created = foodService.createBulk(foods);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/bulk/dto")
    public ResponseEntity<List<Food>> createBulkDto(@Valid @RequestBody List<FoodRequest> requests) {
        logger.info("Creating bulk foods via DTO, count: {}", requests.size());
        List<Food> created = foodService.createBulkFromDto(requests);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/duplicate")
    public ResponseEntity<Food> duplicateFood(@PathVariable Long id) {
        logger.info("Duplicating food id: {}", id);
        Food duplicated = foodService.duplicate(id);
        return new ResponseEntity<>(duplicated, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Food> likeFood(@PathVariable Long id) {
        logger.info("Liking food id: {}", id);
        Food updated = foodService.like(id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<Food> viewFood(@PathVariable Long id) {
        logger.info("Viewing food id: {}", id);
        Food updated = foodService.incrementView(id);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(
            @PathVariable Long id,
            @Valid @RequestBody Food food) {
        logger.info("Updating food with id: {}", id);
        Food updated = foodService.update(id, food);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/dto")
    public ResponseEntity<Food> updateFoodDto(
            @PathVariable Long id,
            @Valid @RequestBody FoodRequest request) {
        logger.info("Updating food via DTO, id: {}", id);
        Food updated = foodService.updateFromDto(id, request);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Food> updateStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        logger.info("Updating stock for id: {}, quantity: {}", id, quantity);
        Food updated = foodService.stock(id, quantity);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/stock/dto")
    public ResponseEntity<Food> updateStockDto(
            @PathVariable Long id,
            @Valid @RequestBody StockUpdateRequest request) {
        logger.info("Updating stock via DTO for id: {}", id);
        Food updated = foodService.updateStockDto(id, request);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/stock/increase")
    public ResponseEntity<Food> increaseStock(
            @PathVariable Long id,
            @RequestParam @Min(1) int amount) {
        logger.info("Increasing stock for id: {}, amount: {}", id, amount);
        Food updated = foodService.increaseStock(id, amount);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/stock/decrease")
    public ResponseEntity<Food> decreaseStock(
            @PathVariable Long id,
            @RequestParam @Min(1) int amount) {
        logger.info("Decreasing stock for id: {}, amount: {}", id, amount);
        Food updated = foodService.decreaseStock(id, amount);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<Food> updatePrice(
            @PathVariable Long id,
            @RequestParam double price) {
        logger.info("Updating price for id: {}, price: {}", id, price);
        Food updated = foodService.updatePrice(id, price);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/discount")
    public ResponseEntity<Food> updateDiscount(
            @PathVariable Long id,
            @RequestParam double discountPercent) {
        logger.info("Updating discount for id: {}", id);
        Food updated = foodService.updateDiscount(id, discountPercent);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/toggle-available")
    public ResponseEntity<Food> toggleAvailability(@PathVariable Long id) {
        logger.info("Toggling availability for id: {}", id);
        Food updated = foodService.toggleAvailability(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/available")
    public ResponseEntity<Food> setAvailable(
            @PathVariable Long id,
            @RequestParam boolean available) {
        logger.info("Setting availability for id: {} to {}", id, available);
        Food updated = foodService.setAvailable(id, available);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/featured")
    public ResponseEntity<Food> toggleFeatured(@PathVariable Long id) {
        logger.info("Toggling featured for id: {}", id);
        Food updated = foodService.toggleFeatured(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/popular")
    public ResponseEntity<Food> togglePopular(@PathVariable Long id) {
        logger.info("Toggling popular for id: {}", id);
        Food updated = foodService.togglePopular(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Food> updateName(
            @PathVariable Long id,
            @RequestParam @NotBlank String name) {
        logger.info("Updating name for id: {}", id);
        Food updated = foodService.updateName(id, name);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/category")
    public ResponseEntity<Food> updateCategory(
            @PathVariable Long id,
            @RequestParam @NotBlank String category) {
        logger.info("Updating category for id: {}", id);
        Food updated = foodService.updateCategory(id, category);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Food> updateImage(
            @PathVariable Long id,
            @RequestParam @NotBlank String imageUrl) {
        logger.info("Updating image for id: {}", id);
        Food updated = foodService.updateImage(id, imageUrl);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/description")
    public ResponseEntity<Food> updateDescription(
            @PathVariable Long id,
            @RequestParam String description) {
        logger.info("Updating description for id: {}", id);
        Food updated = foodService.updateDescription(id, description);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteFood(@PathVariable Long id) {
        logger.info("Deleting food with id: {}", id);
        foodService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Food deleted successfully");
        response.put("deletedId", String.valueOf(id));
        response.put("success", "true");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/category/{name}")
    public ResponseEntity<Map<String, String>> deleteByCategory(@PathVariable String name) {
        logger.info("Deleting foods by category: {}", name);
        foodService.deleteByCategory(name);
        return ResponseEntity.ok(Map.of("message", "Foods deleted for category: " + name));
    }

    @DeleteMapping("/out-of-stock")
    public ResponseEntity<Map<String, String>> deleteOutOfStock() {
        logger.warn("Deleting out of stock foods");
        foodService.deleteOutOfStock();
        return ResponseEntity.ok(Map.of("message", "Out of stock foods deleted"));
    }

    @DeleteMapping("/all")
    public ResponseEntity<Map<String, String>> deleteAllFoods() {
        logger.warn("Deleting ALL foods - Admin action");
        foodService.deleteAll();
        return ResponseEntity.ok(Map.of("message", "All foods deleted"));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("controller", "FoodController");
        status.put("message", "Food API is running");
        status.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(status);
    }
}