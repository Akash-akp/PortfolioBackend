package dev.akashakp.portfolio.service;

import dev.akashakp.portfolio.dto.PortfolioDtos.PortfolioRequest;
import dev.akashakp.portfolio.model.*;
import dev.akashakp.portfolio.repo.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PortfolioService {

    private final PortfolioRepository repo;

    public PortfolioService(PortfolioRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Portfolio> list() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Portfolio get(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Portfolio " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public Portfolio getBySlug(String slug) {
        return repo.findBySlug(slug).orElseThrow(() -> new EntityNotFoundException("Portfolio '" + slug + "' not found"));
    }

    @Transactional
    public Portfolio create(PortfolioRequest req) {
        String slug = (req.slug() == null || req.slug().isBlank())
                ? "portfolio-" + UUID.randomUUID().toString().substring(0, 8)
                : req.slug();
        if (repo.existsBySlug(slug)) {
            throw new IllegalArgumentException("slug already exists: " + slug);
        }
        Portfolio p = Portfolio.builder()
                .slug(slug)
                .hero(req.hero())
                .about(req.about())
                .contact(req.contact())
                .build();
        attachChildren(p, req);
        return repo.save(p);
    }

    @Transactional
    public Portfolio replace(Long id, PortfolioRequest req) {
        Portfolio p = get(id);
        if (req.slug() != null && !req.slug().isBlank()) p.setSlug(req.slug());
        p.setHero(req.hero());
        p.setAbout(req.about());
        p.setContact(req.contact());

        p.getStack().clear();
        p.getExperience().clear();
        p.getProjects().clear();
        p.getServices().clear();
        p.getWhyHire().clear();
        p.getSkills().clear();
        p.getCoding().clear();

        attachChildren(p, req);
        return repo.save(p);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new EntityNotFoundException("Portfolio " + id + " not found");
        repo.deleteById(id);
    }

    private void attachChildren(Portfolio p, PortfolioRequest req) {
        if (req.stack() != null) req.stack().forEach(s -> {
            s.setPortfolio(p);
            p.getStack().add(s);
        });
        if (req.experience() != null) req.experience().forEach(e -> {
            e.setPortfolio(p);
            p.getExperience().add(e);
        });
        if (req.projects() != null) req.projects().forEach(pr -> {
            pr.setPortfolio(p);
            p.getProjects().add(pr);
        });
        if (req.services() != null) req.services().forEach(s -> {
            s.setPortfolio(p);
            p.getServices().add(s);
        });
        if (req.whyHire() != null) req.whyHire().forEach(w -> {
            w.setPortfolio(p);
            p.getWhyHire().add(w);
        });
        if (req.skills() != null) req.skills().forEach(s -> {
            s.setPortfolio(p);
            p.getSkills().add(s);
        });
        if (req.coding() != null) req.coding().forEach(c -> {
            c.setPortfolio(p);
            p.getCoding().add(c);
        });
    }

    // Section-level singleton updates
    @Transactional
    public Portfolio updateHero(Long id, Hero hero) {
        Portfolio p = get(id);
        p.setHero(hero);
        return repo.save(p);
    }

    @Transactional
    public Portfolio updateAbout(Long id, About about) {
        Portfolio p = get(id);
        p.setAbout(about);
        return repo.save(p);
    }

    @Transactional
    public Portfolio updateContact(Long id, Contact contact) {
        Portfolio p = get(id);
        p.setContact(contact);
        return repo.save(p);
    }
}
