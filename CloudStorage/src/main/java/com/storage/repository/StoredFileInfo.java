package com.storage.repository;

import java.time.Instant;
import java.util.UUID;

public interface StoredFileInfo {
    UUID getFileId();
    String getFileName();
    String getContentType();
    Long getSize();
    Instant getUploadDate();
    Instant getExpiryDate();
}