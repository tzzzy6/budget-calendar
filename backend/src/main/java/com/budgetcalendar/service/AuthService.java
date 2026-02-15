package com.budgetcalendar.service;

import com.budgetcalendar.dto.SignupRequest;
import com.budgetcalendar.model.User;
import com.budgetcalendar.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User signup(SignupRequest request) throws Exception {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        String hashedPassword = hashPassword(request.getPassword());
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(hashedPassword);
        
        Long userId = userRepository.save(user);
        user.setId(userId);
        
        return user;
    }
    
    public User authenticate(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        
        if (!verifyPassword(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        
        return user;
    }
    
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        
        byte[] saltAndHash = new byte[salt.length + hashedPassword.length];
        System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
        System.arraycopy(hashedPassword, 0, saltAndHash, salt.length, hashedPassword.length);
        
        return Base64.getEncoder().encodeToString(saltAndHash);
    }
    
    private boolean verifyPassword(String password, String storedHash) throws NoSuchAlgorithmException {
        byte[] saltAndHash = Base64.getDecoder().decode(storedHash);
        
        byte[] salt = new byte[16];
        System.arraycopy(saltAndHash, 0, salt, 0, 16);
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        
        for (int i = 0; i < hashedPassword.length; i++) {
            if (saltAndHash[i + 16] != hashedPassword[i]) {
                return false;
            }
        }
        
        return true;
    }
}
