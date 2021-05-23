package com.eliasfb.bgn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConstantsDto {
    private String lastBuyDate;
    private Long numDaysSinceLastBuy;
}
