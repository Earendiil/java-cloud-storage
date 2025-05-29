package com.storage.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storage.entity.StoredFile;

public interface StoredFileRepository extends JpaRepository<StoredFile, UUID> {



}
