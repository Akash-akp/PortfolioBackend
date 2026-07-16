package dev.akashakp.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "why_hire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WhyHireItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @JsonIgnore
    private Portfolio portfolio;

    private String icon;
    private String title;
    @Column(columnDefinition = "text")
    private String text;

    private Integer sortOrder;
}
