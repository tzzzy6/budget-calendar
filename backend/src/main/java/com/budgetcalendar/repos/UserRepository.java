package com.budgetcalendar.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.budgetcalendar.model.User;
import com.budgetcalendar.rowmappers.UserRowMapper;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), email);
        return users.isEmpty() ? null : users.get(0);
    }

    public Long save(User user) {
        String sql = "INSERT INTO users (email, password_hash) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPasswordHash());
        
        String getIdSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(getIdSql, Long.class);
    }

    public int update(User user) {
        String sql = "UPDATE users SET email = ?, password_hash = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getEmail(), user.getPasswordHash(), user.getId());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
