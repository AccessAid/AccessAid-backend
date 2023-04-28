package dev.accessaid.AccessAid.Ratings.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingNotFoundException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingSaveException;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.Ratings.repository.RatingRepository;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();

    }

    @Override
    public Rating getRatingById(Integer id) throws RatingNotFoundException {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent())
            return rating.get();
        else
            throw new RatingNotFoundException("Rating with id " + id + " not found");

    }

    @Override
    public Rating createRating(Rating rating) throws RatingSaveException {
        User user = userRepository.findById(rating.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + rating.getUser().getId()));

        Place place = placeRepository.findById(rating.getPlace().getId())
                .orElseThrow(
                        () -> new PlaceNotFoundException("Place not found with id: " + rating.getPlace().getId()));

        Rating savedRating = ratingRepository.save(rating);

        place.addRating(savedRating);

        if (!place.getUsers().contains(user)) {
            place.getUsers().add(user);
            placeRepository.save(place);
        }

        return savedRating;

    }

    @Override
    public Rating changeRating(Rating rating) throws RatingNotFoundException {
        Optional<Rating> ratingToUpdate = ratingRepository.findById(rating.getId());
        if (!ratingToUpdate.isPresent()) {
            throw new RatingNotFoundException("Rating with id " + rating.getId() + " not found");
        }
        Rating updatedRating = ratingToUpdate.get();
        return ratingRepository.save(updatedRating);
    }

    @Override
    @Transactional
    public Rating removeRating(Integer id) throws RatingNotFoundException {

        Optional<Rating> ratingToDelete = ratingRepository.findById(id);
        if (!ratingToDelete.isPresent()) {
            throw new RatingNotFoundException("Rating with id " + id + " not found");
        }
        ratingRepository.deleteById(id);
        return ratingToDelete.get();
    }

    @Override
    public List<Rating> getRatingByUser(User user) throws UserNotFoundException {
        return ratingRepository.findByUser(user);

    }

    @Override
    public List<Rating> getRatingByPlace(Place place) throws PlaceNotFoundException {
        return ratingRepository.findByPlace(place);
    }

}
