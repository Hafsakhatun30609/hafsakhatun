package com.canteenpro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long foodId;

    private Long userId;

    @Column(length = 100)
    private String userName;

    @Column(length = 100)
    private String userEmail;

    @Column(length = 200)
    private String userAvatar;

    @Min(1)
    @Max(5)
    private int rating;

    @Column(length = 2000)
    private String comment;

    @Column(length = 100)
    private String foodName;

    @Column(length = 50)
    private String foodCategory;

    @Column(length = 500)
    private String foodImage;

    private Long orderId;

    private String title;

    private String pros;

    private String cons;

    private String suggestion;

    private Integer helpfulCount;

    private Integer unhelpfulCount;

    private Integer likeCount;

    private Integer dislikeCount;

    private Integer reportCount;

    private Boolean isVerified;

    private Boolean isApproved;

    private Boolean isRejected;

    private Boolean isFlagged;

    private Boolean isHidden;

    private Boolean isRecommended;

    private Boolean wouldRecommend;

    private Boolean isEdited;

    private String status;

    private String moderationReason;

    private String adminReply;

    private String ownerReply;

    private LocalDateTime repliedAt;

    private String repliedBy;

    private String imageUrl;

    private String videoUrl;

    private List<String> images;

    private String tags;

    private String location;

    private String deviceType;

    private String ipAddress;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime editedAt;

    private Integer tasteRating;

    private Integer qualityRating;

    private Integer serviceRating;

    private Integer valueRating;

    private Integer packagingRating;

    private Double overallScore;

    public Review() {
        this.rating = 5;
        this.helpfulCount = 0;
        this.unhelpfulCount = 0;
        this.likeCount = 0;
        this.dislikeCount = 0;
        this.reportCount = 0;
        this.isVerified = false;
        this.isApproved = true;
        this.isRejected = false;
        this.isFlagged = false;
        this.isHidden = false;
        this.isRecommended = false;
        this.wouldRecommend = true;
        this.isEdited = false;
        this.status = "APPROVED";
        this.tasteRating = 5;
        this.qualityRating = 5;
        this.serviceRating = 5;
        this.valueRating = 5;
        this.packagingRating = 5;
        this.overallScore = 5.0;
        this.images = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Review(Long id, Long foodId, Long userId, String userName, int rating, String comment) {
        this.id = id;
        this.foodId = foodId;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.images = new ArrayList<>();
    }

    public Review(Long foodId, Long userId, String userName, int rating, String comment) {
        this.foodId = foodId;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.images = new ArrayList<>();
    }

    public Review(Long foodId, String userName, String userEmail, int rating, String comment, String title) {
        this.foodId = foodId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.rating = rating;
        this.comment = comment;
        this.title = title;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.images = new ArrayList<>();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Integer getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(Integer helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

    public Integer getUnhelpfulCount() {
        return unhelpfulCount;
    }

    public void setUnhelpfulCount(Integer unhelpfulCount) {
        this.unhelpfulCount = unhelpfulCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Integer dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Integer getReportCount() {
        return reportCount;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    public Boolean getIsFlagged() {
        return isFlagged;
    }

    public void setIsFlagged(Boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Boolean getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    public Boolean getWouldRecommend() {
        return wouldRecommend;
    }

    public void setWouldRecommend(Boolean wouldRecommend) {
        this.wouldRecommend = wouldRecommend;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModerationReason() {
        return moderationReason;
    }

    public void setModerationReason(String moderationReason) {
        this.moderationReason = moderationReason;
    }

    public String getAdminReply() {
        return adminReply;
    }

    public void setAdminReply(String adminReply) {
        this.adminReply = adminReply;
    }

    public String getOwnerReply() {
        return ownerReply;
    }

    public void setOwnerReply(String ownerReply) {
        this.ownerReply = ownerReply;
    }

    public LocalDateTime getRepliedAt() {
        return repliedAt;
    }

    public void setRepliedAt(LocalDateTime repliedAt) {
        this.repliedAt = repliedAt;
    }

    public String getRepliedBy() {
        return repliedBy;
    }

    public void setRepliedBy(String repliedBy) {
        this.repliedBy = repliedBy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public Integer getTasteRating() {
        return tasteRating;
    }

    public void setTasteRating(Integer tasteRating) {
        this.tasteRating = tasteRating;
    }

    public Integer getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(Integer qualityRating) {
        this.qualityRating = qualityRating;
    }

    public Integer getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(Integer serviceRating) {
        this.serviceRating = serviceRating;
    }

    public Integer getValueRating() {
        return valueRating;
    }

    public void setValueRating(Integer valueRating) {
        this.valueRating = valueRating;
    }

    public Integer getPackagingRating() {
        return packagingRating;
    }

    public void setPackagingRating(Integer packagingRating) {
        this.packagingRating = packagingRating;
    }

    public Double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Double overallScore) {
        this.overallScore = overallScore;
    }

    @Transient
    public double calculateOverallScore() {
        double total = 0;
        int count = 0;
        if (tasteRating != null) {
            total += tasteRating;
            count++;
        }
        if (qualityRating != null) {
            total += qualityRating;
            count++;
        }
        if (serviceRating != null) {
            total += serviceRating;
            count++;
        }
        if (valueRating != null) {
            total += valueRating;
            count++;
        }
        if (packagingRating != null) {
            total += packagingRating;
            count++;
        }
        if (count > 0) {
            this.overallScore = total / count;
        } else {
            this.overallScore = (double) rating;
        }
        return this.overallScore;
    }

    @Transient
    public boolean isPositive() {
        return rating >= 4;
    }

    @Transient
    public boolean isNegative() {
        return rating <= 2;
    }

    @Transient
    public boolean isNeutral() {
        return rating == 3;
    }

    public void incrementHelpful() {
        if (this.helpfulCount == null) {
            this.helpfulCount = 0;
        }
        this.helpfulCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementUnhelpful() {
        if (this.unhelpfulCount == null) {
            this.unhelpfulCount = 0;
        }
        this.unhelpfulCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementLike() {
        if (this.likeCount == null) {
            this.likeCount = 0;
        }
        this.likeCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementDislike() {
        if (this.dislikeCount == null) {
            this.dislikeCount = 0;
        }
        this.dislikeCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void approve() {
        this.isApproved = true;
        this.isRejected = false;
        this.status = "APPROVED";
        this.updatedAt = LocalDateTime.now();
    }

    public void reject(String reason) {
        this.isRejected = true;
        this.isApproved = false;
        this.status = "REJECTED";
        this.moderationReason = reason;
        this.updatedAt = LocalDateTime.now();
    }

    public void flag(String reason) {
        this.isFlagged = true;
        this.status = "FLAGGED";
        this.moderationReason = reason;
        if (this.reportCount == null) {
            this.reportCount = 0;
        }
        this.reportCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void reply(String reply, String by) {
        this.ownerReply = reply;
        this.repliedBy = by;
        this.repliedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markEdited() {
        this.isEdited = true;
        this.editedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}