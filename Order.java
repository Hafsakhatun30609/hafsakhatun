package com.canteenpro.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Order - Complete Order Model for CanteenPro
 *
 * Features:
 * 1. Customer info
 * 2. Items + calculations (subtotal, tax, discount, delivery, total)
 * 3. Status workflow: PENDING -> CONFIRMED -> PREPARING -> READY -> DELIVERED / CANCELLED
 * 4. Payment: CASH, CARD, BKASH, NAGAD, ROCKET
 * 5. Delivery / Pickup, Table, Token, Notes, Coupon
 * 6. Timestamps, Audit, Helpers, Builder, toMap
 *
 * Version: 2.0.0
 */
public class Order {

    // ============================================================
    // CONSTANTS
    // ============================================================

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_PREPARING = "PREPARING";
    public static final String STATUS_READY = "READY";
    public static final String STATUS_DELIVERED = "DELIVERED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_REFUNDED = "REFUNDED";
    public static final String STATUS_ON_HOLD = "ON_HOLD";

    public static final String PAYMENT_CASH = "CASH";
    public static final String PAYMENT_CARD = "CARD";
    public static final String PAYMENT_BKASH = "BKASH";
    public static final String PAYMENT_NAGAD = "NAGAD";
    public static final String PAYMENT_ROCKET = "ROCKET";
    public static final String PAYMENT_ONLINE = "ONLINE";

    public static final String PAYMENT_UNPAID = "UNPAID";
    public static final String PAYMENT_PAID = "PAID";
    public static final String PAYMENT_PARTIAL = "PARTIAL";
    public static final String PAYMENT_REFUNDED = "REFUNDED";
    public static final String PAYMENT_FAILED = "FAILED";

    public static final String TYPE_DINE_IN = "DINE_IN";
    public static final String TYPE_TAKEAWAY = "TAKEAWAY";
    public static final String TYPE_DELIVERY = "DELIVERY";

    public static final double DEFAULT_TAX_RATE = 0.05;
    public static final double DEFAULT_DELIVERY_FEE = 30.0;
    public static final double FREE_DELIVERY_MIN = 500.0;

    // ============================================================
    // CORE FIELDS - ORIGINAL KEPT
    // ============================================================

    private Long id;
    private Long userId;

    private String customerName;
    private String email;
    private String phone;
    private String address;

    private String status = "PENDING";
    private String paymentMethod = "CASH";
    private String paymentStatus = "UNPAID";

    private List<OrderItem> items = new ArrayList<>();
    private double total;
    private LocalDateTime createdAt = LocalDateTime.now();

    // ============================================================
    // EXTENDED FIELDS - CUSTOMER
    // ============================================================

    private String orderNumber;
    private String tokenNumber;
    private String customerId;
    private String customerType;
    private String department;
    private String studentId;
    private String employeeId;

    // ============================================================
    // EXTENDED FIELDS - ORDER TYPE / DELIVERY
    // ============================================================

    private String orderType = TYPE_DINE_IN;
    private String deliveryAddress;
    private String deliveryArea;
    private String deliveryInstructions;
    private String tableNumber;
    private String roomNumber;
    private String building;
    private String floor;

    private LocalDateTime deliveryTime;
    private LocalDateTime pickupTime;
    private LocalDateTime estimatedReadyTime;
    private LocalDateTime confirmedAt;
    private LocalDateTime preparingAt;
    private LocalDateTime readyAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime updatedAt;

    private String deliveryPersonName;
    private String deliveryPersonPhone;
    private String deliveryPersonId;

    private Double deliveryLatitude;
    private Double deliveryLongitude;

    // ============================================================
    // EXTENDED FIELDS - MONEY
    // ============================================================

    private double subtotal;
    private double tax;
    private double taxRate = DEFAULT_TAX_RATE;
    private double discount;
    private double discountAmount;
    private double deliveryFee;
    private double serviceCharge;
    private double packagingFee;
    private double tip;
    private double grandTotal;
    private double paidAmount;
    private double dueAmount;
    private double changeAmount;
    private double refundAmount;

