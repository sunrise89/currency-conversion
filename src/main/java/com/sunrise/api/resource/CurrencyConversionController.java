package com.sunrise.api.resource;

import com.sunrise.api.model.CurrencyConversion;
import com.sunrise.api.rest.client.CurrencyExchangeProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("currency-conversions")
    public CurrencyConversion getConvertedValue(@RequestParam(name = "from") String from,
                                                @RequestParam(name = "to") String to,
                                                @RequestParam(name = "quantity") BigDecimal quantity) {
        logger.info("Retrieving conversions from {} to {}", from, to);
        CurrencyConversion currencyConversion = currencyExchangeProxy.getConvertedValue(from, to);
        currencyConversion.setConvertedValue(quantity.multiply(currencyConversion.getExchangeRate()));
        return currencyConversion;
    }

    @GetMapping("health")
    public ResponseEntity<Integer> checkHealth() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
