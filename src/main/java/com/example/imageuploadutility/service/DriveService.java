package com.example.imageuploadutility.service;

import com.example.imageuploadutility.model.FileInfo;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import com.example.marketComponents.service.ProductService;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.service.AppUserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DriveService {
    final
    AppUserService userService;
    final
    ProductService productService;
    String itemFolderId = "1szEE172WB3oNzQSpwGqEfeuaeUIHQ7UC";
    String userFolderId = "1aDkR3Os65yw2MeLzoU8BB4GE3y2oo_A9";

    GoogleCredential credential = GoogleCredential
            .fromStream (getClass ().getResourceAsStream ("/credentials.json"))
            .createScoped (Collections.singleton (DriveScopes.DRIVE));
    Drive driveService = new Drive.Builder (
            GoogleNetHttpTransport.newTrustedTransport (),
            JacksonFactory.getDefaultInstance (),
            credential)
            .setApplicationName ("easy shopper")
            .build ();
    public DriveService(AppUserService userService, ProductService productService) throws IOException, GeneralSecurityException {
        this.userService = userService;
        this.productService = productService;
    }

    public String uploadImageToDrive(java.io.File imageFile, int path, long id) {
        try {
            File fileMetadata = new File ();
            fileMetadata.setName (imageFile.getName ());
            fileMetadata.setParents(Collections.singletonList(path == 1 ? itemFolderId : userFolderId));
            FileContent mediaContent = new FileContent ("image/jpeg", imageFile);
            File uploadedFile = driveService.files ().create (fileMetadata, mediaContent)
                    .setFields ("id")
                    .execute ();
            String imageUrl = "https://drive.google.com/uc?export=download&id="+uploadedFile.getId ();
            if (path == 1) {
                Product product = productService.getProductById (id);
                product.setImageUrl (imageUrl);
                productService.updateProduct (id,product);
            }else {
                AppUser user = userService.getUser ((int)id);
                user.setPictureUrl (imageUrl);
                userService.updateUser ((int)id,user);
            }
            return "https://drive.google.com/uc?export=download&id="+uploadedFile.getId ();
        } catch (IOException e) {
            e.printStackTrace ();
            return "Image upload failed";
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }
    public List<FileInfo> retrieveFileInfosFromDrive() throws IOException {
        List<FileInfo> fileInfos = new ArrayList<>();
        FileList fileList = driveService.files().list().setQ("'" + this.itemFolderId + "' in parents").execute();
        List<File> files = fileList.getFiles();
        for (File file : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getName());
            fileInfo.setFileUrl("https://drive.google.com/file/d/" + file.getId());
            fileInfo.setFileId(file.getId());
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }
}