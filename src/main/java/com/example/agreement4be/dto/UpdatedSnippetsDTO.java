package com.example.agreement4be.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdatedSnippetsDTO implements Serializable {

    private Long id;

    private List<PlaceholderLocationsDTO> positions;
}
