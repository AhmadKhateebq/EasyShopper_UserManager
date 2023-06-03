package com.example.imageuploadutility.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    private String fileName;
    private String fileId;
    private String fileUrl;

}
