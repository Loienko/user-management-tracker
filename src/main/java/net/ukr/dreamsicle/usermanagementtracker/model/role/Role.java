package net.ukr.dreamsicle.usermanagementtracker.model.role;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "role")
public class Role {

    @Id
    private ObjectId id;

    @Enumerated(EnumType.STRING)
    private RoleType name;
}
