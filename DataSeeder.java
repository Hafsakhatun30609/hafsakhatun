package com.canteenpro.config;
import com.canteenpro.model.Food;import com.canteenpro.service.FoodService;import org.springframework.boot.CommandLineRunner;import org.springframework.stereotype.Component;
@Component public class DataSeeder implements CommandLineRunner {private final FoodService s;public DataSeeder(FoodService s){this.s=s;}public void run(String...args){if(!s.all().isEmpty())return;s.create(new Food(null,"Chicken Burger","Burger","Classic chicken burger",180,25,""));s.create(new Food(null,"Beef Burger","Burger","Beef patty burger",220,20,""));s.create(new Food(null,"French Fries","Snacks","Crispy potato fries",100,40,""));s.create(new Food(null,"Pizza","Fast Food","Cheese pizza",350,15,""));s.create(new Food(null,"Coffee","Drinks","Hot coffee",80,50,""));s.create(new Food(null,"Cold Coffee","Drinks","Chilled coffee",120,30,""));s.create(new Food(null,"Sandwich","Snacks","Fresh sandwich",130,20,""));s.create(new Food(null,"Pasta","Fast Food","Creamy pasta",250,18,""));}}


import com.canteenpro.model.Food;
import com.canteenpro.service.FoodService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final FoodService foodService;

    public DataSeeder(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void run(String... args) {
        if (!foodService.all().isEmpty()) {
            return;
        }
        seedFoods();
    }

    private void seedFoods() {
        List<Food> foods = new ArrayList<>();

        foods.add(new Food(null, "Chicken Burger", "Burger", "Classic chicken burger", 180, 25, ""));
        foods.add(new Food(null, "Beef Burger", "Burger", "Beef patty burger", 220, 20, ""));
        foods.add(new Food(null, "French Fries", "Snacks", "Crispy potato fries", 100, 40, ""));
        foods.add(new Food(null, "Pizza", "Fast Food", "Cheese pizza", 350, 15, ""));
        foods.add(new Food(null, "Coffee", "Drinks", "Hot coffee", 80, 50, ""));
        foods.add(new Food(null, "Cold Coffee", "Drinks", "Chilled coffee", 120, 30, ""));
        foods.add(new Food(null, "Sandwich", "Snacks", "Fresh sandwich", 130, 20, ""));
        foods.add(new Food(null, "Pasta", "Fast Food", "Creamy pasta", 250, 18, ""));
        foods.add(new Food(null, "Classic Chicken Burger", "Burger", "classic chicken prepared with fresh ingredients", 60, 10, ""));
        foods.add(new Food(null, "Spicy Pepper Drinks", "Drinks", "spicy pepper prepared with fresh ingredients", 97, 23, ""));
        foods.add(new Food(null, "Crispy Tuna Meals", "Meals", "crispy tuna prepared with fresh ingredients", 134, 36, ""));
        foods.add(new Food(null, "Cheesy Beef Fast Food", "Fast Food", "cheesy beef prepared with fresh ingredients", 171, 49, ""));
        foods.add(new Food(null, "Grilled Tomato Breakfast", "Breakfast", "grilled tomato prepared with fresh ingredients", 208, 62, ""));
        foods.add(new Food(null, "Fresh Bacon Snacks", "Snacks", "fresh bacon prepared with fresh ingredients", 245, 75, ""));
        foods.add(new Food(null, "Loaded Veggie Dessert", "Dessert", "loaded veggie prepared with fresh ingredients", 282, 88, ""));
        foods.add(new Food(null, "Special Onion Burger", "Burger", "special onion prepared with fresh ingredients", 319, 11, ""));
        foods.add(new Food(null, "Premium Corn Drinks", "Drinks", "premium corn prepared with fresh ingredients", 356, 24, ""));
        foods.add(new Food(null, "Homestyle Cheese Meals", "Meals", "homestyle cheese prepared with fresh ingredients", 393, 37, ""));
        foods.add(new Food(null, "Deluxe Potato Fast Food", "Fast Food", "deluxe potato prepared with fresh ingredients", 430, 50, ""));
        foods.add(new Food(null, "Smoky Spinach Breakfast", "Breakfast", "smoky spinach prepared with fresh ingredients", 467, 63, ""));
        foods.add(new Food(null, "Herb Mushroom Snacks", "Snacks", "herb mushroom prepared with fresh ingredients", 504, 76, ""));
        foods.add(new Food(null, "Golden Fish Dessert", "Dessert", "golden fish prepared with fresh ingredients", 541, 89, ""));
        foods.add(new Food(null, "Crunchy Olive Burger", "Burger", "crunchy olive prepared with fresh ingredients", 88, 12, ""));
        foods.add(new Food(null, "Creamy BBQ Drinks", "Drinks", "creamy bbq prepared with fresh ingredients", 125, 25, ""));
        foods.add(new Food(null, "Savory Egg Meals", "Meals", "savory egg prepared with fresh ingredients", 162, 38, ""));
        foods.add(new Food(null, "Zesty Lemon Fast Food", "Fast Food", "zesty lemon prepared with fresh ingredients", 199, 51, ""));
        foods.add(new Food(null, "Tasty Garlic Breakfast", "Breakfast", "tasty garlic prepared with fresh ingredients", 236, 64, ""));
        foods.add(new Food(null, "Signature Paneer Snacks", "Snacks", "signature paneer prepared with fresh ingredients", 273, 77, ""));
        foods.add(new Food(null, "Classic Chicken Dessert", "Dessert", "classic chicken prepared with fresh ingredients", 310, 90, ""));
        foods.add(new Food(null, "Spicy Pepper Burger", "Burger", "spicy pepper prepared with fresh ingredients", 347, 13, ""));
        foods.add(new Food(null, "Crispy Tuna Drinks", "Drinks", "crispy tuna prepared with fresh ingredients", 384, 26, ""));
        foods.add(new Food(null, "Cheesy Beef Meals", "Meals", "cheesy beef prepared with fresh ingredients", 421, 39, ""));
        foods.add(new Food(null, "Grilled Tomato Fast Food", "Fast Food", "grilled tomato prepared with fresh ingredients", 458, 52, ""));
        foods.add(new Food(null, "Fresh Bacon Breakfast", "Breakfast", "fresh bacon prepared with fresh ingredients", 495, 65, ""));
        foods.add(new Food(null, "Loaded Veggie Snacks", "Snacks", "loaded veggie prepared with fresh ingredients", 532, 78, ""));
        foods.add(new Food(null, "Special Onion Dessert", "Dessert", "special onion prepared with fresh ingredients", 79, 91, ""));
        foods.add(new Food(null, "Premium Corn Burger", "Burger", "premium corn prepared with fresh ingredients", 116, 14, ""));
        foods.add(new Food(null, "Homestyle Cheese Drinks", "Drinks", "homestyle cheese prepared with fresh ingredients", 153, 27, ""));
        foods.add(new Food(null, "Deluxe Potato Meals", "Meals", "deluxe potato prepared with fresh ingredients", 190, 40, ""));
        foods.add(new Food(null, "Smoky Spinach Fast Food", "Fast Food", "smoky spinach prepared with fresh ingredients", 227, 53, ""));
        foods.add(new Food(null, "Herb Mushroom Breakfast", "Breakfast", "herb mushroom prepared with fresh ingredients", 264, 66, ""));
        foods.add(new Food(null, "Golden Fish Snacks", "Snacks", "golden fish prepared with fresh ingredients", 301, 79, ""));
        foods.add(new Food(null, "Crunchy Olive Dessert", "Dessert", "crunchy olive prepared with fresh ingredients", 338, 92, ""));
        foods.add(new Food(null, "Creamy BBQ Burger", "Burger", "creamy bbq prepared with fresh ingredients", 375, 15, ""));
        foods.add(new Food(null, "Savory Egg Drinks", "Drinks", "savory egg prepared with fresh ingredients", 412, 28, ""));
        foods.add(new Food(null, "Zesty Lemon Meals", "Meals", "zesty lemon prepared with fresh ingredients", 449, 41, ""));
        foods.add(new Food(null, "Tasty Garlic Fast Food", "Fast Food", "tasty garlic prepared with fresh ingredients", 486, 54, ""));
        foods.add(new Food(null, "Signature Paneer Breakfast", "Breakfast", "signature paneer prepared with fresh ingredients", 523, 67, ""));
        foods.add(new Food(null, "Classic Chicken Snacks", "Snacks", "classic chicken prepared with fresh ingredients", 70, 80, ""));
        foods.add(new Food(null, "Spicy Pepper Dessert", "Dessert", "spicy pepper prepared with fresh ingredients", 107, 93, ""));
        foods.add(new Food(null, "Crispy Tuna Burger", "Burger", "crispy tuna prepared with fresh ingredients", 144, 16, ""));
        foods.add(new Food(null, "Cheesy Beef Drinks", "Drinks", "cheesy beef prepared with fresh ingredients", 181, 29, ""));
        foods.add(new Food(null, "Grilled Tomato Meals", "Meals", "grilled tomato prepared with fresh ingredients", 218, 42, ""));
        foods.add(new Food(null, "Fresh Bacon Fast Food", "Fast Food", "fresh bacon prepared with fresh ingredients", 255, 55, ""));
        foods.add(new Food(null, "Loaded Veggie Breakfast", "Breakfast", "loaded veggie prepared with fresh ingredients", 292, 68, ""));
        foods.add(new Food(null, "Special Onion Snacks", "Snacks", "special onion prepared with fresh ingredients", 329, 81, ""));
        foods.add(new Food(null, "Premium Corn Dessert", "Dessert", "premium corn prepared with fresh ingredients", 366, 94, ""));
        foods.add(new Food(null, "Homestyle Cheese Burger", "Burger", "homestyle cheese prepared with fresh ingredients", 403, 17, ""));
        foods.add(new Food(null, "Deluxe Potato Drinks", "Drinks", "deluxe potato prepared with fresh ingredients", 440, 30, ""));
        foods.add(new Food(null, "Smoky Spinach Meals", "Meals", "smoky spinach prepared with fresh ingredients", 477, 43, ""));
        foods.add(new Food(null, "Herb Mushroom Fast Food", "Fast Food", "herb mushroom prepared with fresh ingredients", 514, 56, ""));
        foods.add(new Food(null, "Golden Fish Breakfast", "Breakfast", "golden fish prepared with fresh ingredients", 61, 69, ""));
        foods.add(new Food(null, "Crunchy Olive Snacks", "Snacks", "crunchy olive prepared with fresh ingredients", 98, 82, ""));
        foods.add(new Food(null, "Creamy BBQ Dessert", "Dessert", "creamy bbq prepared with fresh ingredients", 135, 95, ""));
        foods.add(new Food(null, "Savory Egg Burger", "Burger", "savory egg prepared with fresh ingredients", 172, 18, ""));
        foods.add(new Food(null, "Zesty Lemon Drinks", "Drinks", "zesty lemon prepared with fresh ingredients", 209, 31, ""));
        foods.add(new Food(null, "Tasty Garlic Meals", "Meals", "tasty garlic prepared with fresh ingredients", 246, 44, ""));
        foods.add(new Food(null, "Signature Paneer Fast Food", "Fast Food", "signature paneer prepared with fresh ingredients", 283, 57, ""));


        package com.canteenpro.config;

import com.canteenpro.model.Food;
import com.canteenpro.service.FoodService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final FoodService foodService;

    public DataSeeder(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void run(String... args) {
        if (!foodService.all().isEmpty()) {
            return;
        }
        seedFoods();
    }

    private void seedFoods() {
        List<Food> foods = new ArrayList<>();

        foods.add(new Food(null, "Chicken Burger", "Burger", "Classic chicken burger", 180, 25, ""));
        foods.add(new Food(null, "Beef Burger", "Burger", "Beef patty burger", 220, 20, ""));
        foods.add(new Food(null, "French Fries", "Snacks", "Crispy potato fries", 100, 40, ""));
        foods.add(new Food(null, "Pizza", "Fast Food", "Cheese pizza", 350, 15, ""));
        foods.add(new Food(null, "Coffee", "Drinks", "Hot coffee", 80, 50, ""));
        foods.add(new Food(null, "Cold Coffee", "Drinks", "Chilled coffee", 120, 30, ""));
        foods.add(new Food(null, "Sandwich", "Snacks", "Fresh sandwich", 130, 20, ""));
        foods.add(new Food(null, "Pasta", "Fast Food", "Creamy pasta", 250, 18, ""));
        foods.add(new Food(null, "Classic Chicken Burger", "Burger", "classic chicken prepared with fresh ingredients", 60, 10, ""));
        foods.add(new Food(null, "Spicy Pepper Drinks", "Drinks", "spicy pepper prepared with fresh ingredients", 97, 23, ""));
        foods.add(new Food(null, "Crispy Tuna Meals", "Meals", "crispy tuna prepared with fresh ingredients", 134, 36, ""));
        foods.add(new Food(null, "Cheesy Beef Fast Food", "Fast Food", "cheesy beef prepared with fresh ingredients", 171, 49, ""));
        foods.add(new Food(null, "Grilled Tomato Breakfast", "Breakfast", "grilled tomato prepared with fresh ingredients", 208, 62, ""));
        foods.add(new Food(null, "Fresh Bacon Snacks", "Snacks", "fresh bacon prepared with fresh ingredients", 245, 75, ""));
        foods.add(new Food(null, "Loaded Veggie Dessert", "Dessert", "loaded veggie prepared with fresh ingredients", 282, 88, ""));
        foods.add(new Food(null, "Special Onion Burger", "Burger", "special onion prepared with fresh ingredients", 319, 11, ""));
        foods.add(new Food(null, "Premium Corn Drinks", "Drinks", "premium corn prepared with fresh ingredients", 356, 24, ""));
        foods.add(new Food(null, "Homestyle Cheese Meals", "Meals", "homestyle cheese prepared with fresh ingredients", 393, 37, ""));
        foods.add(new Food(null, "Deluxe Potato Fast Food", "Fast Food", "deluxe potato prepared with fresh ingredients", 430, 50, ""));
        foods.add(new Food(null, "Smoky Spinach Breakfast", "Breakfast", "smoky spinach prepared with fresh ingredients", 467, 63, ""));
        foods.add(new Food(null, "Herb Mushroom Snacks", "Snacks", "herb mushroom prepared with fresh ingredients", 504, 76, ""));
        foods.add(new Food(null, "Golden Fish Dessert", "Dessert", "golden fish prepared with fresh ingredients", 541, 89, ""));
        foods.add(new Food(null, "Crunchy Olive Burger", "Burger", "crunchy olive prepared with fresh ingredients", 88, 12, ""));
        foods.add(new Food(null, "Creamy BBQ Drinks", "Drinks", "creamy bbq prepared with fresh ingredients", 125, 25, ""));
        foods.add(new Food(null, "Savory Egg Meals", "Meals", "savory egg prepared with fresh ingredients", 162, 38, ""));
        foods.add(new Food(null, "Zesty Lemon Fast Food", "Fast Food", "zesty lemon prepared with fresh ingredients", 199, 51, ""));
        foods.add(new Food(null, "Tasty Garlic Breakfast", "Breakfast", "tasty garlic prepared with fresh ingredients", 236, 64, ""));
        foods.add(new Food(null, "Signature Paneer Snacks", "Snacks", "signature paneer prepared with fresh ingredients", 273, 77, ""));
        foods.add(new Food(null, "Classic Chicken Dessert", "Dessert", "classic chicken prepared with fresh ingredients", 310, 90, ""));
        foods.add(new Food(null, "Spicy Pepper Burger", "Burger", "spicy pepper prepared with fresh ingredients", 347, 13, ""));
        foods.add(new Food(null, "Crispy Tuna Drinks", "Drinks", "crispy tuna prepared with fresh ingredients", 384, 26, ""));
        foods.add(new Food(null, "Cheesy Beef Meals", "Meals", "cheesy beef prepared with fresh ingredients", 421, 39, ""));
        foods.add(new Food(null, "Grilled Tomato Fast Food", "Fast Food", "grilled tomato prepared with fresh ingredients", 458, 52, ""));
        foods.add(new Food(null, "Fresh Bacon Breakfast", "Breakfast", "fresh bacon prepared with fresh ingredients", 495, 65, ""));
        foods.add(new Food(null, "Loaded Veggie Snacks", "Snacks", "loaded veggie prepared with fresh ingredients", 532, 78, ""));
        foods.add(new Food(null, "Special Onion Dessert", "Dessert", "special onion prepared with fresh ingredients", 79, 91, ""));
        foods.add(new Food(null, "Premium Corn Burger", "Burger", "premium corn prepared with fresh ingredients", 116, 14, ""));
        foods.add(new Food(null, "Homestyle Cheese Drinks", "Drinks", "homestyle cheese prepared with fresh ingredients", 153, 27, ""));
        foods.add(new Food(null, "Deluxe Potato Meals", "Meals", "deluxe potato prepared with fresh ingredients", 190, 40, ""));
        foods.add(new Food(null, "Smoky Spinach Fast Food", "Fast Food", "smoky spinach prepared with fresh ingredients", 227, 53, ""));
        foods.add(new Food(null, "Herb Mushroom Breakfast", "Breakfast", "herb mushroom prepared with fresh ingredients", 264, 66, ""));
        foods.add(new Food(null, "Golden Fish Snacks", "Snacks", "golden fish prepared with fresh ingredients", 301, 79, ""));
        foods.add(new Food(null, "Crunchy Olive Dessert", "Dessert", "crunchy olive prepared with fresh ingredients", 338, 92, ""));
        foods.add(new Food(null, "Creamy BBQ Burger", "Burger", "creamy bbq prepared with fresh ingredients", 375, 15, ""));
        foods.add(new Food(null, "Savory Egg Drinks", "Drinks", "savory egg prepared with fresh ingredients", 412, 28, ""));
        foods.add(new Food(null, "Zesty Lemon Meals", "Meals", "zesty lemon prepared with fresh ingredients", 449, 41, ""));
        foods.add(new Food(null, "Tasty Garlic Fast Food", "Fast Food", "tasty garlic prepared with fresh ingredients", 486, 54, ""));
        foods.add(new Food(null, "Signature Paneer Breakfast", "Breakfast", "signature paneer prepared with fresh ingredients", 523, 67, ""));
        foods.add(new Food(null, "Classic Chicken Snacks", "Snacks", "classic chicken prepared with fresh ingredients", 70, 80, ""));
        foods.add(new Food(null, "Spicy Pepper Dessert", "Dessert", "spicy pepper prepared with fresh ingredients", 107, 93, ""));
        foods.add(new Food(null, "Crispy Tuna Burger", "Burger", "crispy tuna prepared with fresh ingredients", 144, 16, ""));
        foods.add(new Food(null, "Cheesy Beef Drinks", "Drinks", "cheesy beef prepared with fresh ingredients", 181, 29, ""));
        foods.add(new Food(null, "Grilled Tomato Meals", "Meals", "grilled tomato prepared with fresh ingredients", 218, 42, ""));
        foods.add(new Food(null, "Fresh Bacon Fast Food", "Fast Food", "fresh bacon prepared with fresh ingredients", 255, 55, ""));
        foods.add(new Food(null, "Loaded Veggie Breakfast", "Breakfast", "loaded veggie prepared with fresh ingredients", 292, 68, ""));
        foods.add(new Food(null, "Special Onion Snacks", "Snacks", "special onion prepared with fresh ingredients", 329, 81, ""));
        foods.add(new Food(null, "Premium Corn Dessert", "Dessert", "premium corn prepared with fresh ingredients", 366, 94, ""));
        foods.add(new Food(null, "Homestyle Cheese Burger", "Burger", "homestyle cheese prepared with fresh ingredients", 403, 17, ""));
        foods.add(new Food(null, "Deluxe Potato Drinks", "Drinks", "deluxe potato prepared with fresh ingredients", 440, 30, ""));
        foods.add(new Food(null, "Smoky Spinach Meals", "Meals", "smoky spinach prepared with fresh ingredients", 477, 43, ""));
        foods.add(new Food(null, "Herb Mushroom Fast Food", "Fast Food", "herb mushroom prepared with fresh ingredients", 514, 56, ""));
        foods.add(new Food(null, "Golden Fish Breakfast", "Breakfast", "golden fish prepared with fresh ingredients", 61, 69, ""));
        foods.add(new Food(null, "Crunchy Olive Snacks", "Snacks", "crunchy olive prepared with fresh ingredients", 98, 82, ""));
        foods.add(new Food(null, "Creamy BBQ Dessert", "Dessert", "creamy bbq prepared with fresh ingredients", 135, 95, ""));
        foods.add(new Food(null, "Savory Egg Burger", "Burger", "savory egg prepared with fresh ingredients", 172, 18, ""));
        foods.add(new Food(null, "Zesty Lemon Drinks", "Drinks", "zesty lemon prepared with fresh ingredients", 209, 31, ""));
        foods.add(new Food(null, "Tasty Garlic Meals", "Meals", "tasty garlic prepared with fresh ingredients", 246, 44, ""));
        foods.add(new Food(null, "Signature Paneer Fast Food", "Fast Food", "signature paneer prepared with fresh ingredients", 283, 57, ""));
        package com.canteenpro.config;

import com.canteenpro.model.Food;
import com.canteenpro.service.FoodService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final FoodService foodService;

    public DataSeeder(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void run(String... args) {
        if (!foodService.all().isEmpty()) {
            return;
        }
        seedFoods();
    }

    private void seedFoods() {
        List<Food> foods = new ArrayList<>();

        foods.add(new Food(null, "Chicken Burger", "Burger", "Classic chicken burger", 180, 25, ""));
        foods.add(new Food(null, "Beef Burger", "Burger", "Beef patty burger", 220, 20, ""));
        foods.add(new Food(null, "French Fries", "Snacks", "Crispy potato fries", 100, 40, ""));
        foods.add(new Food(null, "Pizza", "Fast Food", "Cheese pizza", 350, 15, ""));
        foods.add(new Food(null, "Coffee", "Drinks", "Hot coffee", 80, 50, ""));
        foods.add(new Food(null, "Cold Coffee", "Drinks", "Chilled coffee", 120, 30, ""));
        foods.add(new Food(null, "Sandwich", "Snacks", "Fresh sandwich", 130, 20, ""));
        foods.add(new Food(null, "Pasta", "Fast Food", "Creamy pasta", 250, 18, ""));
        foods.add(new Food(null, "Classic Chicken Burger", "Burger", "classic chicken prepared with fresh ingredients", 60, 10, ""));
        foods.add(new Food(null, "Spicy Pepper Drinks", "Drinks", "spicy pepper prepared with fresh ingredients", 97, 23, ""));
        foods.add(new Food(null, "Crispy Tuna Meals", "Meals", "crispy tuna prepared with fresh ingredients", 134, 36, ""));
        foods.add(new Food(null, "Cheesy Beef Fast Food", "Fast Food", "cheesy beef prepared with fresh ingredients", 171, 49, ""));
        foods.add(new Food(null, "Grilled Tomato Breakfast", "Breakfast", "grilled tomato prepared with fresh ingredients", 208, 62, ""));
        foods.add(new Food(null, "Fresh Bacon Snacks", "Snacks", "fresh bacon prepared with fresh ingredients", 245, 75, ""));
        foods.add(new Food(null, "Loaded Veggie Dessert", "Dessert", "loaded veggie prepared with fresh ingredients", 282, 88, ""));
        foods.add(new Food(null, "Special Onion Burger", "Burger", "special onion prepared with fresh ingredients", 319, 11, ""));
        foods.add(new Food(null, "Premium Corn Drinks", "Drinks", "premium corn prepared with fresh ingredients", 356, 24, ""));
        foods.add(new Food(null, "Homestyle Cheese Meals", "Meals", "homestyle cheese prepared with fresh ingredients", 393, 37, ""));
        foods.add(new Food(null, "Deluxe Potato Fast Food", "Fast Food", "deluxe potato prepared with fresh ingredients", 430, 50, ""));
        foods.add(new Food(null, "Smoky Spinach Breakfast", "Breakfast", "smoky spinach prepared with fresh ingredients", 467, 63, ""));
        foods.add(new Food(null, "Herb Mushroom Snacks", "Snacks", "herb mushroom prepared with fresh ingredients", 504, 76, ""));
        foods.add(new Food(null, "Golden Fish Dessert", "Dessert", "golden fish prepared with fresh ingredients", 541, 89, ""));
        foods.add(new Food(null, "Crunchy Olive Burger", "Burger", "crunchy olive prepared with fresh ingredients", 88, 12, ""));
        foods.add(new Food(null, "Creamy BBQ Drinks", "Drinks", "creamy bbq prepared with fresh ingredients", 125, 25, ""));
        foods.add(new Food(null, "Savory Egg Meals", "Meals", "savory egg prepared with fresh ingredients", 162, 38, ""));
        foods.add(new Food(null, "Zesty Lemon Fast Food", "Fast Food", "zesty lemon prepared with fresh ingredients", 199, 51, ""));
        foods.add(new Food(null, "Tasty Garlic Breakfast", "Breakfast", "tasty garlic prepared with fresh ingredients", 236, 64, ""));
        foods.add(new Food(null, "Signature Paneer Snacks", "Snacks", "signature paneer prepared with fresh ingredients", 273, 77, ""));
        foods.add(new Food(null, "Classic Chicken Dessert", "Dessert", "classic chicken prepared with fresh ingredients", 310, 90, ""));
        foods.add(new Food(null, "Spicy Pepper Burger", "Burger", "spicy pepper prepared with fresh ingredients", 347, 13, ""));
        foods.add(new Food(null, "Crispy Tuna Drinks", "Drinks", "crispy tuna prepared with fresh ingredients", 384, 26, ""));
        foods.add(new Food(null, "Cheesy Beef Meals", "Meals", "cheesy beef prepared with fresh ingredients", 421, 39, ""));
        foods.add(new Food(null, "Grilled Tomato Fast Food", "Fast Food", "grilled tomato prepared with fresh ingredients", 458, 52, ""));
        foods.add(new Food(null, "Fresh Bacon Breakfast", "Breakfast", "fresh bacon prepared with fresh ingredients", 495, 65, ""));
        foods.add(new Food(null, "Loaded Veggie Snacks", "Snacks", "loaded veggie prepared with fresh ingredients", 532, 78, ""));
        foods.add(new Food(null, "Special Onion Dessert", "Dessert", "special onion prepared with fresh ingredients", 79, 91, ""));
        foods.add(new Food(null, "Premium Corn Burger", "Burger", "premium corn prepared with fresh ingredients", 116, 14, ""));
        foods.add(new Food(null, "Homestyle Cheese Drinks", "Drinks", "homestyle cheese prepared with fresh ingredients", 153, 27, ""));
        foods.add(new Food(null, "Deluxe Potato Meals", "Meals", "deluxe potato prepared with fresh ingredients", 190, 40, ""));
        foods.add(new Food(null, "Smoky Spinach Fast Food", "Fast Food", "smoky spinach prepared with fresh ingredients", 227, 53, ""));
        foods.add(new Food(null, "Herb Mushroom Breakfast", "Breakfast", "herb mushroom prepared with fresh ingredients", 264, 66, ""));
        foods.add(new Food(null, "Golden Fish Snacks", "Snacks", "golden fish prepared with fresh ingredients", 301, 79, ""));
        foods.add(new Food(null, "Crunchy Olive Dessert", "Dessert", "crunchy olive prepared with fresh ingredients", 338, 92, ""));
        foods.add(new Food(null, "Creamy BBQ Burger", "Burger", "creamy bbq prepared with fresh ingredients", 375, 15, ""));
        foods.add(new Food(null, "Savory Egg Drinks", "Drinks", "savory egg prepared with fresh ingredients", 412, 28, ""));
        foods.add(new Food(null, "Zesty Lemon Meals", "Meals", "zesty lemon prepared with fresh ingredients", 449, 41, ""));
        foods.add(new Food(null, "Tasty Garlic Fast Food", "Fast Food", "tasty garlic prepared with fresh ingredients", 486, 54, ""));
        foods.add(new Food(null, "Signature Paneer Breakfast", "Breakfast", "signature paneer prepared with fresh ingredients", 523, 67, ""));
        foods.add(new Food(null, "Classic Chicken Snacks", "Snacks", "classic chicken prepared with fresh ingredients", 70, 80, ""));
        foods.add(new Food(null, "Spicy Pepper Dessert", "Dessert", "spicy pepper prepared with fresh ingredients", 107, 93, ""));
        foods.add(new Food(null, "Crispy Tuna Burger", "Burger", "crispy tuna prepared with fresh ingredients", 144, 16, ""));
        foods.add(new Food(null, "Cheesy Beef Drinks", "Drinks", "cheesy beef prepared with fresh ingredients", 181, 29, ""));
        foods.add(new Food(null, "Grilled Tomato Meals", "Meals", "grilled tomato prepared with fresh ingredients", 218, 42, ""));
        foods.add(new Food(null, "Fresh Bacon Fast Food", "Fast Food", "fresh bacon prepared with fresh ingredients", 255, 55, ""));
        foods.add(new Food(null, "Loaded Veggie Breakfast", "Breakfast", "loaded veggie prepared with fresh ingredients", 292, 68, ""));
        foods.add(new Food(null, "Special Onion Snacks", "Snacks", "special onion prepared with fresh ingredients", 329, 81, ""));
        foods.add(new Food(null, "Premium Corn Dessert", "Dessert", "premium corn prepared with fresh ingredients", 366, 94, ""));
        foods.add(new Food(null, "Homestyle Cheese Burger", "Burger", "homestyle cheese prepared with fresh ingredients", 403, 17, ""));
        foods.add(new Food(null, "Deluxe Potato Drinks", "Drinks", "deluxe potato prepared with fresh ingredients", 440, 30, ""));
        foods.add(new Food(null, "Smoky Spinach Meals", "Meals", "smoky spinach prepared with fresh ingredients", 477, 43, ""));
        foods.add(new Food(null, "Herb Mushroom Fast Food", "Fast Food", "herb mushroom prepared with fresh ingredients", 514, 56, ""));
        foods.add(new Food(null, "Golden Fish Breakfast", "Breakfast", "golden fish prepared with fresh ingredients", 61, 69, ""));
        foods.add(new Food(null, "Crunchy Olive Snacks", "Snacks", "crunchy olive prepared with fresh ingredients", 98, 82, ""));
        foods.add(new Food(null, "Creamy BBQ Dessert", "Dessert", "creamy bbq prepared with fresh ingredients", 135, 95, ""));
        foods.add(new Food(null, "Savory Egg Burger", "Burger", "savory egg prepared with fresh ingredients", 172, 18, ""));
        foods.add(new Food(null, "Zesty Lemon Drinks", "Drinks", "zesty lemon prepared with fresh ingredients", 209, 31, ""));
        foods.add(new Food(null, "Tasty Garlic Meals", "Meals", "tasty garlic prepared with fresh ingredients", 246, 44, ""));
        foods.add(new Food(null, "Signature Paneer Fast Food", "Fast Food", "signature paneer prepared with fresh ingredients", 283, 57, ""));
        package com.canteenpro.config;

import com.canteenpro.model.Food;
import com.canteenpro.service.FoodService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final FoodService foodService;

    public DataSeeder(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void run(String... args) {
        if (!foodService.all().isEmpty()) {
            return;
        }
        seedFoods();
    }

    private void seedFoods() {
        List<Food> foods = new ArrayList<>();

        foods.add(new Food(null, "Chicken Burger", "Burger", "Classic chicken burger", 180, 25, ""));
        foods.add(new Food(null, "Beef Burger", "Burger", "Beef patty burger", 220, 20, ""));
        foods.add(new Food(null, "French Fries", "Snacks", "Crispy potato fries", 100, 40, ""));
        foods.add(new Food(null, "Pizza", "Fast Food", "Cheese pizza", 350, 15, ""));
        foods.add(new Food(null, "Coffee", "Drinks", "Hot coffee", 80, 50, ""));
        foods.add(new Food(null, "Cold Coffee", "Drinks", "Chilled coffee", 120, 30, ""));
        foods.add(new Food(null, "Sandwich", "Snacks", "Fresh sandwich", 130, 20, ""));
        foods.add(new Food(null, "Pasta", "Fast Food", "Creamy pasta", 250, 18, ""));
        foods.add(new Food(null, "Classic Chicken Burger", "Burger", "classic chicken prepared with fresh ingredients", 60, 10, ""));
        foods.add(new Food(null, "Spicy Pepper Drinks", "Drinks", "spicy pepper prepared with fresh ingredients", 97, 23, ""));
        foods.add(new Food(null, "Crispy Tuna Meals", "Meals", "crispy tuna prepared with fresh ingredients", 134, 36, ""));
        foods.add(new Food(null, "Cheesy Beef Fast Food", "Fast Food", "cheesy beef prepared with fresh ingredients", 171, 49, ""));
        foods.add(new Food(null, "Grilled Tomato Breakfast", "Breakfast", "grilled tomato prepared with fresh ingredients", 208, 62, ""));
        foods.add(new Food(null, "Fresh Bacon Snacks", "Snacks", "fresh bacon prepared with fresh ingredients", 245, 75, ""));
        foods.add(new Food(null, "Loaded Veggie Dessert", "Dessert", "loaded veggie prepared with fresh ingredients", 282, 88, ""));
        foods.add(new Food(null, "Special Onion Burger", "Burger", "special onion prepared with fresh ingredients", 319, 11, ""));
        foods.add(new Food(null, "Premium Corn Drinks", "Drinks", "premium corn prepared with fresh ingredients", 356, 24, ""));
        foods.add(new Food(null, "Homestyle Cheese Meals", "Meals", "homestyle cheese prepared with fresh ingredients", 393, 37, ""));
        foods.add(new Food(null, "Deluxe Potato Fast Food", "Fast Food", "deluxe potato prepared with fresh ingredients", 430, 50, ""));
        foods.add(new Food(null, "Smoky Spinach Breakfast", "Breakfast", "smoky spinach prepared with fresh ingredients", 467, 63, ""));
        foods.add(new Food(null, "Herb Mushroom Snacks", "Snacks", "herb mushroom prepared with fresh ingredients", 504, 76, ""));
        foods.add(new Food(null, "Golden Fish Dessert", "Dessert", "golden fish prepared with fresh ingredients", 541, 89, ""));
        foods.add(new Food(null, "Crunchy Olive Burger", "Burger", "crunchy olive prepared with fresh ingredients", 88, 12, ""));
        foods.add(new Food(null, "Creamy BBQ Drinks", "Drinks", "creamy bbq prepared with fresh ingredients", 125, 25, ""));
        foods.add(new Food(null, "Savory Egg Meals", "Meals", "savory egg prepared with fresh ingredients", 162, 38, ""));
        foods.add(new Food(null, "Zesty Lemon Fast Food", "Fast Food", "zesty lemon prepared with fresh ingredients", 199, 51, ""));
        foods.add(new Food(null, "Tasty Garlic Breakfast", "Breakfast", "tasty garlic prepared with fresh ingredients", 236, 64, ""));
        foods.add(new Food(null, "Signature Paneer Snacks", "Snacks", "signature paneer prepared with fresh ingredients", 273, 77, ""));
        foods.add(new Food(null, "Classic Chicken Dessert", "Dessert", "classic chicken prepared with fresh ingredients", 310, 90, ""));
        foods.add(new Food(null, "Spicy Pepper Burger", "Burger", "spicy pepper prepared with fresh ingredients", 347, 13, ""));
        foods.add(new Food(null, "Crispy Tuna Drinks", "Drinks", "crispy tuna prepared with fresh ingredients", 384, 26, ""));
        foods.add(new Food(null, "Cheesy Beef Meals", "Meals", "cheesy beef prepared with fresh ingredients", 421, 39, ""));
        foods.add(new Food(null, "Grilled Tomato Fast Food", "Fast Food", "grilled tomato prepared with fresh ingredients", 458, 52, ""));
        foods.add(new Food(null, "Fresh Bacon Breakfast", "Breakfast", "fresh bacon prepared with fresh ingredients", 495, 65, ""));
        foods.add(new Food(null, "Loaded Veggie Snacks", "Snacks", "loaded veggie prepared with fresh ingredients", 532, 78, ""));
        foods.add(new Food(null, "Special Onion Dessert", "Dessert", "special onion prepared with fresh ingredients", 79, 91, ""));
        foods.add(new Food(null, "Premium Corn Burger", "Burger", "premium corn prepared with fresh ingredients", 116, 14, ""));
        foods.add(new Food(null, "Homestyle Cheese Drinks", "Drinks", "homestyle cheese prepared with fresh ingredients", 153, 27, ""));
        foods.add(new Food(null, "Deluxe Potato Meals", "Meals", "deluxe potato prepared with fresh ingredients", 190, 40, ""));
        foods.add(new Food(null, "Smoky Spinach Fast Food", "Fast Food", "smoky spinach prepared with fresh ingredients", 227, 53, ""));
        foods.add(new Food(null, "Herb Mushroom Breakfast", "Breakfast", "herb mushroom prepared with fresh ingredients", 264, 66, ""));
        foods.add(new Food(null, "Golden Fish Snacks", "Snacks", "golden fish prepared with fresh ingredients", 301, 79, ""));
        foods.add(new Food(null, "Crunchy Olive Dessert", "Dessert", "crunchy olive prepared with fresh ingredients", 338, 92, ""));
        foods.add(new Food(null, "Creamy BBQ Burger", "Burger", "creamy bbq prepared with fresh ingredients", 375, 15, ""));
        foods.add(new Food(null, "Savory Egg Drinks", "Drinks", "savory egg prepared with fresh ingredients", 412, 28, ""));
        foods.add(new Food(null, "Zesty Lemon Meals", "Meals", "zesty lemon prepared with fresh ingredients", 449, 41, ""));
        foods.add(new Food(null, "Tasty Garlic Fast Food", "Fast Food", "tasty garlic prepared with fresh ingredients", 486, 54, ""));
        foods.add(new Food(null, "Signature Paneer Breakfast", "Breakfast", "signature paneer prepared with fresh ingredients", 523, 67, ""));
        foods.add(new Food(null, "Classic Chicken Snacks", "Snacks", "classic chicken prepared with fresh ingredients", 70, 80, ""));
        foods.add(new Food(null, "Spicy Pepper Dessert", "Dessert", "spicy pepper prepared with fresh ingredients", 107, 93, ""));
        foods.add(new Food(null, "Crispy Tuna Burger", "Burger", "crispy tuna prepared with fresh ingredients", 144, 16, ""));
        foods.add(new Food(null, "Cheesy Beef Drinks", "Drinks", "cheesy beef prepared with fresh ingredients", 181, 29, ""));
        foods.add(new Food(null, "Grilled Tomato Meals", "Meals", "grilled tomato prepared with fresh ingredients", 218, 42, ""));
        foods.add(new Food(null, "Fresh Bacon Fast Food", "Fast Food", "fresh bacon prepared with fresh ingredients", 255, 55, ""));
        foods.add(new Food(null, "Loaded Veggie Breakfast", "Breakfast", "loaded veggie prepared with fresh ingredients", 292, 68, ""));
        foods.add(new Food(null, "Special Onion Snacks", "Snacks", "special onion prepared with fresh ingredients", 329, 81, ""));
        foods.add(new Food(null, "Premium Corn Dessert", "Dessert", "premium corn prepared with fresh ingredients", 366, 94, ""));
        foods.add(new Food(null, "Homestyle Cheese Burger", "Burger", "homestyle cheese prepared with fresh ingredients", 403, 17, ""));
        foods.add(new Food(null, "Deluxe Potato Drinks", "Drinks", "deluxe potato prepared with fresh ingredients", 440, 30, ""));
        foods.add(new Food(null, "Smoky Spinach Meals", "Meals", "smoky spinach prepared with fresh ingredients", 477, 43, ""));
        foods.add(new Food(null, "Herb Mushroom Fast Food", "Fast Food", "herb mushroom prepared with fresh ingredients", 514, 56, ""));
        foods.add(new Food(null, "Golden Fish Breakfast", "Breakfast", "golden fish prepared with fresh ingredients", 61, 69, ""));
        foods.add(new Food(null, "Crunchy Olive Snacks", "Snacks", "crunchy olive prepared with fresh ingredients", 98, 82, ""));
        foods.add(new Food(null, "Creamy BBQ Dessert", "Dessert", "creamy bbq prepared with fresh ingredients", 135, 95, ""));
        foods.add(new Food(null, "Savory Egg Burger", "Burger", "savory egg prepared with fresh ingredients", 172, 18, ""));
        foods.add(new Food(null, "Zesty Lemon Drinks", "Drinks", "zesty lemon prepared with fresh ingredients", 209, 31, ""));
        foods.add(new Food(null, "Tasty Garlic Meals", "Meals", "tasty garlic prepared with fresh ingredients", 246, 44, ""));
        foods.add(new Food(null, "Signature Paneer Fast Food", "Fast Food", "signature paneer prepared with fresh ingredients", 283, 57, ""));