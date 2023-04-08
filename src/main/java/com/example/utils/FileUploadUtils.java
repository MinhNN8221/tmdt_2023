package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {
    public static void saveFile(MultipartFile m, String fileName, String uploadDir) throws IOException {
    	Path uploadPath = Paths.get(uploadDir);
		//nếu dường dẫn chưa có thì tạo mới
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try(InputStream inputStream = m.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			//In ra đường dẫn tuyệt đối thư mục luuwu ảnh upload
			//System.out.println(filePath.toFile().getAbsolutePath());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
		    throw new IOException("ko lưu đc"+fileName);
		}
    }
}
