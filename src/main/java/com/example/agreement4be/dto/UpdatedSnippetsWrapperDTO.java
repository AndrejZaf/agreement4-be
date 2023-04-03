package com.example.agreement4be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdatedSnippetsWrapperDTO implements Serializable {

    private List<UpdatedSnippetsDTO> updatedSnippets;
}
