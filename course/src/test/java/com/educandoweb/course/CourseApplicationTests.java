package com.educandoweb.course;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CourseApplicationTests {

	@Autowired
	private JdbcTemplate jdbc;

	@Test
	void contextLoads() {
	}

	@Test
	void seededPaymentSharesOrderIdAndUnpaidOrdersRemainValid() {
		assertEquals(1L, jdbc.queryForObject("select count(*) from tb_payment", Long.class));
		assertEquals(1L, jdbc.queryForObject("""
				select count(*) from tb_payment p join tb_order o on p.order_id = o.id
				where p.moment = timestamp with time zone '2019-06-20 21:53:07+00:00'
				""", Long.class));
		assertEquals(2L, jdbc.queryForObject("""
				select count(*) from tb_order o left join tb_payment p on p.order_id = o.id
				where p.order_id is null
				""", Long.class));
		assertEquals(0L, jdbc.queryForObject("""
				select count(*) from information_schema.columns
				where table_name = 'TB_ORDER' and column_name = 'PAYMENT'
				""", Long.class));
	}

}
