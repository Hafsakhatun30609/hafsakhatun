package com.canteenpro.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A self-contained sample-data and demonstration utility for CanteenPro.
 * This class intentionally has no external integrations or database dependency.
 */
public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}
public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}
public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}
public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}
public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}
public final class Sample {
    private Sample() { }

    public static final class FoodSample {
        private final int id;
        private final String name, category, description;
        private final double price;
        private final int stock;
        private final boolean vegetarian;

        public FoodSample(int id, String name, String category, String description,
                         double price, int stock, boolean vegetarian) {
            this.id = id; this.name = name; this.category = category;
            this.description = description; this.price = price; this.stock = stock;
            this.vegetarian = vegetarian;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isVegetarian() { return vegetarian; }
        public boolean isInStock() { return stock > 0; }
        public double priceWithTax(double taxRate) { return price + price * taxRate; }
        public String displayLine() { return id + " | " + name + " | " + category + " | Tk " + price + " | stock: " + stock; }
    }

    public static final class SampleOrderLine {
        private final FoodSample food;
        private final int quantity;
        public SampleOrderLine(FoodSample food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodSample getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return food.getPrice() * quantity; }
    }

    public static final class SampleOrder {
        private final String orderCode, customerName, status;
        private final LocalDateTime createdAt;
        private final List<SampleOrderLine> lines;
        public SampleOrder(String code, String customer, String status, LocalDateTime date, List<SampleOrderLine> lines) {
            this.orderCode = code; this.customerName = customer; this.status = status;
            this.createdAt = date; this.lines = new ArrayList<>(lines);
        }
        public String getOrderCode() { return orderCode; }
        public String getCustomerName() { return customerName; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SampleOrderLine> getLines() { return Collections.unmodifiableList(lines); }
        public double getSubtotal() { return lines.stream().mapToDouble(SampleOrderLine::getSubtotal).sum(); }
        public double getDeliveryFee() { return getSubtotal() >= 500 ? 0 : 40; }
        public double getTotal() { return getSubtotal() + getDeliveryFee(); }
        public int getItemCount() { return lines.stream().mapToInt(SampleOrderLine::getQuantity).sum(); }
    }

    public static List<FoodSample> foodCatalog() {
        return new ArrayList<>(Arrays.asList(
            new FoodSample(1,"Chicken Burger","Burger","Grilled chicken, lettuce and house sauce",180,25,false),
            new FoodSample(2,"Beef Burger","Burger","Beef patty with cheese and vegetables",220,20,false),
            new FoodSample(3,"Veg Burger","Burger","Vegetable patty and fresh salad",150,16,true),
            new FoodSample(4,"French Fries","Snacks","Golden crispy potato fries",100,40,true),
            new FoodSample(5,"Chicken Nuggets","Snacks","Six crispy chicken nuggets",160,22,false),
            new FoodSample(6,"Club Sandwich","Snacks","Three-layer sandwich",130,20,false),
            new FoodSample(7,"Cheese Pizza","Pizza","Classic cheese pizza",350,15,true),
            new FoodSample(8,"Chicken Pizza","Pizza","Chicken and mozzarella pizza",420,12,false),
            new FoodSample(9,"Pasta Alfredo","Pasta","Creamy white sauce pasta",250,18,true),
            new FoodSample(10,"Spicy Pasta","Pasta","Pasta with spicy tomato sauce",230,14,true),
            new FoodSample(11,"Hot Coffee","Drinks","Freshly brewed coffee",80,50,true),
            new FoodSample(12,"Cold Coffee","Drinks","Iced coffee with milk",120,30,true),
            new FoodSample(13,"Lemonade","Drinks","Fresh lemon drink",60,35,true),
            new FoodSample(14,"Chocolate Shake","Drinks","Chocolate milkshake",150,15,true),
            new FoodSample(15,"Chicken Roll","Snacks","Chicken wrapped in flatbread",120,28,false),
            new FoodSample(16,"Egg Roll","Snacks","Egg and vegetable roll",90,24,true),
            new FoodSample(17,"Fried Rice","Rice","Vegetable fried rice",200,19,true),
            new FoodSample(18,"Chicken Fried Rice","Rice","Fried rice with chicken",280,17,false),
            new FoodSample(19,"Mini Cake","Dessert","Soft vanilla cake",90,20,true),
            new FoodSample(20,"Brownie","Dessert","Chocolate brownie",110,18,true)
        ));
    }

