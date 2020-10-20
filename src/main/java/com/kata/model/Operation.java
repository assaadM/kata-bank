package com.kata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Operation {
    private LocalDateTime date;
    private OperationType type;
    private double amount;
    private double balance;
}
