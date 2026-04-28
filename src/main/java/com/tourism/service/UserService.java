package com.tourism.service;

import com.tourism.model.AuthResponse;
import com.tourism.model.User;
import com.tourism.repository.UserRepository;
import com.tourism.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CaptchaService captchaService;

    public User register(User user) {
        user.setStatus("ACTIVE");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = repo.save(user);

        if ("GUIDE".equals(savedUser.getRole())) {
            com.tourism.model.Guide guide = new com.tourism.model.Guide();
            guide.setUser(savedUser);
            guide.setName(savedUser.getName() != null ? savedUser.getName() : "Local Guide");
            guide.setContact("Update Contact");
            guide.setLocation("Anywhere");
            guide.setPricePerHour(20.0); // Default price
            guide.setWorkHours("9 AM - 5 PM");
            guide.setDailyLimitHours(8);
            guide.setAvailable(true);
            guide.setApproved(true); // Automatically approve to show on Guides page instantly
            guideRepo.save(guide);
        }

        return savedUser;
    }

    public AuthResponse login(User loginReq) {
        if (!captchaService.validateCaptcha(loginReq.getCaptchaId(), loginReq.getCaptchaAnswer())) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Invalid CAPTCHA");
        }

        Optional<User> user = repo.findByEmail(loginReq.getEmail());
        if(user.isPresent() && passwordEncoder.matches(loginReq.getPassword(), user.get().getPassword())) {
            if ("BANNED".equals(user.get().getStatus())) {
                throw new RuntimeException("User is banned");
            }
            String token = jwtUtil.generateToken(user.get().getEmail());
            return new AuthResponse(token, user.get());
        }
        throw new RuntimeException("Invalid credentials");
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Autowired private com.tourism.repository.BookingRepository bookingRepo;
    @Autowired private com.tourism.repository.GuideBookingRepository guideBookingRepo;
    @Autowired private com.tourism.repository.HomestayRepository homestayRepo;
    @Autowired private com.tourism.repository.GuideRepository guideRepo;

    @org.springframework.transaction.annotation.Transactional
    public void deleteUser(Long userId) {
        bookingRepo.deleteAll(bookingRepo.findByUserId(userId));
        guideBookingRepo.deleteAll(guideBookingRepo.findByUserId(userId));

        java.util.List<com.tourism.model.Homestay> homestays = homestayRepo.findByHostId(userId);
        for(com.tourism.model.Homestay h : homestays) {
            bookingRepo.deleteAll(bookingRepo.findByHomestayId(h.getId()));
            homestayRepo.delete(h);
        }

        com.tourism.model.Guide guide = guideRepo.findByUserId(userId);
        if (guide != null) {
            guideBookingRepo.deleteAll(guideBookingRepo.findByGuideId(guide.getId()));
            guideRepo.delete(guide);
        }

        repo.deleteById(userId);
    }

    public User updateUser(Long id, User updateReq) {
        User user = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(updateReq.getName());
        user.setEmail(updateReq.getEmail());
        if(updateReq.getPassword() != null && !updateReq.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateReq.getPassword()));
        }
        return repo.save(user);
    }
}
