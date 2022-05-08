package com.training.converter;

import com.training.dto.attachment.AttachmentDto;
import com.training.model.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentConverter implements Converter<AttachmentDto, Attachment> {

    @Override
    public AttachmentDto toDto(Attachment attachment) {
        if (attachment != null) {
            AttachmentDto attachmentDto = new AttachmentDto();
            attachmentDto.setName(attachment.getName());
            attachmentDto.setId(attachment.getId());
            return attachmentDto;
        } else {
            return null;
        }
    }

    @Override
    public Attachment toEntity(AttachmentDto attachmentDto) {
        if (attachmentDto != null) {
            Attachment attachment = new Attachment();
            attachment.setName(attachmentDto.getName());
            attachment.setId(attachmentDto.getId());
            return attachment;
        } else {
            return null;
        }
    }
}
