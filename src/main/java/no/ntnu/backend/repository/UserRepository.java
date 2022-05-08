package no.ntnu.backend.repository;

import no.ntnu.backend.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, String>{
}
