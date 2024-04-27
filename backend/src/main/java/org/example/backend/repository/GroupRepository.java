package org.example.backend.repository;

import org.example.backend.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<UserGroup, Long> {
    UserGroup findByUserId(long userId);
}
