package dev.akashakp.portfolio.repo;

import dev.akashakp.portfolio.model.StackGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StackGroupRepository extends JpaRepository<StackGroup, Long> {
    List<StackGroup> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
}