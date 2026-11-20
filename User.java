package com.canteenpro.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * User - Complete User Model for CanteenPro
 *
 * Features:
 * 1. Basic info - name, email, password, phone, role
 * 2. Profile - address, department, DOB, gender, avatar, bio
 * 3. Account - active, verified, locked, login tracking
 * 4. Preferences, wallet, loyalty points
 * 5. Helpers, validation, builder, toMap
 *
 * Version: 2.0.0
 */
public class User {

    // ============================================================
    // CONSTANTS
    // ============================================================

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_CHEF = "CHEF";

    public static final String GENDER_MALE = "MALE";
    public static final String GENDER_FEMALE = "FEMALE";
    public static final String GENDER_OTHER = "OTHER";

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_BANNED = "BANNED";
    public static final String STATUS_PENDING = "PENDING";

    // ============================================================
    // CORE FIELDS - ORIGINAL KEPT
    // ============================================================

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role = "USER";
    private boolean active = true;

    // ============================================================
    // EXTENDED FIELDS - PROFILE
    // ============================================================

    private String address;
    private String city;
    private String area;
    private String zipCode;
    private String country = "Bangladesh";
    private String department;
    private String studentId;
    private String employeeId;
    private String designation;

    private LocalDate dateOfBirth;
    private String gender;
    private String profileImage;
    private String avatarUrl;
    private String bio;
    private String about;

    // ============================================================
    // EXTENDED FIELDS - ACCOUNT
    // ============================================================

    private boolean emailVerified;
    private boolean phoneVerified;
    private String verificationToken;
    private String resetToken;
    private LocalDateTime resetTokenExpiry;

    private boolean locked;
    private boolean banned;
    private String banReason;
    private LocalDateTime bannedAt;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime lastLoginAt;
    private LocalDateTime lastLogoutAt;
    private String lastLoginIp;
    private String lastLoginDevice;
    private int loginCount;
    private int failedLoginAttempts;

    private String createdBy;
    private String updatedBy;

    // ============================================================
    // EXTENDED FIELDS - PREFERENCES / WALLET / LOYALTY
    // ============================================================

    private String language = "en";
    private String theme = "light";
    private boolean emailNotifications = true;
    private boolean smsNotifications = false;
    private boolean pushNotifications = true;

    private double walletBalance;
    private int loyaltyPoints;
    private String membershipLevel = "BRONZE";
    private double totalSpent;
    private int totalOrders;

    private String favoriteFood;
    private String allergies;
    private String dietaryPreference;

    private String emergencyContactName;
    private String emergencyContactPhone;

    private String socialFacebook;
    private String socialInstagram;
    private String socialWhatsapp;

    private String notes;
    private String tags;
    private boolean deleted;
    private boolean archived;

    private Map<String, String> extraInfo = new HashMap<>();

    // ============================================================
    // CONSTRUCTORS
    // ============================================================

    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(Long id, String name, String email, String password, String phone) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(String name, String email, String password, String phone, String role) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public User(Long id, String name, String email, String password, String phone, String role, boolean active, String address, String department, LocalDate dateOfBirth, String gender) {
        this(id, name, email, password, phone);
        this.role = role;
        this.active = active;
        this.address = address;
        this.department = department;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    // ============================================================
    // ORIGINAL GETTERS / SETTERS - KEPT
    // ============================================================

    public Long getId() { return id; }
    public void setId(Long v) { id = v; }

    public String getName() { return name; }
    public void setName(String v) { name = v; updatedAt = LocalDateTime.now(); }

    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }

    public String getPassword() { return password; }
    public void setPassword(String v) { password = v; }

    public String getPhone() { return phone; }
    public void setPhone(String v) { phone = v; }

    public String getRole() { return role; }
    public void setRole(String v) { role = v; }

    public boolean isActive() { return active; }
    public void setActive(boolean v) { active = v; }

    // ============================================================
    // EXTENDED GETTERS / SETTERS
    // ============================================================

