package com.example.agreement4be.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlaceholderLocationsDTO implements Serializable {

    private Long oldStartPosition;

    private Long oldEndPosition;

    private String oldPlaceholder;

    private Long newStartPosition;

    private Long newEndPosition;

    private String newPlaceholder;
}
