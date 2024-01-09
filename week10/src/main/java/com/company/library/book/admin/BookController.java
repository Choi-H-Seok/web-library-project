package com.company.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVo bookVo, Model model) {
		System.out.println("[UserBookController] searchBookConfirm()");
		
		String nextPage = "admin/book/search_book";
		
		List<BookVo> bookVos = bookService.searchBookConfirm(bookVo);
		
		model.addAttribute("bookVos", bookVos);
		
		return nextPage;
	}
	
	@GetMapping("/bookDetail")
	public String bookDetail(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[UserBookController] bookDetail()");
		
		String nextPage = "admin/book/book_detail";
		
		BookVo bookVo =  bookService.bookDetail(b_no);
		
		model.addAttribute("bookVo", bookVo);
		
		return nextPage;
	}
	
	@GetMapping("/modifyBookForm")
	public String modifyBook(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[UserBookController] modifyBook()");
		
		String nextPage = "admin/book/modify_book_form";
		
		BookVo bookVo = bookService.bookUpdate(b_no);
		
		model.addAttribute("bookVo",bookVo);
		
		return nextPage;
	}
	
	@PostMapping("/modifyBookConfirm")
	public String modifyBookConfirm(BookVo bookVo,
			@RequestParam("file") MultipartFile file) {
		System.out.println("[UserBookController] modifyBookConfirm()");
		
		String nextPage = "admin/book/modify_book_ok";
		
		if (!file.getOriginalFilename().equals("")) {
			
			String savedFileName = uploadFileService.upload(file);
			
			if (savedFileName != null) 
				bookVo.setB_thumbnail(savedFileName);
			}
			
			int result = bookService.modifyBookConfirm(bookVo);
			
			if (result <= 0)
				nextPage = "admin/book/modify_book_ng";
			
			return nextPage;
		}
}
