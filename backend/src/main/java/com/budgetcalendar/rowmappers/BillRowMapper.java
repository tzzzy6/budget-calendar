package com.budgetcalendar.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import com.budgetcalendar.model.Bill;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillRowMapper implements RowMapper<Bill> {

    @Override
    public Bill mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Bill bill = new Bill();
        bill.setId(rs.getLong("id"));
        bill.setUserId(rs.getLong("user_id"));
        bill.setName(rs.getString("name"));
        bill.setAmount(rs.getBigDecimal("amount"));
        bill.setFrequency(rs.getString("frequency"));
        bill.setStartDate(rs.getDate("start_date").toLocalDate());
        bill.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        bill.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return bill;
    }
}
