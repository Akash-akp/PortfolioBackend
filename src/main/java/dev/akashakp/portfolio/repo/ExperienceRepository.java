package dev.akashakp.portfolio.repo;

import dev.akashakp.portfolio.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
}
