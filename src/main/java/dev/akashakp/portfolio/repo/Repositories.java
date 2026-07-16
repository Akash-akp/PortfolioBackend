package dev.akashakp.portfolio.repo;

import dev.akashakp.portfolio.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Repositories {

    interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
        Optional<Portfolio> findBySlug(String slug);
        boolean existsBySlug(String slug);
    }

    interface StackGroupRepository extends JpaRepository<StackGroup, Long> {
        List<StackGroup> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
    }

    interface ExperienceRepository extends JpaRepository<Experience, Long> {
        List<Experience> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
    }

    interface ProjectRepository extends JpaRepository<Project, Long> {
        List<Project> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
    }

    interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
        List<ServiceItem> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
    }

    interface WhyHireRepository extends JpaRepository<WhyHireItem, Long> {
        List<WhyHireItem> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
    }

    interface SkillRepository extends JpaRepository<Skill, Long> {
        List<Skill> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
    }

    interface CodingProfileRepository extends JpaRepository<CodingProfile, Long> {
        List<CodingProfile> findByPortfolioIdOrderBySortOrderAscIdAsc(Long portfolioId);
    }
}
