package com.example.blog.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;

import org.springframework.stereotype.Service;

@Service
public class FileService {
    
    public Path createMarkdownFile(String title , String content) throws Exception {
        String markdownContent = Normalizer.normalize(content, Normalizer.Form.NFC);
        Path markdownFilePath = Paths.get(System.getProperty("java.io.tmpdir"), title + ".md");
        Files.write(markdownFilePath, markdownContent.getBytes(StandardCharsets.UTF_8));
        return markdownFilePath;
   }

   public void  deleteFile(Path path) throws Exception {
       Files.delete(path);
   }
}
