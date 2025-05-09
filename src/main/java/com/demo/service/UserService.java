package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.util.FileNameUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MinioService minioService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(String name, String email, String username, String password, MultipartFile imageFile) {
        try {
            String customFileName = FileNameUtil.getFileExtension(imageFile.getOriginalFilename());
            String imageUrl = minioService.uploadFile(imageFile, customFileName);

            // Hardcoded base URL to strip off (change this as per your configuration)
            String baseUrl = "http://localhost:9000/my-bucket/";

            // Extract the relative path (removing the base URL)
            String relativePath = imageUrl.replace(baseUrl, "");

            // Debugging output
            System.out.println("Full Image URL: " + imageUrl);
            System.out.println("Saved Relative Path: " + relativePath);

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setImageUrl(relativePath); // Save only the relative path

            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }

    public User updateUser(Long id, String name, String email, String username, String password, MultipartFile imageFile) {

        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            return null;
        }

        // Update the user details
        existingUser.setName(name);
        existingUser.setEmail(email);
        existingUser.setUsername(username);
        existingUser.setUsername(password);

        // Save the updated user back to the database
        return userRepository.save(existingUser);
    }
    // Method to handle saving the image (you can implement this as per your requirements)

    private String saveImage(MultipartFile imageFile) {
        // Save image logic here
        // For example, you can store the file in a directory and return the file path.
        return "image/path/to/file";
    }
}
