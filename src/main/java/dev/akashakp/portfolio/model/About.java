package dev.akashakp.portfolio.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class About implements Serializable {
    private String title;
    private String description;
    private List<Pillar> pillars;
    private List<TimelineItem> timeline;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Pillar implements Serializable{
        /** server | layers | brain | sparkles */
        private String icon;
        private String title;
        private String text;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class TimelineItem implements Serializable{
        private String year;
        private String label;
    }
}