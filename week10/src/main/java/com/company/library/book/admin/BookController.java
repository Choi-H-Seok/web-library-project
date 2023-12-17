package com.company.library.book.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.company.library.book.BookVo;
import com.company.library.book.admin.util.UploadFileService;

@Controller
@RequestMapping("/book/admin")
public class BookController {
	
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	BookService bookService;
	
	//도서 등록
	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		String nextPage = "admin/book/register_book_form";
		
		return nextPage;
	}
	
	//도서 등록 처리
	@PostMapping("/registerBookConfirm")
	public String registerBookConfirm(BookVo bookVo,
			@RequestParam("file") MultipartFile file) {
		
		String nextPage = "admin/book/register_book_ok";
		
		//파일 저장
		String saveFileName = uploadFileService.upload(file);
		
		if (saveFileName != null) {
			//파일 저장 ok
			bookVo.setB_thumbnail(saveFileName);
			int result = bookService.registerBookConfirm(bookVo);
			
			if (result <= 0) {
				nextPage = "admin/book/register_book_ng";
			}
			
		} else {
			nextPage = "admin/book/register_book_ng";
		}
		
		return nextPage;
	}
}
