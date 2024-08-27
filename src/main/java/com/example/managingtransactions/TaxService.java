package com.example.managingtransactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TaxService {

    private final static Logger logger = LoggerFactory.getLogger(TaxService.class);

    private final JdbcTemplate jdbcTemplate;

    public TaxService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void tax(){
        logger.info("Adding tex...");
        jdbcTemplate.update("insert into TAX(AMOUNT) values (?)", 1);
        logger.info("Current total tax: " + totalTax());
    }

    public int totalTax(){
        List<Integer> total = jdbcTemplate.query("select sum(AMOUNT) from tax", (rs, rowNum) -> rs.getInt(1));
        return total.get(0);
    }

}
