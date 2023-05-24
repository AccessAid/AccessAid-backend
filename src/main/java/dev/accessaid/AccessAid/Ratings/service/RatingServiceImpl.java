package dev.accessaid.AccessAid.Ratings.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingDuplicateUserPlaceException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingNotFoundException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingSaveException;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.Ratings.repository.RatingRepository;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<Rating> getAllRatings(Pageable pageable) {
        return ratingRepository.findAll(pageable);
    }

    @Override
    public Rating getRatingById(Integer id) throws RatingNotFoundException {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("Rating with id " + id + " not found"));
    }

    @Override
    public Rating createRating(Rating rating)
            throws RatingSaveException, UserNotFoundException, PlaceNotFoundException {

        if (rating.getRating() != null && rating.getUser().getId() == null && rating.getPlace().getId() != null)
            throw new RatingSaveException("user must not be null");

        if (rating.getRating() != null && rating.getUser().getId() != null && rating.getPlace().getId() == null)
            throw new RatingSaveException("place must not be null");

        if (rating.getRating() != null && rating.getUser().getId() == null && rating.getPlace().getId() == null)
            throw new RatingSaveException("user and place must not be null");

        TypedQuery<Rating> query = entityManager.createQuery(
                "FROM Rating r WHERE r.user.id = :userId AND r.place.id = :placeId",
                Rating.class);
        query.setParameter("userId", rating.getUser().getId());
        query.setParameter("placeId", rating.getPlace().getId());
        List<Rating> ratingFind = query.getResultList();
        if (ratingFind.size() > 0)
            throw new RatingDuplicateUserPlaceException("rating for user and place already exists");

        User user = userRepository.findById(rating.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + rating.getUser().getId()));

        Place place = placeRepository.findById(rating.getPlace().getId())
                .orElseThrow(() -> new PlaceNotFoundException("Place not found with id: " + rating.getPlace().getId()));

        Rating savedRating = ratingRepository.save(rating);

        place.addRating(savedRating);

        if (!place.getUsers().contains(user)) {
            place.getUsers().add(user);
            placeRepository.save(place);
        }

        return savedRating;

    }

    @Override
    public Rating changeRating(Integer ratingId, Rating rating) throws RatingNotFoundException {
        Optional<Rating> ratingToUpdate = ratingRepository.findById(ratingId);
        if (!ratingToUpdate.isPresent())
            throw new RatingNotFoundException("Rating with id " + ratingId + " not found");

        ratingToUpdate.get().setRating(rating.getRating());

        return ratingRepository.save(ratingToUpdate.get());
    }

    @Override
    @Transactional
    public Rating removeRating(Integer id) throws RatingNotFoundException {
        Rating ratingToDelete = ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("Rating with id " + id + " not found"));
        ratingRepository.deleteById(id);
        return ratingToDelete;
    }

    @Override
    public Page<Rating> getRatingByUser(User user, Pageable pageable) throws UserNotFoundException {
        return ratingRepository.findByUser(user, pageable);

    }

    @Override
    public Page<Rating> getRatingByPlace(Place place, Pageable pageable) throws PlaceNotFoundException {
        return ratingRepository.findByPlace(place, pageable);
    }

}