    private String currency = "BDT";
    private String couponCode;
    private String couponType;
    private double couponDiscount;

    // ============================================================
    // EXTENDED FIELDS - META / NOTES
    // ============================================================

    private String notes;
    private String specialInstructions;
    private String kitchenNotes;
    private String cancellationReason;
    private String refundReason;
    private String adminNotes;

    private String source = "WEB";
    private String device;
    private String ipAddress;
    private String userAgent;

    private int priority = 0;
    private boolean isUrgent;
    private boolean isGift;
    private String giftMessage;

    private int rating;
    private String feedback;
    private String review;

    private String invoiceNumber;
    private String transactionId;
    private String bkashTrxId;
    private String paymentReference;

    private String createdBy;
    private String updatedBy;
    private String confirmedBy;
    private String preparedBy;
    private String deliveredBy;

    private boolean deleted;
    private boolean archived;
    private String tags;

    private Map<String, String> extraInfo = new HashMap<>();

    // ============================================================
    // CONSTRUCTORS
    // ============================================================

    public Order() {
        this.orderNumber = generateOrderNumber();
        this.tokenNumber = generateTokenNumber();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Order(Long userId, String customerName, String email, String phone) {
        this();
        this.userId = userId;
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
    }

    public Order(Long userId, String customerName, String email, String phone, String address, List<OrderItem> items) {
        this(userId, customerName, email, phone);
        this.address = address;
        this.items = items != null ? items : new ArrayList<>();
        recalculate();
    }

    public Order(Long id, Long userId, String customerName, String email, String phone, String address, String status, String paymentMethod, String paymentStatus, List<OrderItem> items, double total, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.items = items != null ? items : new ArrayList<>();
        this.total = total;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.orderNumber = generateOrderNumber();
        this.tokenNumber = generateTokenNumber();
        recalculate();
    }

    // ============================================================
    // ORIGINAL GETTERS / SETTERS - KEPT
    // ============================================================

    public Long getId() { return id; }
    public void setId(Long v) { id = v; }

    public Long getUserId() { return userId; }
    public void setUserId(Long v) { userId = v; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String v) { customerName = v; }

    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }

    public String getPhone() { return phone; }
    public void setPhone(String v) { phone = v; }

    public String getAddress() { return address; }
    public void setAddress(String v) { address = v; }