    public String getAddress() { return address; }
    public void setAddress(String v) { address = v; }

    public String getCity() { return city; }
    public void setCity(String v) { city = v; }

    public String getArea() { return area; }
    public void setArea(String v) { area = v; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String v) { zipCode = v; }

    public String getCountry() { return country; }
    public void setCountry(String v) { country = v; }

    public String getDepartment() { return department; }
    public void setDepartment(String v) { department = v; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String v) { studentId = v; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String v) { employeeId = v; }

    public String getDesignation() { return designation; }
    public void setDesignation(String v) { designation = v; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate v) { dateOfBirth = v; }

    public String getGender() { return gender; }
    public void setGender(String v) { gender = v; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String v) { profileImage = v; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String v) { avatarUrl = v; }

    public String getBio() { return bio; }
    public void setBio(String v) { bio = v; }

    public String getAbout() { return about; }
    public void setAbout(String v) { about = v; }

    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean v) { emailVerified = v; }

    public boolean isPhoneVerified() { return phoneVerified; }
    public void setPhoneVerified(boolean v) { phoneVerified = v; }

    public String getVerificationToken() { return verificationToken; }
    public void setVerificationToken(String v) { verificationToken = v; }

    public String getResetToken() { return resetToken; }
    public void setResetToken(String v) { resetToken = v; }

    public LocalDateTime getResetTokenExpiry() { return resetTokenExpiry; }
    public void setResetTokenExpiry(LocalDateTime v) { resetTokenExpiry = v; }

    public boolean isLocked() { return locked; }
    public void setLocked(boolean v) { locked = v; }

    public boolean isBanned() { return banned; }
    public void setBanned(boolean v) { banned = v; }

    public String getBanReason() { return banReason; }
    public void setBanReason(String v) { banReason = v; }

    public LocalDateTime getBannedAt() { return bannedAt; }
    public void setBannedAt(LocalDateTime v) { bannedAt = v; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { createdAt = v; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v) { updatedAt = v; }

    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime v) { lastLoginAt = v; }

    public LocalDateTime getLastLogoutAt() { return lastLogoutAt; }
    public void setLastLogoutAt(LocalDateTime v) { lastLogoutAt = v; }

    public String getLastLoginIp() { return lastLoginIp; }
    public void setLastLoginIp(String v) { lastLoginIp = v; }

    public String getLastLoginDevice() { return lastLoginDevice; }
    public void setLastLoginDevice(String v) { lastLoginDevice = v; }

    public int getLoginCount() { return loginCount; }
    public void setLoginCount(int v) { loginCount = v; }

    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(int v) { failedLoginAttempts = v; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String v) { createdBy = v; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String v) { updatedBy = v; }

    public String getLanguage() { return language; }
    public void setLanguage(String v) { language = v; }

    public String getTheme() { return theme; }
    public void setTheme(String v) { theme = v; }

    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean v) { emailNotifications = v; }

    public boolean isSmsNotifications() { return smsNotifications; }
    public void setSmsNotifications(boolean v) { smsNotifications = v; }

    public boolean isPushNotifications() { return pushNotifications; }
    public void setPushNotifications(boolean v) { pushNotifications = v; }

    public double getWalletBalance() { return walletBalance; }
    public void setWalletBalance(double v) { walletBalance = v; }

    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int v) { loyaltyPoints = v; }

    public String getMembershipLevel() { return membershipLevel; }
    public void setMembershipLevel(String v) { membershipLevel = v; }

    public double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(double v) { totalSpent = v; }

    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int v) { totalOrders = v; }

    public String getFavoriteFood() { return favoriteFood; }
    public void setFavoriteFood(String v) { favoriteFood = v; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String v) { allergies = v; }

    public String getDietaryPreference() { return dietaryPreference; }
    public void setDietaryPreference(String v) { dietaryPreference = v; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String v) { emergencyContactName = v; }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String v) { emergencyContactPhone = v; }

    public String getSocialFacebook() { return socialFacebook; }
    public void setSocialFacebook(String v) { socialFacebook = v; }

    public String getSocialInstagram() { return socialInstagram; }
    public void setSocialInstagram(String v) { socialInstagram = v; }

    public String getSocialWhatsapp() { return socialWhatsapp; }
    public void setSocialWhatsapp(String v) { socialWhatsapp = v; }

    public String getNotes() { return notes; }
    public void setNotes(String v) { notes = v; }

    public String getTags() { return tags; }
    public void setTags(String v) { tags = v; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean v) { deleted = v; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean v) { archived = v; }

    public Map<String, String> getExtraInfo() { return extraInfo; }
    public void setExtraInfo(Map<String, String> v) { extraInfo = v; }

    // ============================================================
    // BUSINESS LOGIC
    // ============================================================

    public boolean isAdmin() { return ROLE_ADMIN.equalsIgnoreCase(role); }
    public boolean isStaff() { return ROLE_STAFF.equalsIgnoreCase(role); }
    public boolean isManager() { return ROLE_MANAGER.equalsIgnoreCase(role); }
    public boolean isChef() { return ROLE_CHEF.equalsIgnoreCase(role); }
    public boolean isCustomer() { return ROLE_USER.equalsIgnoreCase(role); }

    public boolean canManageUsers() { return isAdmin() || isManager(); }
    public boolean canManageOrders() { return isAdmin() || isManager() || isStaff() || isChef(); }
    public boolean canManageMenu() { return isAdmin() || isManager() || isChef(); }

    public void activate() { this.active = true; this.locked = false; this.banned = false; updatedAt = LocalDateTime.now(); }
    public void deactivate() { this.active = false; updatedAt = LocalDateTime.now(); }
    public void toggleActive() { this.active = !this.active; updatedAt = LocalDateTime.now(); }

    public void ban(String reason) {
        this.banned = true;
        this.active = false;
        this.banReason = reason;
        this.bannedAt = LocalDateTime.now();
    }

    public void unban() {
        this.banned = false;
        this.active = true;
        this.banReason = null;
        this.bannedAt = null;
    }

    public void lock() { this.locked = true; }
    public void unlock() { this.locked = false; this.failedLoginAttempts = 0; }

    public void recordLogin(String ip, String device) {
        this.lastLoginAt = LocalDateTime.now();
        this.lastLoginIp = ip;
        this.lastLoginDevice = device;
        this.loginCount++;
        this.failedLoginAttempts = 0;
    }

    public void recordFailedLogin() {
        this.failedLoginAttempts++;
        if (failedLoginAttempts >= 5) this.locked = true;
    }

    public void recordLogout() {
        this.lastLogoutAt = LocalDateTime.now();
    }

    public void addWallet(double amount) {
        this.walletBalance += amount;
        updateMembership();
    }

    public boolean deductWallet(double amount) {
        if (walletBalance < amount) return false;
        walletBalance -= amount;
        return true;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
        updateMembership();
    }

    public void addOrder(double amount) {
        this.totalOrders++;
        this.totalSpent += amount;
        this.loyaltyPoints += (int) (amount / 10);
        updateMembership();
    }

    private void updateMembership() {
        if (totalSpent >= 10000 || loyaltyPoints >= 1000) membershipLevel = "GOLD";
        else if (totalSpent >= 5000 || loyaltyPoints >= 500) membershipLevel = "SILVER";
        else membershipLevel = "BRONZE";
    }

    public String generateVerificationToken() {
        this.verificationToken = UUID.randomUUID().toString();
        return verificationToken;
    }

    public String generateResetToken() {
        this.resetToken = UUID.randomUUID().toString();
        this.resetTokenExpiry = LocalDateTime.now().plusMinutes(15);
        return resetToken;
    }

    public boolean isResetTokenValid() {
        return resetToken != null && resetTokenExpiry != null && LocalDateTime.now().isBefore(resetTokenExpiry);
    }

    public int getAge() {
        if (dateOfBirth == null) return 0;
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (address != null) sb.append(address);
        if (area != null) sb.append(", ").append(area);
        if (city != null) sb.append(", ").append(city);
        if (zipCode != null) sb.append(" - ").append(zipCode);
        return sb.toString();
    }

    public String getDisplayName() {
        if (name != null && !name.isEmpty()) return name;
        if (email != null) return email.split("@")[0];
        return "Guest";
    }

    public String getInitials() {
        if (name == null || name.isEmpty()) return "G";
        String[] parts = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isEmpty()) sb.append(p.charAt(0));
            if (sb.length() >= 2) break;
        }
        return sb.toString().toUpperCase();
    }

    public String maskEmail() {
        if (email == null || !email.contains("@")) return "***";
        String[] parts = email.split("@");
        if (parts[0].length() <= 2) return "***@" + parts[1];
        return parts[0].charAt(0) + "***" + parts[0].charAt(parts[0].length() - 1) + "@" + parts[1];
    }

    public String maskPhone() {
        if (phone == null || phone.length() < 7) return "********";
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 3);
    }

    public boolean isValidEmail() {
        return email != null && email.contains("@") && email.contains(".") && email.length() > 5;
    }

    public boolean isValidPhone() {
        if (phone == null) return false;
        String c = phone.replaceAll("[^0-9+]", "");
        return c.length() >= 10 && c.length() <= 15;
    }

    public boolean isValidPassword() {
        return password != null && password.length() >= 6;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("name", name);
        m.put("email", email);
        m.put("phone", phone);
        m.put("role", role);
        m.put("active", active);
        m.put("department", department);
        m.put("address", address);
        m.put("membershipLevel", membershipLevel);
        m.put("loyaltyPoints", loyaltyPoints);
        m.put("totalOrders", totalOrders);
        m.put("totalSpent", totalSpent);
        m.put("createdAt", createdAt);
        return m;
    }

    public Map<String, Object> toPublicMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("name", name);
        m.put("role", role);
        m.put("active", active);
        m.put("avatar", avatarUrl);
        m.put("bio", bio);
        m.put("membershipLevel", membershipLevel);
        return m;
    }

    public static User sampleAdmin() {
        User u = new User("Admin", "admin@canteenpro.com", "admin123", "01700000000");
        u.setRole(ROLE_ADMIN);
        u.setDepartment("Management");
        u.setEmailVerified(true);
        return u;
    }

    public static User sampleCustomer() {
        User u = new User("Rahim Uddin", "rahim@gmail.com", "123456", "01712345678");
        u.setRole(ROLE_USER);
        u.setDepartment("CSE");
        u.setCity("Bhairab");
        return u;
    }

    // ============================================================
    // BUILDER
    // ============================================================

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private User user = new User();
        public Builder name(String v) { user.setName(v); return this; }
        public Builder email(String v) { user.setEmail(v); return this; }
        public Builder password(String v) { user.setPassword(v); return this; }
        public Builder phone(String v) { user.setPhone(v); return this; }
        public Builder role(String v) { user.setRole(v); return this; }
        public Builder address(String v) { user.setAddress(v); return this; }
        public Builder department(String v) { user.setDepartment(v); return this; }
        public Builder gender(String v) { user.setGender(v); return this; }
        public Builder dateOfBirth(LocalDate v) { user.setDateOfBirth(v); return this; }
        public Builder city(String v) { user.setCity(v); return this; }
        public Builder bio(String v) { user.setBio(v); return this; }
        public User build() { user.setUpdatedAt(LocalDateTime.now()); return user; }
    }

    // ============================================================
    // EQUALS / HASH / STRING
    // ============================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", active=" + active +
                ", department='" + department + '\'' +
                ", membershipLevel='" + membershipLevel + '\'' +
                ", totalOrders=" + totalOrders +
                ", createdAt=" + createdAt +
                '}';
    }
}