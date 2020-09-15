package net.ukr.dreamsicle.usermanagementtracker.repository;

import net.ukr.dreamsicle.usermanagementtracker.model.user.StatusType;
import net.ukr.dreamsicle.usermanagementtracker.model.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "select u from User u where u.id = :id and u.status = :statusType")
    Optional<User> findByIdAndStatus(ObjectId id, StatusType statusType);

    @Query(value = "select u from User u where u.status = :statusType")
    Page<User> findAllByStatus(Pageable pageable, StatusType statusType);

}
