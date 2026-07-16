package dev.akashakp.portfolio.dto;


import dev.akashakp.portfolio.model.*;

import java.util.List;

public class PortfolioDtos {

    public record PortfolioRequest(
            String slug,
            Hero hero,
            About about,
            Contact contact,
            List<StackGroup> stack,
            List<Experience> experience,
            List<Project> projects,
            List<ServiceItem> services,
            List<WhyHireItem> whyHire,
            List<Skill> skills,
            List<CodingProfile> coding
    ) {}
}