package com.budgetcalendar.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class PaymentCard {
    private Long id;
    private Long billId;
    private Long userId;
    private String name;
    private BigDecimal amount;
    private LocalDate calculatedDueDate;
    private LocalDate plannedPaymentDate;
    private Boolean isOneTime;
    private Boolean isPaid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getCalculatedDueDate() {
        return calculatedDueDate;
    }

    public void setCalculatedDueDate(LocalDate calculatedDueDate) {
        this.calculatedDueDate = calculatedDueDate;
    }

    public LocalDate getPlannedPaymentDate() {
        return plannedPaymentDate;
    }

    public void setPlannedPaymentDate(LocalDate plannedPaymentDate) {
        this.plannedPaymentDate = plannedPaymentDate;
    }

    public Boolean getIsOneTime() {
        return isOneTime;
    }

    public void setIsOneTime(Boolean isOneTime) {
        this.isOneTime = isOneTime;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
