package com.company.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.library.book.BookVo;

@Service
public class BookService {
	
	@Autowired
	BookDao bookDao;

	public int registerBookConfirm(BookVo bookVo) {
		
		boolean isISBN = bookDao.isISBN(bookVo.getB_isbn());
		
		if(!isISBN) {
			int result = bookDao.insert(bookVo);
			
			if (result > 0) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return 0;
		}
	}

	public List<BookVo> searchBookConfirm(BookVo bookVo) {
		System.out.println("[BookService] searchBookConfirm()");
		
		return bookDao.selectBooksBySearch(bookVo);
	}

	public BookVo bookDetail(int b_no) {
		System.out.println("[BookService] bookDetail()");
		
		return bookDao.selectBook(b_no);
	}

}
