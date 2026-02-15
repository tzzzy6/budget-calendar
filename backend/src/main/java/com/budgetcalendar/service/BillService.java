package com.budgetcalendar.service;

import com.budgetcalendar.dto.BillRequest;
import com.budgetcalendar.dto.BillResponse;
import com.budgetcalendar.model.Bill;
import com.budgetcalendar.repos.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {
    
    @Autowired
    private BillRepository billRepository;
    
    public BillResponse createBill(Long userId, BillRequest request) {
        Bill bill = new Bill();
        bill.setUserId(userId);
        bill.setName(request.getName());
        bill.setAmount(request.getAmount());
        bill.setFrequency(request.getFrequency());
        bill.setStartDate(request.getStartDate());
        
        Long id = billRepository.save(bill);
        bill = billRepository.findById(id);
        
        return mapToResponse(bill);
    }
    
    public List<BillResponse> getUserBills(Long userId) {
        List<Bill> bills = billRepository.findByUserId(userId);
        return bills.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public BillResponse getBill(Long id, Long userId) {
        Bill bill = billRepository.findById(id);
        if (bill == null || !bill.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Bill not found");
        }
        return mapToResponse(bill);
    }
    
    public BillResponse updateBill(Long id, Long userId, BillRequest request) {
        Bill bill = billRepository.findById(id);
        if (bill == null || !bill.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Bill not found");
        }
        
        bill.setName(request.getName());
        bill.setAmount(request.getAmount());
        bill.setFrequency(request.getFrequency());
        bill.setStartDate(request.getStartDate());
        
        billRepository.update(bill);
        bill = billRepository.findById(id);
        
        return mapToResponse(bill);
    }
    
    public void deleteBill(Long id, Long userId) {
        Bill bill = billRepository.findById(id);
        if (bill == null || !bill.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Bill not found");
        }
        billRepository.delete(id);
    }
    
    private BillResponse mapToResponse(Bill bill) {
        return new BillResponse(
            bill.getId(),
            bill.getName(),
            bill.getAmount(),
            bill.getFrequency(),
            bill.getStartDate(),
            bill.getCreatedAt(),
            bill.getUpdatedAt()
        );
    }
}
