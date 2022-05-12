package no.ntnu.backend.repository;

import no.ntnu.backend.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, Long> {

    //public List<Expression> findAllByEspressionUser(String email);

    Optional<List<Expression>> findAllByEspressionUser(String email);

}
