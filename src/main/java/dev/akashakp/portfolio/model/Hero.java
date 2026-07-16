package dev.akashakp.portfolio.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Transient;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hero implements Serializable {
    private String name;
    private String tagline;
    private String description;
    private List<String> roles;
    private String availability;
    private String resumeUrl;
    private String photo;
    @Transient
    private List<Social> socials;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Social implements Serializable{
        private String label;
        private String href;
        /** github | linkedin | mail */
        private String icon;
    }
}
