package com.example.imageuploadutility.controller;

import com.example.imageuploadutility.model.FileInfo;
import com.example.imageuploadutility.service.DriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class DriveController {
    @Autowired
    DriveService service ;
    @PostMapping("/upload/{path}/{id}")
    public String uploadImage(@RequestParam("image") MultipartFile imageFile, @PathVariable int path,@PathVariable long id) {
        try {
            System.out.println (imageFile.getOriginalFilename ());
            String filePath = "C:\\Users\\Ahmad Khateeb\\Pictures\\uploadedimages\\" + imageFile.getOriginalFilename (); // Replace with your desired file path
            File destinationFile = new File (filePath);
            FileCopyUtils.copy (imageFile.getBytes (), destinationFile);
            String imageId = service.uploadImageToDrive (destinationFile,path,id);
            destinationFile.delete ();
            System.out.println (imageId);
            return imageId;
        } catch (IOException e) {
            e.printStackTrace ();
            System.out.println ("image upload failed");
            return "Failed to upload image";
        }
    }
    @GetMapping("/files")
    public List<FileInfo> getFilesFromDrive() throws IOException {
        return service.retrieveFileInfosFromDrive ();
    }
}
