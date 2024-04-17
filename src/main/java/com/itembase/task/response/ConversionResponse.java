package com.itembase.task.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionResponse {
    private String from;
    private String to;
    private Double amount;
    private Double converted;
}
