package com.budgetcalendar.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import com.budgetcalendar.model.Payday;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaydayRowMapper implements RowMapper<Payday> {

    @Override
    public Payday mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Payday payday = new Payday();
        payday.setId(rs.getLong("id"));
        payday.setUserId(rs.getLong("user_id"));
        payday.setAmount(rs.getBigDecimal("amount"));
        payday.setFrequency(rs.getString("frequency"));
        payday.setStartDate(rs.getDate("start_date").toLocalDate());
        payday.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        payday.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return payday;
    }
}
