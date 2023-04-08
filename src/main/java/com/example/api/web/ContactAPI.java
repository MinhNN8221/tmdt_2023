package com.example.api.web;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactAPI {
      
	/*@Autowired
	private JavaMailSender mailSender;
	
	@PostMapping(value = "/freshfood/contact")
	public Integer sendmail(@RequestBody ConactFormEntity conactFormEntity) throws MessagingException, UnsupportedEncodingException {
		String name = conactFormEntity.getName();
		String email = conactFormEntity.getEmail();
		String message = conactFormEntity.getMessage();
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true); //nếu dùng multipart thì tham số thứ 2 là true
		
	    //Gửi đến với tên linh
		helper.setFrom("nguyenhoailinh2207@gmail.com","linh");
		helper.setTo("nguyenhoailinh2207@gmail.com");
		

		String mimeSubject = name + "đã gửi lời nhắn";
		String mimeContent = "<p><b>Tên: <b/><i>"+name+"</i></p>";
	    mimeContent += "<p><b>Email: </b><i>"+email+"</i></p>";
		mimeContent += message;
		
		helper.setSubject(mimeSubject);
		//để cấu hình html , tham số thứ 2 là true
	    helper.setText(mimeContent, true);
		
	    mailSender.send(mimeMessage);
	    
		return 1;
	}*/
}
