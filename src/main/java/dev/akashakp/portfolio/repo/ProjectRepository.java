package dev.akashakp.portfolio.repo;

import dev.akashakp.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
}
