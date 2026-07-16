package dev.akashakp.portfolio.web;

import dev.akashakp.portfolio.model.*;
import dev.akashakp.portfolio.repo.*;
import dev.akashakp.portfolio.service.PortfolioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio/{portfolioId}")
        @Transactional
        public class SectionController {

        private final PortfolioService portfolioService;
        private final StackGroupRepository stackRepo;
        private final ExperienceRepository expRepo;
        private final ProjectRepository projRepo;
        private final ServiceItemRepository svcRepo;
        private final WhyHireRepository whyRepo;
        private final SkillRepository skillRepo;
        private final CodingProfileRepository codingRepo;

        public SectionController(PortfolioService portfolioService,
        StackGroupRepository stackRepo,
        ExperienceRepository expRepo,
        ProjectRepository projRepo,
        ServiceItemRepository svcRepo,
        WhyHireRepository whyRepo,
        SkillRepository skillRepo,
        CodingProfileRepository codingRepo) {
    this.portfolioService = portfolioService;
    this.stackRepo = stackRepo;
    this.expRepo = expRepo;
    this.projRepo = projRepo;
    this.svcRepo = svcRepo;
    this.whyRepo = whyRepo;
    this.skillRepo = skillRepo;
    this.codingRepo = codingRepo;
}

// ----- STACK -----
@GetMapping("/stack")
        public List<StackGroup> listStack(@PathVariable Long portfolioId) {
    portfolioService.get(portfolioId);
    return stackRepo.findByPortfolioIdOrderBySortOrderAscIdAsc(portfolioId);
}

@GetMapping("/stack/{itemId}")
        public StackGroup getStack(@PathVariable Long portfolioId, @PathVariable Long itemId) {
        return checkStack(portfolioId, itemId);
    }

@PostMapping("/stack")
        public ResponseEntity<StackGroup> createStack(@PathVariable Long portfolioId, @RequestBody StackGroup body) {
    Portfolio p = portfolioService.get(portfolioId);
    body.setId(null); body.setPortfolio(p);
    return ResponseEntity.status(201).body(stackRepo.save(body));
}

@PutMapping("/stack/{itemId}")
        public StackGroup updateStack(@PathVariable Long portfolioId, @PathVariable Long itemId, @RequestBody StackGroup body) {
        StackGroup existing = checkStack(portfolioId, itemId);
        existing.setTitle(body.getTitle());
        existing.setItems(body.getItems());
        if (body.getSortOrder() != null) existing.setSortOrder(body.getSortOrder());
        return stackRepo.save(existing);
    }

@DeleteMapping("/stack/{itemId}")
        public ResponseEntity<Void> deleteStack(@PathVariable Long portfolioId, @PathVariable Long itemId) {
    checkStack(portfolioId, itemId); stackRepo.deleteById(itemId);
    return ResponseEntity.noContent().build();
}

private StackGroup checkStack(Long portfolioId, Long itemId) {
    StackGroup s = stackRepo.findById(itemId).orElseThrow(() -> new EntityNotFoundException("StackGroup " + itemId + " not found"));
    if (!s.getPortfolio().getId().equals(portfolioId)) throw new EntityNotFoundException("StackGroup " + itemId + " not in portfolio " + portfolioId);
    return s;
}

// ----- EXPERIENCE -----
@GetMapping("/experience")
        public List<Experience> listExperience(@PathVariable Long portfolioId) {
    portfolioService.get(portfolioId);
    return expRepo.findByPortfolioIdOrderBySortOrderAscIdAsc(portfolioId);
}

@GetMapping("/experience/{itemId}")
        public Experience getExp(@PathVariable Long portfolioId, @PathVariable Long itemId) { return checkExp(portfolioId, itemId); }

@PostMapping("/experience")
        public ResponseEntity<Experience> createExp(@PathVariable Long portfolioId, @RequestBody Experience body) {
    Portfolio p = portfolioService.get(portfolioId);
    body.setId(null); body.setPortfolio(p);
    return ResponseEntity.status(201).body(expRepo.save(body));
}

@PutMapping("/experience/{itemId}")
        public Experience updateExp(@PathVariable Long portfolioId, @PathVariable Long itemId, @RequestBody Experience body) {
        Experience e = checkExp(portfolioId, itemId);
        e.setCompany(body.getCompany()); e.setRole(body.getRole()); e.setPeriod(body.getPeriod());
        e.setLocation(body.getLocation()); e.setBullets(body.getBullets()); e.setTags(body.getTags());
        if (body.getSortOrder() != null) e.setSortOrder(body.getSortOrder());
        return expRepo.save(e);
    }

@DeleteMapping("/experience/{itemId}")
        public ResponseEntity<Void> deleteExp(@PathVariable Long portfolioId, @PathVariable Long itemId) {
    checkExp(portfolioId, itemId); expRepo.deleteById(itemId);
    return ResponseEntity.noContent().build();
}

private Experience checkExp(Long portfolioId, Long itemId) {
    Experience e = expRepo.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Experience " + itemId + " not found"));
    if (!e.getPortfolio().getId().equals(portfolioId)) throw new EntityNotFoundException("Experience " + itemId + " not in portfolio " + portfolioId);
    return e;
}

// ----- PROJECTS -----
@GetMapping("/projects")
        public List<Project> listProjects(@PathVariable Long portfolioId) {
    portfolioService.get(portfolioId);
    return projRepo.findByPortfolioIdOrderBySortOrderAscIdAsc(portfolioId);
}

@GetMapping("/projects/{itemId}")
        public Project getProject(@PathVariable Long portfolioId, @PathVariable Long itemId) { return checkProj(portfolioId, itemId); }

@PostMapping("/projects")
        public ResponseEntity<Project> createProject(@PathVariable Long portfolioId, @RequestBody Project body) {
    Portfolio p = portfolioService.get(portfolioId);
    body.setId(null); body.setPortfolio(p);
    return ResponseEntity.status(201).body(projRepo.save(body));
}

@PutMapping("/projects/{itemId}")
        public Project updateProject(@PathVariable Long portfolioId, @PathVariable Long itemId, @RequestBody Project body) {
        Project existing = checkProj(portfolioId, itemId);
        existing.setName(body.getName()); existing.setTagline(body.getTagline()); existing.setDescription(body.getDescription());
        existing.setAccent(body.getAccent()); existing.setGithub(body.getGithub()); existing.setLive(body.getLive());
        existing.setPreviewImage(body.getPreviewImage()); existing.setTags(body.getTags());
        existing.setHighlights(body.getHighlights()); existing.setCaseStudy(body.getCaseStudy());
        if (body.getSortOrder() != null) existing.setSortOrder(body.getSortOrder());
        return projRepo.save(existing);
    }

@DeleteMapping("/projects/{itemId}")
        public ResponseEntity<Void> deleteProject(@PathVariable Long portfolioId, @PathVariable Long itemId) {
    checkProj(portfolioId, itemId); projRepo.deleteById(itemId);
    return ResponseEntity.noContent().build();
}

private Project checkProj(Long portfolioId, Long itemId) {
    Project p = projRepo.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Project " + itemId + " not found"));
    if (!p.getPortfolio().getId().equals(portfolioId)) throw new EntityNotFoundException("Project " + itemId + " not in portfolio " + portfolioId);
    return p;
}

// ----- SERVICES -----
@GetMapping("/services")
        public List<ServiceItem> listServices(@PathVariable Long portfolioId) {
    portfolioService.get(portfolioId);
    return svcRepo.findByPortfolioIdOrderBySortOrderAscIdAsc(portfolioId);
}

@GetMapping("/services/{itemId}")
        public ServiceItem getService(@PathVariable Long portfolioId, @PathVariable Long itemId) { return checkSvc(portfolioId, itemId); }

@PostMapping("/services")
        public ResponseEntity<ServiceItem> createService(@PathVariable Long portfolioId, @RequestBody ServiceItem body) {
    Portfolio p = portfolioService.get(portfolioId);
    body.setId(null); body.setPortfolio(p);
    return ResponseEntity.status(201).body(svcRepo.save(body));
}

@PutMapping("/services/{itemId}")
        public ServiceItem updateService(@PathVariable Long portfolioId, @PathVariable Long itemId, @RequestBody ServiceItem body) {
        ServiceItem s = checkSvc(portfolioId, itemId);
        s.setName(body.getName()); s.setTagline(body.getTagline()); s.setFeatures(body.getFeatures());
        s.setHighlighted(body.isHighlighted());
        if (body.getSortOrder() != null) s.setSortOrder(body.getSortOrder());
        return svcRepo.save(s);
    }

@DeleteMapping("/services/{itemId}")
        public ResponseEntity<Void> deleteService(@PathVariable Long portfolioId, @PathVariable Long itemId) {
    checkSvc(portfolioId, itemId); svcRepo.deleteById(itemId);
    return ResponseEntity.noContent().build();
}

private ServiceItem checkSvc(Long portfolioId, Long itemId) {
    ServiceItem s = svcRepo.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Service " + itemId + " not found"));
    if (!s.getPortfolio().getId().equals(portfolioId)) throw new EntityNotFoundException("Service " + itemId + " not in portfolio " + portfolioId);
    return s;
}

// ----- WHY HIRE -----
@GetMapping("/why-hire")
        public List<WhyHireItem> listWhyHire(@PathVariable Long portfolioId) {
    portfolioService.get(portfolioId);
    return whyRepo.findByPortfolioIdOrderBySortOrderAscIdAsc(portfolioId);
}

@GetMapping("/why-hire/{itemId}")
        public WhyHireItem getWhyHire(@PathVariable Long portfolioId, @PathVariable Long itemId) { return checkWhy(portfolioId, itemId); }

@PostMapping("/why-hire")
        public ResponseEntity<WhyHireItem> createWhyHire(@PathVariable Long portfolioId, @RequestBody WhyHireItem body) {
    Portfolio p = portfolioService.get(portfolioId);
    body.setId(null); body.setPortfolio(p);
    return ResponseEntity.status(201).body(whyRepo.save(body));
}

@PutMapping("/why-hire/{itemId}")
        public WhyHireItem updateWhyHire(@PathVariable Long portfolioId, @PathVariable Long itemId, @RequestBody WhyHireItem body) {
        WhyHireItem w = checkWhy(portfolioId, itemId);
        w.setIcon(body.getIcon()); w.setTitle(body.getTitle()); w.setText(body.getText());
        if (body.getSortOrder() != null) w.setSortOrder(body.getSortOrder());
        return whyRepo.save(w);
    }

@DeleteMapping("/why-hire/{itemId}")
        public ResponseEntity<Void> deleteWhyHire(@PathVariable Long portfolioId, @PathVariable Long itemId) {
    checkWhy(portfolioId, itemId); whyRepo.deleteById(itemId);
    return ResponseEntity.noContent().build();
}

private WhyHireItem checkWhy(Long portfolioId, Long itemId) {
    WhyHireItem w = whyRepo.findById(itemId).orElseThrow(() -> new EntityNotFoundException("WhyHireItem " + itemId + " not found"));
    if (!w.getPortfolio().getId().equals(portfolioId)) throw new EntityNotFoundException("WhyHireItem " + itemId + " not in portfolio " + portfolioId);
    return w;
}

// ----- SKILLS -----
@GetMapping("/skills")
        public List<Skill> listSkills(@PathVariable Long portfolioId) {
    portfolioService.get(portfolioId);
    return skillRepo.findByPortfolioIdOrderBySortOrderAscIdAsc(portfolioId);
}

@GetMapping("/skills/{itemId}")
        public Skill getSkill(@PathVariable Long portfolioId, @PathVariable Long itemId) { return checkSkill(portfolioId, itemId); }

@PostMapping("/skills")
        public ResponseEntity<Skill> createSkill(@PathVariable Long portfolioId, @RequestBody Skill body) {
    Portfolio p = portfolioService.get(portfolioId);
    body.setId(null); body.setPortfolio(p);
    return ResponseEntity.status(201).body(skillRepo.save(body));
}

@PutMapping("/skills/{itemId}")
        public Skill updateSkill(@PathVariable Long portfolioId, @PathVariable Long itemId, @RequestBody Skill body) {
        Skill s = checkSkill(portfolioId, itemId);
        s.setName(body.getName()); s.setLevel(body.getLevel());
        if (body.getSortOrder() != null) s.setSortOrder(body.getSortOrder());
        return skillRepo.save(s);
    }

@DeleteMapping("/skills/{itemId}")
        public ResponseEntity<Void> deleteSkill(@PathVariable Long portfolioId, @PathVariable Long itemId) {
    checkSkill(portfolioId, itemId); skillRepo.deleteById(itemId);
    return ResponseEntity.noContent().build();
}

private Skill checkSkill(Long portfolioId, Long itemId) {
    Skill s = skillRepo.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Skill " + itemId + " not found"));
    if (!s.getPortfolio().getId().equals(portfolioId)) throw new EntityNotFoundException("Skill " + itemId + " not in portfolio " + portfolioId);
    return s;
}

// ----- CODING -----
@GetMapping("/coding")
        public List<CodingProfile> listCoding(@PathVariable Long portfolioId) {
    portfolioService.get(portfolioId);
    return codingRepo.findByPortfolioIdOrderBySortOrderAscIdAsc(portfolioId);
}

@GetMapping("/coding/{itemId}")
        public CodingProfile getCoding(@PathVariable Long portfolioId, @PathVariable Long itemId) { return checkCoding(portfolioId, itemId); }

@PostMapping("/coding")
        public ResponseEntity<CodingProfile> createCoding(@PathVariable Long portfolioId, @RequestBody CodingProfile body) {
    Portfolio p = portfolioService.get(portfolioId);
    body.setId(null); body.setPortfolio(p);
    return ResponseEntity.status(201).body(codingRepo.save(body));
}

@PutMapping("/coding/{itemId}")
        public CodingProfile updateCoding(@PathVariable Long portfolioId, @PathVariable Long itemId, @RequestBody CodingProfile body) {
        CodingProfile c = checkCoding(portfolioId, itemId);
        c.setName(body.getName()); c.setHandle(body.getHandle()); c.setIcon(body.getIcon());
        c.setStat(body.getStat()); c.setMeta(body.getMeta()); c.setHref(body.getHref());
        if (body.getSortOrder() != null) c.setSortOrder(body.getSortOrder());
        return codingRepo.save(c);
    }

@DeleteMapping("/coding/{itemId}")
        public ResponseEntity<Void> deleteCoding(@PathVariable Long portfolioId, @PathVariable Long itemId) {
    checkCoding(portfolioId, itemId); codingRepo.deleteById(itemId);
    return ResponseEntity.noContent().build();
}

private CodingProfile checkCoding(Long portfolioId, Long itemId) {
    CodingProfile c = codingRepo.findById(itemId).orElseThrow(() -> new EntityNotFoundException("CodingProfile " + itemId + " not found"));
    if (!c.getPortfolio().getId().equals(portfolioId)) throw new EntityNotFoundException("CodingProfile " + itemId + " not in portfolio " + portfolioId);
    return c;
}
}
