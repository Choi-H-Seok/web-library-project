package com.company.library.book.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.company.library.book.BookVo;

@Component
public class BookDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean isISBN(String b_isbn) {
		
		String sql = "SELECT COUNT(*) FROM tb1_book WHERE b_isbn = ?";
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, b_isbn);
		
		return (result > 0) ? true : false;
	}

	public int insert(BookVo bookVo) {
		
		String sql = "INSERT INTO tb1_book (b_thumbnail, b_name, b_author, "+
						"b_publisher, b_publish_year, b_isbn, " +
						"b_call_number, b_rental_able, " +
						"b_reg_date, b_mod_date)" + 
						"VALUES (?,?,?,?,?,?,?,?,NOW(),NOW())";
		
		int result = -1;
		
		result = jdbcTemplate.update(sql, bookVo.getB_thumbnail(),
										  bookVo.getB_name(),
										  bookVo.getB_author(),
										  bookVo.getB_publisher(),
										  bookVo.getB_publish_year(),
										  bookVo.getB_isbn(),
										  bookVo.getB_call_number(),
										  bookVo.getRental_able());
		
		
		return result;
	}}
