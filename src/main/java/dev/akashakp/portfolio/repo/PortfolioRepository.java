package dev.akashakp.portfolio.repo;

import dev.akashakp.portfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findBySlug(String slug);
    boolean existsBySlug(String slug);
}