    public static List<String> categories() {
        return foodCatalog().stream().map(FoodSample::getCategory).distinct().sorted().collect(Collectors.toList());
    }
    public static Optional<FoodSample> findFoodById(int id) {
        return foodCatalog().stream().filter(f -> f.getId() == id).findFirst();
    }
    public static List<FoodSample> searchFoods(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();
        return foodCatalog().stream().filter(f -> f.getName().toLowerCase().contains(query)
                || f.getCategory().toLowerCase().contains(query)
                || f.getDescription().toLowerCase().contains(query)).collect(Collectors.toList());
    }
    public static List<FoodSample> foodsByCategory(String category) {
        return foodCatalog().stream().filter(f -> f.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public static List<FoodSample> vegetarianFoods() {
        return foodCatalog().stream().filter(FoodSample::isVegetarian).collect(Collectors.toList());
    }
    public static List<FoodSample> availableFoods() {
        return foodCatalog().stream().filter(FoodSample::isInStock).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceAscending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice)).collect(Collectors.toList());
    }
    public static List<FoodSample> sortByPriceDescending() {
        return foodCatalog().stream().sorted(Comparator.comparingDouble(FoodSample::getPrice).reversed()).collect(Collectors.toList());
    }
    public static List<FoodSample> lowStockItems(int threshold) {
        return foodCatalog().stream().filter(f -> f.getStock() <= threshold).collect(Collectors.toList());
    }
    public static double averageMenuPrice() {
        return foodCatalog().stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static double categoryAveragePrice(String category) {
        return foodsByCategory(category).stream().mapToDouble(FoodSample::getPrice).average().orElse(0);
    }
    public static Map<String, Long> categoryCounts() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.counting()));
    }
    public static Map<String, Double> categoryPriceTotals() {
        return foodCatalog().stream().collect(Collectors.groupingBy(FoodSample::getCategory, LinkedHashMap::new, Collectors.summingDouble(FoodSample::getPrice)));
    }
    public static String catalogReport() {
        StringBuilder report = new StringBuilder("=== CANTEENPRO SAMPLE MENU ===\n");
        for (FoodSample food : foodCatalog()) report.append(food.displayLine()).append('\n');
        report.append("Categories: ").append(categories()).append('\n');
        report.append("Average price: Tk ").append(String.format("%.2f", averageMenuPrice())).append('\n');
        report.append("Total menu entries: ").append(foodCatalog().size());
        return report.toString();
    }

