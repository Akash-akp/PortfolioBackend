package dev.akashakp.portfolio.web;

import dev.akashakp.portfolio.dto.PortfolioDtos.PortfolioRequest;
import dev.akashakp.portfolio.model.*;
import dev.akashakp.portfolio.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService service;

    public PortfolioController(PortfolioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Portfolio> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public Portfolio get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/slug/{slug}")
    public Portfolio getBySlug(@PathVariable String slug) {
        return service.getBySlug(slug);
    }

    @PostMapping
    public ResponseEntity<Portfolio> create(@RequestBody PortfolioRequest req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    @PutMapping("/{id}")
    public Portfolio replace(@PathVariable Long id, @RequestBody PortfolioRequest req) {
        return service.replace(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===== Section-level singleton endpoints =====

    @GetMapping("/{id}/hero")
    public Hero getHero(@PathVariable Long id) {
        return service.get(id).getHero();
    }

    @PutMapping("/{id}/hero")
    public Hero updateHero(@PathVariable Long id, @RequestBody Hero hero) {
        return service.updateHero(id, hero).getHero();
    }

    @GetMapping("/{id}/about")
    public About getAbout(@PathVariable Long id) {
        return service.get(id).getAbout();
    }

    @PutMapping("/{id}/about")
    public About updateAbout(@PathVariable Long id, @RequestBody About about) {
        return service.updateAbout(id, about).getAbout();
    }

    @GetMapping("/{id}/contact")
    public Contact getContact(@PathVariable Long id) {
        return service.get(id).getContact();
    }

    @PutMapping("/{id}/contact")
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        return service.updateContact(id, contact).getContact();
    }
}
