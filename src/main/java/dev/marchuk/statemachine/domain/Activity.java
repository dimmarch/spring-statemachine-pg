package dev.marchuk.statemachine.domain;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class Activity {
    private Integer id;
    private ActivityState state;
    private String name;
    private String description;
    private Duration duration;
    private Provider provider;
}
