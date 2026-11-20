package com.canteenpro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class OrderItem {

    private Long id;

    @NotNull
    private Long foodId;

    @NotBlank
    @Column(length = 100)
    private String foodName;

    @Min(1)
    private int quantity;

    @Min(0)
    private double price;

    private Double originalPrice;

    private Double discountPrice;

    private Double discountPercent;

    private Double discountAmount;

    private Double subtotal;

    private Double totalPrice;

    private Double taxAmount;

    private Double taxPercent;

    private String image;

    private String imageUrl;

    private String thumbnailUrl;

    private String category;

    private String description;

    private String size;

    private String variant;

    private String spiceLevel;

    private String specialInstruction;

    private String note;

    private String customerNote;

    private String kitchenNote;

    private List<String> addOns;

    private List<String> removedIngredients;

    private List<String> extraToppings;

    private String packagingType;

    private Boolean isVeg;

    private Boolean isSpicy;

    private Boolean isGift;

    private String giftMessage;

    private Integer calories;

    private Integer preparationTime;

    private String status;

    private String orderStatus;

    private String sku;

    private String barcode;

    private String unit;

    private Double weight;

    private String brand;

    private String origin;

    private String ingredients;

    private String allergens;

    private String nutritionInfo;

    private String tags;

    private Long orderId;

    private Long userId;

    private String customerName;

    private String customerEmail;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime preparedAt;

    private LocalDateTime deliveredAt;

    private String preparedBy;

    private String deliveredBy;

    private Integer rating;

    private String review;

    private Boolean isReviewed;

    private Boolean isRefunded;

    private Double refundAmount;

    private String refundReason;

    private LocalDateTime refundedAt;

    private Boolean isCancelled;

    private String cancelReason;

    private LocalDateTime cancelledAt;

    private Integer sortOrder;

    private String itemCode;

    private String batchNo;

    public OrderItem() {
        this.quantity = 1;
        this.price = 0.0;
        this.originalPrice = 0.0;
        this.discountPrice = 0.0;
        this.discountPercent = 0.0;
        this.discountAmount = 0.0;
        this.subtotal = 0.0;
        this.totalPrice = 0.0;
        this.taxAmount = 0.0;
        this.taxPercent = 0.0;
        this.isVeg = false;
        this.isSpicy = false;
        this.isGift = false;
        this.isReviewed = false;
        this.isRefunded = false;
        this.isCancelled = false;
        this.calories = 0;
        this.preparationTime = 0;
        this.rating = 0;
        this.refundAmount = 0.0;
        this.sortOrder = 0;
        this.addOns = new ArrayList<>();
        this.removedIngredients = new ArrayList<>();
        this.extraToppings = new ArrayList<>();
        this.status = "PENDING";
        this.orderStatus = "PENDING";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public OrderItem(Long foodId, String foodName, int quantity, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.originalPrice = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.addOns = new ArrayList<>();
        this.removedIngredients = new ArrayList<>();
        this.extraToppings = new ArrayList<>();
    }

    public OrderItem(Long foodId, String foodName, int quantity, double price, String image, String category) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.originalPrice = price;
        this.image = image;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.addOns = new ArrayList<>();
        this.removedIngredients = new ArrayList<>();
        this.extraToppings = new ArrayList<>();
    }

    public OrderItem(Long id, Long foodId, String foodName, int quantity, double price, String size, String variant) {
        this.id = id;
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.originalPrice = price;
        this.size = size;
        this.variant = variant;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.addOns = new ArrayList<>();
        this.removedIngredients = new ArrayList<>();
        this.extraToppings = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getSubtotal() {
        return price * quantity;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotalPrice() {
        return getSubtotal() - (discountAmount != null ? discountAmount : 0.0) + (taxAmount != null ? taxAmount : 0.0);
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(Double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public String getKitchenNote() {
        return kitchenNote;
    }

    public void setKitchenNote(String kitchenNote) {
        this.kitchenNote = kitchenNote;
    }

    public List<String> getAddOns() {
        return addOns;
    }

    public void setAddOns(List<String> addOns) {
        this.addOns = addOns;
    }

    public List<String> getRemovedIngredients() {
        return removedIngredients;
    }

    public void setRemovedIngredients(List<String> removedIngredients) {
        this.removedIngredients = removedIngredients;
    }

    public List<String> getExtraToppings() {
        return extraToppings;
    }

    public void setExtraToppings(List<String> extraToppings) {
        this.extraToppings = extraToppings;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    public Boolean getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public Boolean getIsSpicy() {
        return isSpicy;
    }

    public void setIsSpicy(Boolean isSpicy) {
        this.isSpicy = isSpicy;
    }

    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public String getGiftMessage() {
        return giftMessage;
    }

    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getNutritionInfo() {
        return nutritionInfo;
    }

    public void setNutritionInfo(String nutritionInfo) {
        this.nutritionInfo = nutritionInfo;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getPreparedAt() {
        return preparedAt;
    }

    public void setPreparedAt(LocalDateTime preparedAt) {
        this.preparedAt = preparedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Boolean getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(Boolean isReviewed) {
        this.isReviewed = isReviewed;
    }

    public Boolean getIsRefunded() {
        return isRefunded;
    }

    public void setIsRefunded(Boolean isRefunded) {
        this.isRefunded = isRefunded;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public LocalDateTime getRefundedAt() {
        return refundedAt;
    }

    public void setRefundedAt(LocalDateTime refundedAt) {
        this.refundedAt = refundedAt;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Transient
    public double calculateSubtotal() {
        double base = price * quantity;
        double disc = discountAmount != null ? discountAmount : 0.0;
        this.subtotal = base - disc;
        if (this.subtotal < 0) {
            this.subtotal = 0.0;
        }
        return this.subtotal;
    }

    @Transient
    public double calculateTax() {
        double sub = calculateSubtotal();
        double percent = taxPercent != null ? taxPercent : 0.0;
        this.taxAmount = sub * percent / 100;
        return this.taxAmount;
    }

    @Transient
    public double calculateTotal() {
        double sub = calculateSubtotal();
        double tax = calculateTax();
        this.totalPrice = sub + tax;
        return this.totalPrice;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
        this.updatedAt = LocalDateTime.now();
        calculateSubtotal();
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
        if (this.quantity < 0) {
            this.quantity = 0;
        }
        this.updatedAt = LocalDateTime.now();
        calculateSubtotal();
    }

    public void applyDiscount(double percent) {
        this.discountPercent = percent;
        double base = price * quantity;
        this.discountAmount = base * percent / 100;
        this.discountPrice = price - (price * percent / 100);
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsPrepared(String by) {
        this.status = "PREPARED";
        this.preparedBy = by;
        this.preparedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsDelivered(String by) {
        this.status = "DELIVERED";
        this.deliveredBy = by;
        this.deliveredAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsCancelled(String reason) {
        this.isCancelled = true;
        this.status = "CANCELLED";
        this.cancelReason = reason;
        this.cancelledAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsRefunded(double amount, String reason) {
        this.isRefunded = true;
        this.refundAmount = amount;
        this.refundReason = reason;
        this.refundedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void addAddOn(String addOn) {
        if (this.addOns == null) {
            this.addOns = new ArrayList<>();
        }
        this.addOns.add(addOn);
    }

    public void removeAddOn(String addOn) {
        if (this.addOns != null) {
            this.addOns.remove(addOn);
        }
    }

    public void addTopping(String topping) {
        if (this.extraToppings == null) {
            this.extraToppings = new ArrayList<>();
        }
        this.extraToppings.add(topping);
    }
}