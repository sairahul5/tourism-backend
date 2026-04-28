package com.tourism.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CaptchaService {

    private final Map<String, String> captchaStore = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public String generateCaptchaText(int length) {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // Removed confusing chars like 1, I, O, 0
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public String generateCaptchaImage(String text) {
        int width = 160;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Noise
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 20; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        // Text
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        int x = 20;
        for (char c : text.toCharArray()) {
            g.drawString(String.valueOf(c), x, 35);
            x += 25;
        }

        g.dispose();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate CAPTCHA image", e);
        }
    }

    public com.tourism.dto.CaptchaResponse createCaptcha() {
        String text = generateCaptchaText(5);
        String id = UUID.randomUUID().toString();
        captchaStore.put(id, text);
        String base64Image = generateCaptchaImage(text);
        return new com.tourism.dto.CaptchaResponse(id, base64Image);
    }

    public boolean validateCaptcha(String id, String answer) {
        if (id == null || answer == null) return false;
        String storedText = captchaStore.remove(id); // Remove immediately after check to prevent reuse
        return storedText != null && storedText.equalsIgnoreCase(answer.trim());
    }
}