    public String getStatus() { return status; }
    public void setStatus(String v) {
        status = v;
        updatedAt = LocalDateTime.now();
        if (STATUS_CONFIRMED.equals(v) && confirmedAt == null) confirmedAt = LocalDateTime.now();
        if (STATUS_PREPARING.equals(v) && preparingAt == null) preparingAt = LocalDateTime.now();
        if (STATUS_READY.equals(v) && readyAt == null) readyAt = LocalDateTime.now();
        if (STATUS_DELIVERED.equals(v) && deliveredAt == null) deliveredAt = LocalDateTime.now();
        if (STATUS_CANCELLED.equals(v) && cancelledAt == null) cancelledAt = LocalDateTime.now();
    }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String v) { paymentMethod = v; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String v) { paymentStatus = v; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> v) {
        items = v != null ? v : new ArrayList<>();
        recalculate();
    }

    public double getTotal() { return total; }
    public void setTotal(double v) { total = v; grandTotal = v; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { createdAt = v; }

    // ============================================================
    // EXTENDED GETTERS / SETTERS
    // ============================================================

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String v) { orderNumber = v; }

    public String getTokenNumber() { return tokenNumber; }
    public void setTokenNumber(String v) { tokenNumber = v; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String v) { customerId = v; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String v) { customerType = v; }

    public String getDepartment() { return department; }
    public void setDepartment(String v) { department = v; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String v) { studentId = v; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String v) { employeeId = v; }

    public String getOrderType() { return orderType; }
    public void setOrderType(String v) { orderType = v; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String v) { deliveryAddress = v; }

    public String getDeliveryArea() { return deliveryArea; }
    public void setDeliveryArea(String v) { deliveryArea = v; }

    public String getDeliveryInstructions() { return deliveryInstructions; }
    public void setDeliveryInstructions(String v) { deliveryInstructions = v; }

    public String getTableNumber() { return tableNumber; }
    public void setTableNumber(String v) { tableNumber = v; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String v) { roomNumber = v; }

    public String getBuilding() { return building; }
    public void setBuilding(String v) { building = v; }

    public String getFloor() { return floor; }
    public void setFloor(String v) { floor = v; }

    public LocalDateTime getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(LocalDateTime v) { deliveryTime = v; }

    public LocalDateTime getPickupTime() { return pickupTime; }
    public void setPickupTime(LocalDateTime v) { pickupTime = v; }

    public LocalDateTime getEstimatedReadyTime() { return estimatedReadyTime; }
    public void setEstimatedReadyTime(LocalDateTime v) { estimatedReadyTime = v; }

    public LocalDateTime getConfirmedAt() { return confirmedAt; }
    public void setConfirmedAt(LocalDateTime v) { confirmedAt = v; }

    public LocalDateTime getPreparingAt() { return preparingAt; }
    public void setPreparingAt(LocalDateTime v) { preparingAt = v; }

    public LocalDateTime getReadyAt() { return readyAt; }
    public void setReadyAt(LocalDateTime v) { readyAt = v; }

    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime v) { deliveredAt = v; }

    public LocalDateTime getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(LocalDateTime v) { cancelledAt = v; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v) { updatedAt = v; }

    public String getDeliveryPersonName() { return deliveryPersonName; }
    public void setDeliveryPersonName(String v) { deliveryPersonName = v; }

    public String getDeliveryPersonPhone() { return deliveryPersonPhone; }
    public void setDeliveryPersonPhone(String v) { deliveryPersonPhone = v; }

    public String getDeliveryPersonId() { return deliveryPersonId; }
    public void setDeliveryPersonId(String v) { deliveryPersonId = v; }

    public Double getDeliveryLatitude() { return deliveryLatitude; }
    public void setDeliveryLatitude(Double v) { deliveryLatitude = v; }

    public Double getDeliveryLongitude() { return deliveryLongitude; }
    public void setDeliveryLongitude(Double v) { deliveryLongitude = v; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double v) { subtotal = v; }

    public double getTax() { return tax; }
    public void setTax(double v) { tax = v; }

    public double getTaxRate() { return taxRate; }
    public void setTaxRate(double v) { taxRate = v; }

    public double getDiscount() { return discount; }
    public void setDiscount(double v) { discount = v; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double v) { discountAmount = v; }

    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double v) { deliveryFee = v; }

    public double getServiceCharge() { return serviceCharge; }
    public void setServiceCharge(double v) { serviceCharge = v; }

    public double getPackagingFee() { return packagingFee; }
    public void setPackagingFee(double v) { packagingFee = v; }

    public double getTip() { return tip; }
    public void setTip(double v) { tip = v; }

    public double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(double v) { grandTotal = v; total = v; }

    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double v) { paidAmount = v; updateDue(); }

    public double getDueAmount() { return dueAmount; }
    public void setDueAmount(double v) { dueAmount = v; }

    public double getChangeAmount() { return changeAmount; }
    public void setChangeAmount(double v) { changeAmount = v; }

    public double getRefundAmount() { return refundAmount; }
    public void setRefundAmount(double v) { refundAmount = v; }

    public String getCurrency() { return currency; }
    public void setCurrency(String v) { currency = v; }

    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String v) { couponCode = v; }

    public String getCouponType() { return couponType; }
    public void setCouponType(String v) { couponType = v; }

    public double getCouponDiscount() { return couponDiscount; }
    public void setCouponDiscount(double v) { couponDiscount = v; }

    public String getNotes() { return notes; }
    public void setNotes(String v) { notes = v; }

    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String v) { specialInstructions = v; }

    public String getKitchenNotes() { return kitchenNotes; }
    public void setKitchenNotes(String v) { kitchenNotes = v; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String v) { cancellationReason = v; }

    public String getRefundReason() { return refundReason; }
    public void setRefundReason(String v) { refundReason = v; }

    public String getAdminNotes() { return adminNotes; }
    public void setAdminNotes(String v) { adminNotes = v; }

    public String getSource() { return source; }
    public void setSource(String v) { source = v; }

    public String getDevice() { return device; }
    public void setDevice(String v) { device = v; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String v) { ipAddress = v; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String v) { userAgent = v; }

    public int getPriority() { return priority; }
    public void setPriority(int v) { priority = v; }

    public boolean isUrgent() { return isUrgent; }
    public void setUrgent(boolean v) { isUrgent = v; }

    public boolean isGift() { return isGift; }
    public void setGift(boolean v) { isGift = v; }

    public String getGiftMessage() { return giftMessage; }
    public void setGiftMessage(String v) { giftMessage = v; }

    public int getRating() { return rating; }
    public void setRating(int v) { rating = v; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String v) { feedback = v; }

    public String getReview() { return review; }
    public void setReview(String v) { review = v; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String v) { invoiceNumber = v; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String v) { transactionId = v; }

    public String getBkashTrxId() { return bkashTrxId; }
    public void setBkashTrxId(String v) { bkashTrxId = v; }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String v) { paymentReference = v; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String v) { createdBy = v; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String v) { updatedBy = v; }

    public String getConfirmedBy() { return confirmedBy; }
    public void setConfirmedBy(String v) { confirmedBy = v; }

    public String getPreparedBy() { return preparedBy; }
    public void setPreparedBy(String v) { preparedBy = v; }

    public String getDeliveredBy() { return deliveredBy; }
    public void setDeliveredBy(String v) { deliveredBy = v; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean v) { deleted = v; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean v) { archived = v; }

    public String getTags() { return tags; }
    public void setTags(String v) { tags = v; }

    public Map<String, String> getExtraInfo() { return extraInfo; }
    public void setExtraInfo(Map<String, String> v) { extraInfo = v; }

    // ============================================================
    // BUSINESS LOGIC
    // ============================================================

    public void addItem(OrderItem item) {
        if (items == null) items = new ArrayList<>();
        items.add(item);
        recalculate();
    }

    public void removeItem(int index) {
        if (items != null && index >= 0 && index < items.size()) {
            items.remove(index);
            recalculate();
        }
    }

    public void clearItems() {
        items = new ArrayList<>();
        recalculate();
    }

    public int getItemCount() {
        if (items == null) return 0;
        return items.stream().mapToInt(i -> i.getQuantity()).sum();
    }

    public int getUniqueItemCount() {
        return items == null ? 0 : items.size();
    }

    public void recalculate() {
        if (items == null) items = new ArrayList<>();
        subtotal = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        double disc = discountAmount + couponDiscount;
        if (discount > 0) disc += subtotal * discount / 100.0;
        tax = (subtotal - disc) * taxRate;
        if (TYPE_DELIVERY.equals(orderType)) {
            deliveryFee = subtotal >= FREE_DELIVERY_MIN ? 0 : DEFAULT_DELIVERY_FEE;
        } else {
            deliveryFee = 0;
        }
        grandTotal = subtotal - disc + tax + deliveryFee + serviceCharge + packagingFee + tip;
        if (grandTotal < 0) grandTotal = 0;
        total = grandTotal;
        updateDue();
    }

    private void updateDue() {
        dueAmount = grandTotal - paidAmount;
        if (dueAmount < 0) {
            changeAmount = -dueAmount;
            dueAmount = 0;
        } else {
            changeAmount = 0;
        }
        if (paidAmount >= grandTotal && grandTotal > 0) {
            paymentStatus = PAYMENT_PAID;
        } else if (paidAmount > 0) {
            paymentStatus = PAYMENT_PARTIAL;
        } else {
            if (!PAYMENT_REFUNDED.equals(paymentStatus) && !PAYMENT_FAILED.equals(paymentStatus)) {
                paymentStatus = PAYMENT_UNPAID;
            }
        }
    }

    public void applyCoupon(String code, double discountValue, String type) {
        this.couponCode = code;
        this.couponType = type;
        if ("PERCENT".equalsIgnoreCase(type)) {
            this.couponDiscount = subtotal * discountValue / 100.0;
        } else {
            this.couponDiscount = discountValue;
        }
        recalculate();
    }

    public void removeCoupon() {
        this.couponCode = null;
        this.couponType = null;
        this.couponDiscount = 0;
        recalculate();
    }

    public void markAsPaid(double amount) {
        this.paidAmount = amount;
        updateDue();
    }

    public void markAsPaid() {
        this.paidAmount = grandTotal;
        updateDue();
    }

    public void confirm() { setStatus(STATUS_CONFIRMED); }
    public void startPreparing() { setStatus(STATUS_PREPARING); }
    public void markReady() { setStatus(STATUS_READY); }
    public void markDelivered() { setStatus(STATUS_DELIVERED); }

    public void cancel(String reason) {
        this.cancellationReason = reason;
        setStatus(STATUS_CANCELLED);
    }

    public void refund(String reason) {
        this.refundReason = reason;
        this.refundAmount = paidAmount;
        setStatus(STATUS_REFUNDED);
        setPaymentStatus(PAYMENT_REFUNDED);
    }

    public void hold() { setStatus(STATUS_ON_HOLD); }
    public void unhold() { setStatus(STATUS_PENDING); }

    public boolean isPending() { return STATUS_PENDING.equals(status); }
    public boolean isConfirmed() { return STATUS_CONFIRMED.equals(status); }
    public boolean isPreparing() { return STATUS_PREPARING.equals(status); }
    public boolean isReady() { return STATUS_READY.equals(status); }
    public boolean isDelivered() { return STATUS_DELIVERED.equals(status); }
    public boolean isCancelled() { return STATUS_CANCELLED.equals(status); }
    public boolean isRefunded() { return STATUS_REFUNDED.equals(status); }
    public boolean isPaid() { return PAYMENT_PAID.equals(paymentStatus); }
    public boolean isUnpaid() { return PAYMENT_UNPAID.equals(paymentStatus); }
    public boolean isDelivery() { return TYPE_DELIVERY.equals(orderType); }
    public boolean isTakeaway() { return TYPE_TAKEAWAY.equals(orderType); }
    public boolean isDineIn() { return TYPE_DINE_IN.equals(orderType); }
    public boolean isCash() { return PAYMENT_CASH.equals(paymentMethod); }
    public boolean isBkash() { return PAYMENT_BKASH.equals(paymentMethod); }
    public boolean isOnlinePayment() { return PAYMENT_CARD.equals(paymentMethod) || PAYMENT_ONLINE.equals(paymentMethod); }

    public boolean canCancel() {
        return STATUS_PENDING.equals(status) || STATUS_CONFIRMED.equals(status) || STATUS_PREPARING.equals(status);
    }

    public boolean canRefund() {
        return STATUS_DELIVERED.equals(status) && PAYMENT_PAID.equals(paymentStatus);
    }

    public boolean canDeliver() {
        return STATUS_READY.equals(status) || STATUS_PREPARING.equals(status);
    }

    public String getStatusLabel() {
        switch (status) {
            case STATUS_PENDING: return "Pending";
            case STATUS_CONFIRMED: return "Confirmed";
            case STATUS_PREPARING: return "Preparing";
            case STATUS_READY: return "Ready";
            case STATUS_DELIVERED: return "Delivered";
            case STATUS_CANCELLED: return "Cancelled";
            case STATUS_REFUNDED: return "Refunded";
            default: return status;
        }
    }

    public String getPaymentLabel() {
        switch (paymentMethod) {
            case PAYMENT_CASH: return "Cash";
            case PAYMENT_CARD: return "Card";
            case PAYMENT_BKASH: return "bKash";
            case PAYMENT_NAGAD: return "Nagad";
            case PAYMENT_ROCKET: return "Rocket";
            default: return paymentMethod;
        }
    }

    // ============================================================
    // HELPERS / UTILS
    // ============================================================

    private String generateOrderNumber() {
        return "ORD-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + (int) (Math.random() * 9000 + 1000);
    }

    private String generateTokenNumber() {
        return "T-" + (int) (Math.random() * 900 + 100);
    }

    public String generateInvoiceNumber() {
        this.invoiceNumber = "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return invoiceNumber;
    }

    public String getFormattedTotal() {
        return String.format("%.2f %s", grandTotal, currency);
    }

    public String getFormattedDate() {
        if (createdAt == null) return "";
        return createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    public String getSummary() {
        return orderNumber + " | " + customerName + " | " + getItemCount() + " items | " + getFormattedTotal() + " | " + status;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("orderNumber", orderNumber);
        m.put("customerName", customerName);
        m.put("email", email);
        m.put("phone", phone);
        m.put("status", status);
        m.put("paymentMethod", paymentMethod);
        m.put("paymentStatus", paymentStatus);
        m.put("subtotal", subtotal);
        m.put("tax", tax);
        m.put("discount", discountAmount + couponDiscount);
        m.put("deliveryFee", deliveryFee);
        m.put("total", total);
        m.put("grandTotal", grandTotal);
        m.put("itemCount", getItemCount());
        m.put("createdAt", createdAt);
        m.put("orderType", orderType);
        return m;
    }

    public Order copy() {
        Order c = new Order();
        c.setUserId(this.userId);
        c.setCustomerName(this.customerName);
        c.setEmail(this.email);
        c.setPhone(this.phone);
        c.setAddress(this.address);
        c.setStatus(this.status);
        c.setPaymentMethod(this.paymentMethod);
        c.setPaymentStatus(this.paymentStatus);
        c.setItems(new ArrayList<>(this.items));
        c.setOrderType(this.orderType);
        c.setNotes(this.notes);
        c.recalculate();
        return c;
    }

    public static Order sample() {
        Order o = new Order(1L, "Rahim Uddin", "rahim@gmail.com", "01712345678");
        o.setAddress("Bhairab Bazar");
        o.setOrderType(TYPE_DINE_IN);
        o.setTableNumber("T-05");
        o.setNotes("Less spicy");
        return o;
    }

    public static List<Order> sortByDate(List<Order> list, boolean ascending) {
        if (list == null) return new ArrayList<>();
        Comparator<Order> c = Comparator.comparing(Order::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()));
        if (!ascending) c = c.reversed();
        return list.stream().sorted(c).collect(Collectors.toList());
    }

    public static double sumTotal(List<Order> list) {
        if (list == null) return 0;
        return list.stream().mapToDouble(Order::getGrandTotal).sum();
    }

    // ============================================================
    // BUILDER
    // ============================================================

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Order order = new Order();

        public Builder userId(Long v) { order.setUserId(v); return this; }
        public Builder customerName(String v) { order.setCustomerName(v); return this; }
        public Builder email(String v) { order.setEmail(v); return this; }
        public Builder phone(String v) { order.setPhone(v); return this; }
        public Builder address(String v) { order.setAddress(v); return this; }
        public Builder orderType(String v) { order.setOrderType(v); return this; }
        public Builder tableNumber(String v) { order.setTableNumber(v); return this; }
        public Builder paymentMethod(String v) { order.setPaymentMethod(v); return this; }
        public Builder notes(String v) { order.setNotes(v); return this; }
        public Builder items(List<OrderItem> v) { order.setItems(v); return this; }
        public Builder addItem(OrderItem v) { order.addItem(v); return this; }
        public Builder coupon(String code, double disc, String type) { order.applyCoupon(code, disc, type); return this; }
        public Builder deliveryFee(double v) { order.setDeliveryFee(v); return this; }
        public Order build() { order.recalculate(); return order; }
    }

    // ============================================================
    // EQUALS / HASHCODE / TO_STRING
    // ============================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(orderNumber, order.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", total=" + total +
                ", grandTotal=" + grandTotal +
                ", itemCount=" + getItemCount() +
                ", orderType='" + orderType + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}