package com.canteenpro.controller;

import com.canteenpro.dto.ReviewRequest;
import com.canteenpro.dto.ReviewResponse;
import com.canteenpro.dto.ReviewUpdateRequest;
import com.canteenpro.model.Review;
import com.canteenpro.exception.ReviewNotFoundException;
import com.canteenpro.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/api/reviews")
@Validated
@CrossOrigin(origins = "*")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        logger.info("Fetching all reviews - page: {}, size: {}", page, size);
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Review> reviewPage = reviewService.getAllPaged(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", reviewPage.getContent());
        response.put("currentPage", reviewPage.getNumber());
        response.put("totalItems", reviewPage.getTotalElements());
        response.put("totalPages", reviewPage.getTotalPages());
        response.put("hasNext", reviewPage.hasNext());
        response.put("hasPrevious", reviewPage.hasPrevious());
        response.put("isFirst", reviewPage.isFirst());
        response.put("isLast", reviewPage.isLast());
        response.put("success", true);
        response.put("message", "Reviews fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllSimple() {
        logger.info("Fetching simple list of all reviews");
        List<Review> reviews = reviewService.all();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        logger.info("Fetching review with id: {}", id);
        Review review = reviewService.get(id);
        if (review == null) {
            throw new ReviewNotFoundException("Review not found with id: " + id);
        }
        return ResponseEntity.ok(review);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ReviewResponse> getReviewDetails(@PathVariable Long id) {
        logger.info("Fetching review details for id: {}", id);
        ReviewResponse response = reviewService.getReviewDetails(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/food/{id}")
    public ResponseEntity<List<Review>> getFoodReviews(@PathVariable Long id) {
        logger.info("Fetching reviews for food id: {}", id);
        List<Review> reviews = reviewService.foodReviews(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/food/{id}/paged")
    public ResponseEntity<Map<String, Object>> getFoodReviewsPaged(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        logger.info("Fetching paged reviews for food id: {}", id);
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Review> reviewPage = reviewService.foodReviewsPaged(id, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", reviewPage.getContent());
        response.put("currentPage", reviewPage.getNumber());
        response.put("totalItems", reviewPage.getTotalElements());
        response.put("totalPages", reviewPage.getTotalPages());
        response.put("hasNext", reviewPage.hasNext());
        response.put("hasPrevious", reviewPage.hasPrevious());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/food/{id}/average")
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable Long id) {
        logger.info("Fetching average rating for food id: {}", id);
        double avg = reviewService.average(id);
        long count = reviewService.countByFood(id);
        Map<String, Object> response = new HashMap<>();
        response.put("foodId", id);
        response.put("averageRating", avg);
        response.put("totalReviews", count);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/food/{id}/summary")
    public ResponseEntity<Map<String, Object>> getFoodReviewSummary(@PathVariable Long id) {
        logger.info("Fetching review summary for food id: {}", id);
        Map<String, Object> summary = new HashMap<>();
        summary.put("foodId", id);
        summary.put("averageRating", reviewService.average(id));
        summary.put("totalReviews", reviewService.countByFood(id));
        summary.put("fiveStar", reviewService.countByFoodAndRating(id, 5));
        summary.put("fourStar", reviewService.countByFoodAndRating(id, 4));
        summary.put("threeStar", reviewService.countByFoodAndRating(id, 3));
        summary.put("twoStar", reviewService.countByFoodAndRating(id, 2));
        summary.put("oneStar", reviewService.countByFoodAndRating(id, 1));
        summary.put("positiveCount", reviewService.countPositive(id));
        summary.put("negativeCount", reviewService.countNegative(id));
        summary.put("recommendationRate", reviewService.recommendationRate(id));
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/food/{id}/rating/{rating}")
    public ResponseEntity<List<Review>> getFoodReviewsByRating(
            @PathVariable Long id,
            @PathVariable int rating) {
        logger.info("Fetching reviews for food {} with rating {}", id, rating);
        List<Review> reviews = reviewService.foodReviewsByRating(id, rating);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/food/{id}/top")
    public ResponseEntity<List<Review>> getTopFoodReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "3") int limit) {
        logger.info("Fetching top reviews for food id: {}", id);
        List<Review> reviews = reviewService.topReviews(id, limit);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/food/{id}/recent")
    public ResponseEntity<List<Review>> getRecentFoodReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "5") int limit) {
        logger.info("Fetching recent reviews for food id: {}", id);
        List<Review> reviews = reviewService.recentFoodReviews(id, limit);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/food/{id}/with-comment")
    public ResponseEntity<List<Review>> getFoodReviewsWithComment(@PathVariable Long id) {
        logger.info("Fetching reviews with comment for food id: {}", id);
        List<Review> reviews = reviewService.withComment(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/food/{id}/with-image")
    public ResponseEntity<List<Review>> getFoodReviewsWithImage(@PathVariable Long id) {
        logger.info("Fetching reviews with image for food id: {}", id);
        List<Review> reviews = reviewService.withImage(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/food/{id}/verified")
    public ResponseEntity<List<Review>> getVerifiedFoodReviews(@PathVariable Long id) {
        logger.info("Fetching verified reviews for food id: {}", id);
        List<Review> reviews = reviewService.verifiedReviews(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable String email) {
        logger.info("Fetching reviews by user: {}", email);
        List<Review> reviews = reviewService.byUser(email);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{email}/paged")
    public ResponseEntity<Map<String, Object>> getUserReviewsPaged(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching paged reviews for user: {}", email);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Review> reviewPage = reviewService.byUserPaged(email, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", reviewPage.getContent());
        response.put("currentPage", reviewPage.getNumber());
        response.put("totalItems", reviewPage.getTotalElements());
        response.put("totalPages", reviewPage.getTotalPages());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{email}/count")
    public ResponseEntity<Map<String, Long>> getUserReviewCount(@PathVariable String email) {
        logger.info("Counting reviews for user: {}", email);
        long count = reviewService.countByUser(email);
        return ResponseEntity.ok(Map.of("totalReviews", count));
    }

    @GetMapping("/user/{email}/average")
    public ResponseEntity<Map<String, Object>> getUserAverageRating(@PathVariable String email) {
        logger.info("Fetching average rating given by user: {}", email);
        double avg = reviewService.averageByUser(email);
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("averageRatingGiven", avg);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Review>> getByRating(@PathVariable int rating) {
        logger.info("Fetching reviews with rating: {}", rating);
        List<Review> reviews = reviewService.byRating(rating);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/rating/{rating}/count")
    public ResponseEntity<Map<String, Long>> getRatingCount(@PathVariable int rating) {
        logger.info("Counting reviews with rating: {}", rating);
        long count = reviewService.countByRating(rating);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Review>> searchReviews(@RequestParam String q) {
        logger.info("Searching reviews with query: {}", q);
        if (q == null || q.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Review> reviews = reviewService.search(q.trim());
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Review>> filterReviews(
            @RequestParam(required = false) Long foodId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(required = false) String userEmail,
            @RequestParam(required = false) Boolean verified,
            @RequestParam(required = false) Boolean hasComment) {
        logger.info("Filtering reviews - foodId: {}, minRating: {}", foodId, minRating);
        List<Review> reviews = reviewService.filter(foodId, minRating, maxRating, userEmail, verified, hasComment);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Review>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        logger.info("Fetching reviews from {} to {}", from, to);
        List<Review> reviews = reviewService.byDateRange(from, to);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/today")
    public ResponseEntity<List<Review>> getTodayReviews() {
        logger.info("Fetching today reviews");
        List<Review> reviews = reviewService.todayReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Review>> getRecentReviews(
            @RequestParam(defaultValue = "10") int limit) {
        logger.info("Fetching recent reviews, limit: {}", limit);
        List<Review> reviews = reviewService.recentReviews(limit);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Review>> getTopRatedReviews(
            @RequestParam(defaultValue = "10") int limit) {
        logger.info("Fetching top rated reviews");
        List<Review> reviews = reviewService.topRated(limit);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/most-helpful")
    public ResponseEntity<List<Review>> getMostHelpfulReviews(
            @RequestParam(defaultValue = "10") int limit) {
        logger.info("Fetching most helpful reviews");
        List<Review> reviews = reviewService.mostHelpful(limit);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Review>> getPendingReviews() {
        logger.info("Fetching pending reviews");
        List<Review> reviews = reviewService.pendingReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Review>> getApprovedReviews() {
        logger.info("Fetching approved reviews");
        List<Review> reviews = reviewService.approvedReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<Review>> getRejectedReviews() {
        logger.info("Fetching rejected reviews");
        List<Review> reviews = reviewService.rejectedReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/flagged")
    public ResponseEntity<List<Review>> getFlaggedReviews() {
        logger.info("Fetching flagged reviews");
        List<Review> reviews = reviewService.flaggedReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getReviewCount() {
        long count = reviewService.count();
        Map<String, Long> response = Map.of("totalReviews", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/rating")
    public ResponseEntity<Map<String, Long>> getCountByAllRatings() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("oneStar", reviewService.countByRating(1));
        counts.put("twoStar", reviewService.countByRating(2));
        counts.put("threeStar", reviewService.countByRating(3));
        counts.put("fourStar", reviewService.countByRating(4));
        counts.put("fiveStar", reviewService.countByRating(5));
        counts.put("total", reviewService.count());
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/count/status")
    public ResponseEntity<Map<String, Long>> getCountByStatus() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("pending", reviewService.countPending());
        counts.put("approved", reviewService.countApproved());
        counts.put("rejected", reviewService.countRejected());
        counts.put("flagged", reviewService.countFlagged());
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/overall-average")
    public ResponseEntity<Map<String, Object>> getOverallAverage() {
        double avg = reviewService.overallAverage();
        long total = reviewService.count();
        Map<String, Object> response = new HashMap<>();
        response.put("overallAverage", avg);
        response.put("totalReviews", total);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/best-foods")
    public ResponseEntity<List<Map<String, Object>>> getBestFoods(
            @RequestParam(defaultValue = "5") int limit) {
        logger.info("Fetching best foods by rating");
        List<Map<String, Object>> best = reviewService.bestFoods(limit);
        return ResponseEntity.ok(best);
    }

    @GetMapping("/worst-foods")
    public ResponseEntity<List<Map<String, Object>>> getWorstFoods(
            @RequestParam(defaultValue = "5") int limit) {
        logger.info("Fetching worst foods by rating");
        List<Map<String, Object>> worst = reviewService.worstFoods(limit);
        return ResponseEntity.ok(worst);
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@Valid @RequestBody Review review) {
        logger.info("Adding review for food id: {}", review.getFoodId());
        Review created = reviewService.add(review);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/dto")
    public ResponseEntity<Review> addReviewDto(@Valid @RequestBody ReviewRequest request) {
        logger.info("Adding review via DTO for food id: {}", request.getFoodId());
        Review created = reviewService.addFromDto(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Review>> addBulkReviews(@Valid @RequestBody List<Review> reviews) {
        logger.info("Adding bulk reviews, count: {}", reviews.size());
        List<Review> created = new ArrayList<>();
        for (Review r : reviews) {
            created.add(reviewService.add(r));
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody Review review) {
        logger.info("Updating review with id: {}", id);
        Review updated = reviewService.update(id, review);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/dto")
    public ResponseEntity<Review> updateReviewDto(
            @PathVariable Long id,
            @Valid @RequestBody ReviewUpdateRequest request) {
        logger.info("Updating review via DTO, id: {}", id);
        Review updated = reviewService.updateFromDto(id, request);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/rating")
    public ResponseEntity<Review> updateRating(
            @PathVariable Long id,
            @RequestParam @Min(1) @Max(5) int rating) {
        logger.info("Updating rating for review id: {}, rating: {}", id, rating);
        Review updated = reviewService.updateRating(id, rating);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/comment")
    public ResponseEntity<Review> updateComment(
            @PathVariable Long id,
            @RequestParam @NotBlank String comment) {
        logger.info("Updating comment for review id: {}", id);
        Review updated = reviewService.updateComment(id, comment);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/helpful")
    public ResponseEntity<Review> markHelpful(@PathVariable Long id) {
        logger.info("Marking review as helpful, id: {}", id);
        Review updated = reviewService.incrementHelpful(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/unhelpful")
    public ResponseEntity<Review> markUnhelpful(@PathVariable Long id) {
        logger.info("Marking review as unhelpful, id: {}", id);
        Review updated = reviewService.incrementUnhelpful(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/like")
    public ResponseEntity<Review> likeReview(@PathVariable Long id) {
        logger.info("Liking review id: {}", id);
        Review updated = reviewService.like(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/dislike")
    public ResponseEntity<Review> dislikeReview(@PathVariable Long id) {
        logger.info("Disliking review id: {}", id);
        Review updated = reviewService.dislike(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Review> approveReview(@PathVariable Long id) {
        logger.info("Approving review id: {}", id);
        Review updated = reviewService.approve(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Review> rejectReview(
            @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        logger.info("Rejecting review id: {}, reason: {}", id, reason);
        Review updated = reviewService.reject(id, reason);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/flag")
    public ResponseEntity<Review> flagReview(
            @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        logger.info("Flagging review id: {}", id);
        Review updated = reviewService.flag(id, reason);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/unflag")
    public ResponseEntity<Review> unflagReview(@PathVariable Long id) {
        logger.info("Unflagging review id: {}", id);
        Review updated = reviewService.unflag(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<Review> verifyReview(@PathVariable Long id) {
        logger.info("Verifying review id: {}", id);
        Review updated = reviewService.verify(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/hide")
    public ResponseEntity<Review> hideReview(@PathVariable Long id) {
        logger.info("Hiding review id: {}", id);
        Review updated = reviewService.hide(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/show")
    public ResponseEntity<Review> showReview(@PathVariable Long id) {
        logger.info("Showing review id: {}", id);
        Review updated = reviewService.show(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/reply")
    public ResponseEntity<Review> replyToReview(
            @PathVariable Long id,
            @RequestParam String reply) {
        logger.info("Replying to review id: {}", id);
        Review updated = reviewService.reply(id, reply);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteReview(@PathVariable Long id) {
        logger.info("Deleting review with id: {}", id);
        reviewService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review deleted successfully");
        response.put("deletedId", String.valueOf(id));
        response.put("success", "true");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/food/{foodId}")
    public ResponseEntity<Map<String, String>> deleteByFood(@PathVariable Long foodId) {
        logger.info("Deleting reviews for food id: {}", foodId);
        reviewService.deleteByFood(foodId);
        return ResponseEntity.ok(Map.of("message", "Reviews deleted for food"));
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<Map<String, String>> deleteByUser(@PathVariable String email) {
        logger.info("Deleting reviews for user: {}", email);
        reviewService.deleteByUser(email);
        return ResponseEntity.ok(Map.of("message", "Reviews deleted for user"));
    }

    @DeleteMapping("/all")
    public ResponseEntity<Map<String, String>> deleteAllReviews() {
        logger.warn("Deleting ALL reviews - Admin action");
        reviewService.deleteAll();
        return ResponseEntity.ok(Map.of("message", "All reviews deleted"));
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkUserReviewed(
            @RequestParam Long foodId,
            @RequestParam String email) {
        logger.info("Checking if user {} reviewed food {}", email, foodId);
        boolean reviewed = reviewService.hasReviewed(foodId, email);
        return ResponseEntity.ok(Map.of("hasReviewed", reviewed));
    }

    @GetMapping("/can-review")
    public ResponseEntity<Map<String, Boolean>> canReview(
            @RequestParam Long foodId,
            @RequestParam String email) {
        boolean can = !reviewService.hasReviewed(foodId, email);
        return ResponseEntity.ok(Map.of("canReview", can));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("controller", "ReviewController");
        status.put("message", "Review API is running");
        status.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(status);
    }
}