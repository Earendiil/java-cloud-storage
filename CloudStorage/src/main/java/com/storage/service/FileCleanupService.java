package com.storage.service;

import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.storage.entity.StoredFile;
import com.storage.repository.StoredFileRepository;

import jakarta.transaction.Transactional;

@Service
public class FileCleanupService {

    private final StoredFileRepository fileRepository;

    public FileCleanupService(StoredFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Scheduled(cron = "0 0 * * * *") // Every hour
    @Transactional
    public void deleteExpiredFiles() {
        Instant now = Instant.now();
        List<StoredFile> expiredFiles = fileRepository.findAllByExpiryDateBefore(now);

        for (StoredFile file : expiredFiles) {
            fileRepository.delete(file);
            // Optional: delete from filesystem/cloud if stored outside DB
        }

        if (!expiredFiles.isEmpty()) {
            System.out.println("Deleted " + expiredFiles.size() + " expired files.");
        }
    }
}

