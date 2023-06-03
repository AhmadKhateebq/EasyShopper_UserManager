package com.example.imageuploadutility.service;

import com.example.imageuploadutility.model.FileInfo;
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
    String itemFolderId = "1szEE172WB3oNzQSpwGqEfeuaeUIHQ7UC";
    String userFolderId = "1aDkR3Os65yw2MeLzoU8BB4GE3y2oo_A9";

    GoogleCredential credential = GoogleCredential
            .fromStream (getClass ().getResourceAsStream ("/credentials.json"))
            .createScoped (Collections.singleton (DriveScopes.DRIVE));
    Drive driveService = new Drive.Builder (
            GoogleNetHttpTransport.newTrustedTransport (),
            JacksonFactory.getDefaultInstance (),
            credential)
            .setApplicationName ("Your Application Name")
            .build ();
    public DriveService() throws IOException, GeneralSecurityException {
    }

    public String uploadImageToDrive(java.io.File imageFile,int path) {
        try {
            File fileMetadata = new File ();
            fileMetadata.setName (imageFile.getName ());
            fileMetadata.setParents(Collections.singletonList(path == 1 ? itemFolderId : userFolderId));
            FileContent mediaContent = new FileContent ("image/jpeg", imageFile);
            File uploadedFile = driveService.files ().create (fileMetadata, mediaContent)
                    .setFields ("id")
                    .execute ();
            return "https://drive.google.com/file/d/"+uploadedFile.getId ();
        } catch (IOException e) {
            e.printStackTrace ();
            return "Image upload failed";
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