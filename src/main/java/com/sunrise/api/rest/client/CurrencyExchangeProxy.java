package com.sunrise.api.rest.client;

import com.sunrise.api.model.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @FeignClient(name = "currency-exchange-service", url = "localhost:8000")
@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeProxy {

    @GetMapping("/exchange-rates/rates-by-countries")
    public CurrencyConversion getConvertedValue(@RequestParam(name = "from") String from,
                                                @RequestParam(name = "to") String to);
}
