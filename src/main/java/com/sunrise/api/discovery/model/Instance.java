package com.sunrise.api.discovery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Instance {
    private String serviceName;
    private String host;
    private Integer port;
}
