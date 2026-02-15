package com.budgetcalendar.service;

import com.budgetcalendar.dto.PaydayRequest;
import com.budgetcalendar.dto.PaydayResponse;
import com.budgetcalendar.model.Payday;
import com.budgetcalendar.repos.PaydayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaydayService {
    
    @Autowired
    private PaydayRepository paydayRepository;
    
    public PaydayResponse createPayday(Long userId, PaydayRequest request) {
        Payday payday = new Payday();
        payday.setUserId(userId);
        payday.setAmount(request.getAmount());
        payday.setFrequency(request.getFrequency());
        payday.setStartDate(request.getStartDate());
        
        Long id = paydayRepository.save(payday);
        payday = paydayRepository.findById(id);
        
        return mapToResponse(payday);
    }
    
    public List<PaydayResponse> getUserPaydays(Long userId) {
        List<Payday> paydays = paydayRepository.findByUserId(userId);
        return paydays.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public PaydayResponse getPayday(Long id, Long userId) {
        Payday payday = paydayRepository.findById(id);
        if (payday == null || !payday.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Payday not found");
        }
        return mapToResponse(payday);
    }
    
    public PaydayResponse updatePayday(Long id, Long userId, PaydayRequest request) {
        Payday payday = paydayRepository.findById(id);
        if (payday == null || !payday.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Payday not found");
        }
        
        payday.setAmount(request.getAmount());
        payday.setFrequency(request.getFrequency());
        payday.setStartDate(request.getStartDate());
        
        paydayRepository.update(payday);
        payday = paydayRepository.findById(id);
        
        return mapToResponse(payday);
    }
    
    public void deletePayday(Long id, Long userId) {
        Payday payday = paydayRepository.findById(id);
        if (payday == null || !payday.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Payday not found");
        }
        paydayRepository.delete(id);
    }
    
    private PaydayResponse mapToResponse(Payday payday) {
        return new PaydayResponse(
            payday.getId(),
            payday.getAmount(),
            payday.getFrequency(),
            payday.getStartDate(),
            payday.getCreatedAt(),
            payday.getUpdatedAt()
        );
    }
}
