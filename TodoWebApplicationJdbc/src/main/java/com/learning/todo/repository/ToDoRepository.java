package com.learning.todo.repository;

import com.learning.todo.domain.ToDo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private static final String SQL_INSERT = "insert into todo (id, " +
            " description, created, modified, completed) values (:id,:description, :created,:modified,:completed)";
    private static final String SQL_QUERY_FIND_ALL = "select id, description, created, modified, completed from todo";
    private static final String SQL_QUERY_FIND_BY_ID = SQL_QUERY_FIND_ALL + " where id = :id";
    private static final String SQL_UPDATE = "update todo " +
            " set description = :description, modified = :modified, completed = :completed " +
            " where id = :id";
    private static final String SQL_DELETE = "delete from todo where id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private RowMapper<ToDo> toDoRowMapper = (ResultSet rs, int rowNum) -> {
        ToDo toDo = new ToDo();
        toDo.setId(rs.getString("id"));
        toDo.setDescription(rs.getString("description"));
        toDo.setModified(rs.getTimestamp("modified").toLocalDateTime());
        toDo.setCreated(rs.getTimestamp("created").toLocalDateTime());
        toDo.setCompleted(rs.getBoolean("completed"));
        return toDo;
    };

    public ToDoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ToDo save(ToDo domain) {
        ToDo result = findById(domain.getId());
        if (null != result) {
            result.setModified(LocalDateTime.now());
            result.setDescription(domain.getDescription());
            result.setCompleted(domain.isCompleted());
            return insertUpdate(result, SQL_UPDATE);
        }

        return insertUpdate(result, SQL_INSERT);
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        domains.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo domain) {
        Map<String, Object> namedParameters = Collections.singletonMap("id", domain.getId());
        jdbcTemplate.update(SQL_DELETE, namedParameters);
    }

    @Override
    public ToDo findById(String id) {
       try{
           Map<String, Object> namedParameters = Collections.singletonMap("id", id);
           return jdbcTemplate.queryForObject(SQL_QUERY_FIND_BY_ID, namedParameters, toDoRowMapper);
       } catch (EmptyResultDataAccessException e){
           return null;
       }
    }

    @Override
    public Iterable<ToDo> findAll() {
        return jdbcTemplate.query(SQL_QUERY_FIND_ALL, toDoRowMapper);
    }

    private ToDo insertUpdate(final ToDo toDo, final String sql) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("id", toDo.getId());
        namedParameters.put("description", toDo.getDescription());
        namedParameters.put("created", Timestamp.valueOf(toDo.getCreated()));
        namedParameters.put("modified", Timestamp.valueOf(toDo.getModified()));
        namedParameters.put("completed", toDo.isCompleted());

        jdbcTemplate.update(sql, namedParameters);
        return findById(toDo.getId());
    }
}
