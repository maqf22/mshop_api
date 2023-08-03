package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods {
    private Long id;
    private Integer store;
    private BigDecimal price;
    private Date startTime;
    private Date endTime;

    public Long getEndTime() {
        return endTime.getTime();
    }

    public Long getStartTime() {
        return startTime.getTime();
    }
}
