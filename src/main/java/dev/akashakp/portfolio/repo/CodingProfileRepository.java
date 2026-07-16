package dev.akashakp.portfolio.repo;

import dev.akashakp.portfolio.model.CodingProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CodingProfileRepository extends JpaRepository<CodingProfile, Long> {
    List<CodingProfile> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
}
