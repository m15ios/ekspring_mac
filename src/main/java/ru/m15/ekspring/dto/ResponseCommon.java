package ru.m15.ekspring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * regular API answer
 */

@Data
@AllArgsConstructor
public class ResponseCommon {

    @NotNull
    private Integer stateCode;
    @NotNull
    private String stateDescr;

}
