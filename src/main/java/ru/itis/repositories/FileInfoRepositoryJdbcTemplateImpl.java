package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.FileInfo;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class FileInfoRepositoryJdbcTemplateImpl implements FileInfoRepository {


    //language=SQL
    private final String SQL_INSERT_FILE_INFO = "INSERT INTO file_storage_info (storage_file_name, original_file_name, size, type, url) values (?, ?, ?, ?, ?);";
    //language=SQL
    private final String SQL_FIND_FILE_INFO_BY_NAME = "SELECT * FROM file_storage_info WHERE storage_file_name = ?;";
    //language=SQL
    private final String SQL_FIND_FILE_INFO_BY_ID = "SELECT * FROM file_storage_info WHERE id = ?;";
    //language=SQL
    private final String SQL_FIND_ALL = "SELECT * FROM file_storage_info;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<FileInfo> userRowMapper = (row, rowNumber) -> {
        return FileInfo.builder()
                .id(row.getLong("id"))
                .originalFileName(row.getString("original_file_name"))
                .storageFileName(row.getString("storage_file_name"))
                .size(row.getLong("size"))
                .type(row.getString("type"))
                .url(row.getString("url"))
                .build();
    };

    @Override
    public Optional<FileInfo> find(Long id) {
        try {
            FileInfo fileInfo = jdbcTemplate.queryForObject(SQL_FIND_FILE_INFO_BY_ID, new Object[]{id}, userRowMapper);
            return Optional.ofNullable(fileInfo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<FileInfo> find(String name) {
        try {
            FileInfo fileInfo = jdbcTemplate.queryForObject(SQL_FIND_FILE_INFO_BY_NAME, new Object[]{name}, userRowMapper);
            return Optional.ofNullable(fileInfo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<FileInfo> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public void update(FileInfo model) {

    }

    @Override
    public void save(FileInfo entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT_FILE_INFO);
            statement.setString(1, entity.getStorageFileName());
            statement.setString(2, entity.getOriginalFileName());
            statement.setLong(3, entity.getSize());
            statement.setString(4, entity.getType());
            statement.setString(5, entity.getUrl());
            return statement;
        }, keyHolder);

        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long aLong) {

    }
}
