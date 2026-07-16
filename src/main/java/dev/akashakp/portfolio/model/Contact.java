package dev.akashakp.portfolio.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact implements Serializable {
    private String email;
    private String location;
    private String calendarUrl;
}
