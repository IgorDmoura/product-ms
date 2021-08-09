package com.compasso.teste.productms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDTO {

    @JsonProperty("status_code")
    private int statusCode;

    private String message;
}