package dev.akashakp.portfolio.repo;

import dev.akashakp.portfolio.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    List<ServiceItem> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
}
