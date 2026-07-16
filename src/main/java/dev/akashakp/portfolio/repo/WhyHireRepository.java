package dev.akashakp.portfolio.repo;

import dev.akashakp.portfolio.model.WhyHireItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WhyHireRepository extends JpaRepository<WhyHireItem, Long> {
    List<WhyHireItem> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
}
