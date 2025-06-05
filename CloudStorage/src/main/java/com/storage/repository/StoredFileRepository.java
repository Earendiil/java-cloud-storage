package com.storage.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.storage.entity.StoredFile;
import com.storage.entity.User;

public interface StoredFileRepository extends JpaRepository<StoredFile, UUID> {

	//this is needed in order NOT to load the DATA file when getting the info
	@Query("SELECT f.fileId AS fileId, f.fileName AS fileName, f.contentType AS contentType, f.size AS size, f.uploadDate AS uploadDate FROM StoredFile f WHERE f.owner = :user")
	List<StoredFileInfo> findAllFileInfoByOwner(@Param("user") User user);




}
