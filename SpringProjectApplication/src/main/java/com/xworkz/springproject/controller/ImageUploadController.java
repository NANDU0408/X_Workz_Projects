package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.user.ImageDownloadDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;


@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData"})
public class ImageUploadController {

    @Autowired
    private SignUpService signUpService;


    public ImageUploadController() {
        System.out.println("Created ImageUploadController");
    }

    @PostMapping("/uploadFile")
    public String updateDetailsAndUpload(@ModelAttribute("signUpDTO") SignUpDTO signUpDTO,
                                         @RequestParam("file") MultipartFile multipartFile,
                                         Model model) {
        System.out.println("Image upload process is initiated using DTO: " + signUpDTO);

        try {
            // Check if a new file is uploaded
            if (!multipartFile.isEmpty()) {
                // Generate a unique file name
                String fileName = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + multipartFile.getOriginalFilename();
                long fileSize = multipartFile.getSize();
                String fileType = multipartFile.getContentType();

                // Save the uploaded file to the file system
                Path path = Paths.get("C:\\Users\\Admin\\OneDrive\\Desktop\\uplodedImages\\" + fileName);
                Files.write(path, multipartFile.getBytes());

                // Retrieve user details from session
                SignUpDTO signUpDTOOfSession = (SignUpDTO) model.getAttribute("userData");

                // Mark existing active image as inactive in database
                List<ImageDownloadDTO> activeImages = signUpService.findByUserIdAndStatus(signUpDTOOfSession.getId(), "ACTIVE");
                for (ImageDownloadDTO activeImage : activeImages) {
                    System.out.println("Running Inactive in controller"+activeImage);
                    activeImage.setStatus("INACTIVE");
                    System.out.println("Running Inactive after inactivating in controller"+activeImage);
                    signUpService.saveImageDetails(activeImage);
                }

                // Create ImageDownloadDTO for the new image
                ImageDownloadDTO imageDownloadDTO = new ImageDownloadDTO();
                System.out.println("Before activating in controller"+imageDownloadDTO);
                imageDownloadDTO.setImageName(fileName);
                imageDownloadDTO.setImageSize(fileSize);
                imageDownloadDTO.setImageType(fileType);
                imageDownloadDTO.setCreatedBy(signUpDTO.getFirstName() + " " + signUpDTO.getLastName());
                imageDownloadDTO.setCreatedAt(LocalDateTime.now());
                imageDownloadDTO.setStatus("ACTIVE");
                imageDownloadDTO.setUserId(signUpDTOOfSession.getId());
                signUpService.saveImageDetails(imageDownloadDTO);
                System.out.println("After activating in controller" +imageDownloadDTO);

                // Add ImageDownloadDTO to the model for further processing or display
                model.addAttribute("imageDownloadDTO", imageDownloadDTO);
            }

            // Update user details in the database using the service
            SignUpDTO updatedDTO = signUpService.updateUserDetails(signUpDTO.getEmailAddress(), signUpDTO);

            if (updatedDTO != null) {
                model.addAttribute("message", "Profile updated successfully");
            } else {
                model.addAttribute("message", "Profile update failed. User not found");
            }

            // Add updated DTO and other attributes to the model
            model.addAttribute("signUpDTO", signUpDTO);
        } catch (Exception e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
            e.printStackTrace();
        }

        return "registration/Edit.jsp";
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, Model model) throws IOException {

        ImageDownloadDTO imageDownloadDTO = (ImageDownloadDTO) model.getAttribute("userImageData");
        System.out.println("Running downloading Image" +imageDownloadDTO);

        if (imageDownloadDTO == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path path = Paths.get("C:\\Users\\Admin\\OneDrive\\Desktop\\uplodedImages\\" + imageDownloadDTO.getImageName());

        // Check if the file exists
        if (!Files.exists(path)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(imageDownloadDTO.getImageType());
        response.setBufferSize((int) imageDownloadDTO.getImageSize());
        Files.copy(path, response.getOutputStream());
    }

}
