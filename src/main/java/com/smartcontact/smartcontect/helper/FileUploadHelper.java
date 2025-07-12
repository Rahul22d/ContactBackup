package com.smartcontact.smartcontect.helper;


import java.io.File;
// import java.io.FileOutputStream;
import java.io.IOException;
// import java.io.InputStream;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
    
    private final String uploadDir = "D:\\c\\Api\\smartcontect\\src\\main\\resources\\static\\image";
    // private final String uploadDir = new ClassPathResource("/static/image").getFile().getAbsolutePath();

    /* ClassPathResouece thror IOException
     * @error Default constructor cannot handle exception type IOException thrown by implicit super constructor. 
     * Must define an explicit constructor
     * 
     * Create default constructor whith throws IOException
     */
    public FileUploadHelper() throws IOException {

    }
    
    public String uploadFile(MultipartFile multipart) {
        
        try {
            // // for stream file data
            // InputStream stream = multipart.getInputStream();
            // //save all btye in array
            // byte data[] = new byte[stream.available()];
            // // read all data
            // stream.read(data);

            // // create path in image file with original name
            // FileOutputStream file = new FileOutputStream(uploadDir + File.separator + multipart.getOriginalFilename());
            // // write data in file
            // file.write(data);
            // // flush data 
            // file.flush();
            // // close file
            // file.close();

            // // close stream
            // stream.close();

            /*
             * for shortcut 
             */
            // Files.copy(multipart.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);
            
           
            Files.copy(multipart.getInputStream(), Paths.get(uploadDir+ File.separator + multipart.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("image uplaoaded");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        } 

        return uploadDir+ File.separator + multipart.getOriginalFilename();
    }
}
