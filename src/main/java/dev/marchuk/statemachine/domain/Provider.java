package dev.marchuk.statemachine.domain;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class Provider {
    private Integer id;
    private String name;
    private String email;
}
