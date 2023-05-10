package dev.accessaid.AccessAid.Ratings.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.Ratings.response.RatingResponse;

public class RatingMapper {

    public static RatingResponse toRatingResponse(Rating rating) {
        return new RatingResponse(
                rating.getId(),
                rating.getRating(),
                rating.getUser().getId(),
                rating.getPlace().getId());

    }

    public static List<RatingResponse> toRatingResponses(List<Rating> ratings) {
        return ratings.stream().map(RatingMapper::toRatingResponse).collect(Collectors.toList());
    }

    public static Page<RatingResponse> toRatingResponses(Page<Rating> ratings, Pageable pageable) {
        List<RatingResponse> ratingResponses = ratings.stream()
                .map(RatingMapper::toRatingResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(ratingResponses, pageable, ratings.getTotalElements());
    }

}