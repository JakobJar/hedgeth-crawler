package com.hedgeth.crawler.entity;

import com.google.common.base.MoreObjects;
import com.hedgeth.crawler.util.AddressUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Token {

    @EqualsAndHashCode.Include
    private final String address;
    private final String name;
    private final String symbol;
}
