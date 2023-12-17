package com.vodafone.spring.batch.ImplService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Service
public class DirectoryListenerService {

    @Value("${directory}")
    private Path directoryPath;

    private  Path latestDetectedFile;

    public  Path getLatestDetectedFile() {
        return latestDetectedFile;
    }

    @Scheduled(fixedDelay = 15000) // 15 seconds
    public void monitorDirectory() {
        try {
            Files.walkFileTree(directoryPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    System.out.println("my file name is "+file.getFileName());
                    if (file.getFileName().toString().endsWith(".csv")) {
                        latestDetectedFile = file;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void renameFile(Path originalFilePath, String newFileName) {
        try {
            Path renamedFilePath = originalFilePath.resolveSibling(newFileName);
            Files.move(originalFilePath, renamedFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed to: " + renamedFilePath);
        } catch (IOException e) {
            System.err.println("Error renaming file: " + originalFilePath);
            e.printStackTrace();
        }
    }

    // Call this method when you want to rename the last detected file
    public void renameLastDetectedFile() {
        if (latestDetectedFile != null && Files.exists(latestDetectedFile)) {
            String temp= latestDetectedFile.toString()+".processed";
            renameFile(latestDetectedFile, temp);
        }
    }

}
