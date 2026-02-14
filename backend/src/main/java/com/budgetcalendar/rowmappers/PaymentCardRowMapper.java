package com.budgetcalendar.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import com.budgetcalendar.model.PaymentCard;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentCardRowMapper implements RowMapper<PaymentCard> {

    @Override
    public PaymentCard mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        PaymentCard card = new PaymentCard();
        card.setId(rs.getLong("id"));
        card.setBillId(rs.getObject("bill_id") != null ? rs.getLong("bill_id") : null);
        card.setUserId(rs.getLong("user_id"));
        card.setName(rs.getString("name"));
        card.setAmount(rs.getBigDecimal("amount"));
        card.setCalculatedDueDate(rs.getDate("calculated_due_date").toLocalDate());
        
        if (rs.getDate("planned_payment_date") != null) {
            card.setPlannedPaymentDate(rs.getDate("planned_payment_date").toLocalDate());
        }
        
        card.setIsOneTime(rs.getBoolean("is_one_time"));
        card.setIsPaid(rs.getBoolean("is_paid"));
        card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        card.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return card;
    }
}
