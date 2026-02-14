package com.budgetcalendar.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.budgetcalendar.model.Payday;
import com.budgetcalendar.rowmappers.PaydayRowMapper;
import java.util.List;

@Repository
public class PaydayRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Payday> findByUserId(Long userId) {
        String sql = "SELECT * FROM paydays WHERE user_id = ? ORDER BY start_date";
        return jdbcTemplate.query(sql, new PaydayRowMapper(), userId);
    }

    public Payday findById(Long id) {
        String sql = "SELECT * FROM paydays WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new PaydayRowMapper(), id);
    }

    public Long save(Payday payday) {
        String sql = "INSERT INTO paydays (user_id, amount, frequency, start_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, payday.getUserId(), payday.getAmount(), 
                           payday.getFrequency(), payday.getStartDate());
        
        String getIdSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(getIdSql, Long.class);
    }

    public int update(Payday payday) {
        String sql = "UPDATE paydays SET amount = ?, frequency = ?, start_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, payday.getAmount(), payday.getFrequency(), 
                                  payday.getStartDate(), payday.getId());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM paydays WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