    public static List<SampleOrder> sampleOrders() {
        List<FoodSample> foods = foodCatalog();
        LocalDateTime now = LocalDateTime.now();
        List<SampleOrder> orders = new ArrayList<>();
        orders.add(new SampleOrder("CP-1001","Rahim","COMPLETED",now.minusDays(2),Arrays.asList(new SampleOrderLine(foods.get(0),2),new SampleOrderLine(foods.get(3),1))));
        orders.add(new SampleOrder("CP-1002","Karim","PROCESSING",now.minusHours(5),Arrays.asList(new SampleOrderLine(foods.get(6),1),new SampleOrderLine(foods.get(10),2))));
        orders.add(new SampleOrder("CP-1003","Nusrat","PENDING",now.minusHours(2),Arrays.asList(new SampleOrderLine(foods.get(8),1),new SampleOrderLine(foods.get(12),2))));
        orders.add(new SampleOrder("CP-1004","Sadia","CANCELLED",now.minusDays(1),Arrays.asList(new SampleOrderLine(foods.get(1),1))));
        orders.add(new SampleOrder("CP-1005","Hasan","COMPLETED",now.minusDays(3),Arrays.asList(new SampleOrderLine(foods.get(17),1),new SampleOrderLine(foods.get(18),2))));
        return orders;
    }
    public static List<SampleOrder> ordersByStatus(String status) {
        return sampleOrders().stream().filter(o -> o.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }
    public static List<SampleOrder> ordersOnDate(LocalDate date) {
        return sampleOrders().stream().filter(o -> o.getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public static double totalSampleRevenue() {
        return sampleOrders().stream().filter(o -> !"CANCELLED".equals(o.getStatus())).mapToDouble(SampleOrder::getTotal).sum();
    }
    public static Map<String, Double> revenueByStatus() {
        Map<String, Double> result = new HashMap<>();
        for (SampleOrder order : sampleOrders()) result.merge(order.getStatus(), order.getTotal(), Double::sum);
        return result;
    }
    public static String orderReport() {
        StringBuilder out = new StringBuilder("=== SAMPLE ORDER REPORT ===\n");
        for (SampleOrder order : sampleOrders()) {
            out.append(order.getOrderCode()).append(" | ").append(order.getCustomerName())
               .append(" | ").append(order.getStatus()).append(" | items=").append(order.getItemCount())
               .append(" | total Tk ").append(String.format("%.2f",order.getTotal())).append('\n');
        }
        out.append("Non-cancelled sample revenue: Tk ").append(String.format("%.2f",totalSampleRevenue()));
        return out.toString();
    }

    public static List<String> sampleCustomers() {
        return Arrays.asList("Rahim Uddin","Karim Ahmed","Nusrat Jahan","Sadia Akter","Hasan Mahmud",
                "Ayesha Rahman","Mehedi Hasan","Tania Islam","Sabbir Hossain","Farhana Chowdhury");
    }
    public static Map<String, String> sampleCustomerProfiles() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("Rahim Uddin", "rahim@example.test"); profiles.put("Karim Ahmed", "karim@example.test");
        profiles.put("Nusrat Jahan", "nusrat@example.test"); profiles.put("Sadia Akter", "sadia@example.test");
        profiles.put("Hasan Mahmud", "hasan@example.test"); profiles.put("Ayesha Rahman", "ayesha@example.test");
        profiles.put("Mehedi Hasan", "mehedi@example.test"); profiles.put("Tania Islam", "tania@example.test");
        profiles.put("Sabbir Hossain", "sabbir@example.test"); profiles.put("Farhana Chowdhury", "farhana@example.test");
        return profiles;
    }
    public static boolean isValidSampleEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@'); int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1 && !email.contains(" ");
    }
    public static boolean isValidQuantity(int quantity) { return quantity > 0 && quantity <= 99; }
    public static boolean isValidPrice(double price) { return Double.isFinite(price) && price >= 0; }
    public static double calculateDiscount(double total, double percentage) {
        if (total < 0 || percentage < 0 || percentage > 100) throw new IllegalArgumentException("Invalid discount inputs");
        return total * percentage / 100.0;
    }
    public static double applyDiscount(double total, double percentage) { return total - calculateDiscount(total, percentage); }
    public static double calculateDeliveryFee(double subtotal) { return subtotal >= 500 ? 0 : 40; }
    public static double calculateGrandTotal(double subtotal, double discountPercentage) {
        return applyDiscount(subtotal, discountPercentage) + calculateDeliveryFee(subtotal);
    }
    public static String generateDemoOrderCode(int sequence) {
        if (sequence < 1) throw new IllegalArgumentException("Sequence must be positive");
        return "CP-" + String.format("%04d", sequence);
    }
    public static String formatMoney(double amount) { return String.format("Tk %.2f", amount); }
    public static String formatDate(LocalDate date) { return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); }
    public static String formatTime(LocalTime time) { return time.format(DateTimeFormatter.ofPattern("hh:mm a")); }
    public static String formatTimestamp(LocalDateTime dateTime) { return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")); }
    public static List<String> menuTips() {
        return Arrays.asList("Keep food descriptions short and clear.","Update stock after each order.",
                "Review low-stock items every day.","Use consistent categories.","Keep menu prices easy to read.",
                "Mark unavailable items clearly.","Review order totals before checkout.");
    }
    public static String demoSummary() {
        return "Sample catalog contains " + foodCatalog().size() + " food entries, " + categories().size()
                + " categories and " + sampleOrders().size() + " orders. Average menu price: " + formatMoney(averageMenuPrice());
    }
}

