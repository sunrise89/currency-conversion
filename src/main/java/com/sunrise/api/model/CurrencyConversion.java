package com.sunrise.api.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CurrencyConversion {
    private Long id;
    private String from;
    private String to;
    private BigDecimal exchangeRate;
    private String environment;
    private BigDecimal convertedValue;
}
