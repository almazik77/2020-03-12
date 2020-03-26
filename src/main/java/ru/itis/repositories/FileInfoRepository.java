package ru.itis.repositories;

import ru.itis.models.FileInfo;

import java.util.Optional;

public interface FileInfoRepository extends CrudRepository<Long, FileInfo> {
    Optional<FileInfo> find(String name);
}
