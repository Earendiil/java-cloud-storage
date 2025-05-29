package com.storage.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storage.entity.StoredFile;
import com.storage.entity.User;

public interface StoredFileRepository extends JpaRepository<StoredFile, UUID> {


	List<StoredFile> findAllByOwner(User user);



}
