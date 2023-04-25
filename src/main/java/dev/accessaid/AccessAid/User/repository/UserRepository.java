package dev.accessaid.AccessAid.User.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.accessaid.AccessAid.User.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM _user", nativeQuery = true)
    List<User> findAllUsers();

    void deleteUserById(Integer id);

    <S extends User> S save(S user);

}
