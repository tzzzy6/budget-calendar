package com.budgetcalendar.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.budgetcalendar.model.Bill;
import com.budgetcalendar.rowmappers.BillRowMapper;
import java.util.List;

@Repository
public class BillRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Bill> findByUserId(Long userId) {
        String sql = "SELECT * FROM bills WHERE user_id = ? ORDER BY start_date";
        return jdbcTemplate.query(sql, new BillRowMapper(), userId);
    }

    public Bill findById(Long id) {
        String sql = "SELECT * FROM bills WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BillRowMapper(), id);
    }

    public Long save(Bill bill) {
        String sql = "INSERT INTO bills (user_id, name, amount, frequency, start_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, bill.getUserId(), bill.getName(), bill.getAmount(), 
                           bill.getFrequency(), bill.getStartDate());
        
        String getIdSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(getIdSql, Long.class);
    }

    public int update(Bill bill) {
        String sql = "UPDATE bills SET name = ?, amount = ?, frequency = ?, start_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, bill.getName(), bill.getAmount(), 
                                  bill.getFrequency(), bill.getStartDate(), bill.getId());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM bills WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
