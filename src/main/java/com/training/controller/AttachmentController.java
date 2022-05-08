package com.training.controller;

import com.training.model.Attachment;
import com.training.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;

@RestController
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/attachments/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id) {

        Attachment attachment = attachmentService.getById(id);
        if (attachment != null) {
            MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
            String mimeType = fileTypeMap.getContentType(attachment.getName());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                    .body(new ByteArrayResource(attachment.getBlob()));
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ticket/{id}/attachments")
    public ResponseEntity<Void> addAttachment(@RequestParam MultipartFile multipartFile,
                                              @PathVariable Long id) {
        attachmentService.saveAttachment(id, multipartFile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.noContent().build();
    }

}
