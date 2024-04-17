package com.itembase.task.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionRequest {
    private String from;
    private String to;
    private Double amount;
}
