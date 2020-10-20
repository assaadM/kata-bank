package com.kata.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String id;
    private double balance;
    @Builder.Default
    private List<Operation> operations = new ArrayList<>();
}
