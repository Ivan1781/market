package com.market.api.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PhotoResponseDto implements Serializable {
    @JsonProperty("name")
    private String name;
}
