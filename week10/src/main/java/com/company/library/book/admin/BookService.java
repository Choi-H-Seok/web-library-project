package com.company.library.book.admin;

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

}
