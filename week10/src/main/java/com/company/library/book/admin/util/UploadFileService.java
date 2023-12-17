package com.company.library.book.admin.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	public String upload(MultipartFile file) {
		boolean result = false;
		
		//업로드한 원본 파일명
		String fileOriName = file.getOriginalFilename();
		
		//파일 경로에서 확장명을 추출
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."),
											fileOriName.length());
		
		//파일 저장 위치
		String uploadDir = "/Users/choehyeonseog/Upload/";
		
		//파일의 유일한 식별자 생성 (UUID)
		UUID uuid = UUID.randomUUID();
		
		String uniqueName = uuid.toString().replace("-", "");
		
		//실제 저장할 파일 객체 생성
		File saveFile = new File(uploadDir + uniqueName + fileExtension);
		
		// 파일이 저장될 디렉터리가 있는지 체크
		if (!saveFile.exists())
			saveFile.mkdir();
		
		try {
			file.transferTo(saveFile);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (result) {
			System.out.println("FILE UPLOAD SUCCES");
			return uniqueName + fileExtension;
			
		} else {
			System.out.println("FILE UPLOAD FAIL");
			return null;
		}
		
	}

}
