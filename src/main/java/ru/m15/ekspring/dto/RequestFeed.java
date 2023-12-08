package ru.m15.ekspring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
//import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 *  format & object for external request get/add_feed
 */

@Data
//@Builder
@Accessors(chain = true)
public class RequestFeed {

    public RequestFeed(){

    }

    @NotBlank
    private String feedUrl;
    private String durationTime;

}
