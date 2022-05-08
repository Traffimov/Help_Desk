package com.training.validators;

import com.training.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AttachmentValidator {

    private static final int MAX_FILE_SIZE = 5242880;

    private static final List<String> fileExtensions = List.of(".pdf", ".doc", ".docx", ".png", ".jpeg", ".jpg");

    public void validateFileAttachment(MultipartFile multipartFile) {

        String fileName = multipartFile.getOriginalFilename();
        int lastIndex = Objects.requireNonNull(fileName).lastIndexOf('.');
        String fileExtension = fileName.substring(lastIndex);

        if (multipartFile.getSize() >= MAX_FILE_SIZE) {
            throw new ValidationException("The size of the attached file should not be greater than 5 Mb. Please select another file");
        } else if (!fileExtensions.contains(fileExtension)) {
            throw new ValidationException("The selected file type is not allowed. Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg.");
        }
    }
}
