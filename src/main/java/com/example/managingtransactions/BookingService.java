package com.example.managingtransactions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookingService {

	private final static Logger logger = LoggerFactory.getLogger(BookingService.class);

	private final JdbcTemplate jdbcTemplate;
	private final TaxService taxService;

	public BookingService(JdbcTemplate jdbcTemplate, TaxService taxService) {
		this.jdbcTemplate = jdbcTemplate;
		this.taxService = taxService;
	}

	@Transactional
	public void book(String... persons) {
		for (String person : persons) {
			logger.info("Booking " + person + " in a seat...");
			taxService.tax();
			jdbcTemplate.update("insert into BOOKINGS(FIRST_NAME) values (?)", person);
		}
	}

	public List<String> findAllBookings() {
		return jdbcTemplate.query("select FIRST_NAME from BOOKINGS",
				(rs, rowNum) -> rs.getString("FIRST_NAME"));
	}
}