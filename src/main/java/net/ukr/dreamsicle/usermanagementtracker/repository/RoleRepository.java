package net.ukr.dreamsicle.usermanagementtracker.repository;

import net.ukr.dreamsicle.usermanagementtracker.model.role.Role;
import net.ukr.dreamsicle.usermanagementtracker.model.role.RoleType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, ObjectId> {
    Optional<Role> findByName(RoleType roleUser);
}
