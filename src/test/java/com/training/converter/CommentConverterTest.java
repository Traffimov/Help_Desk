package com.training.converter;

import com.training.dto.comment.CommentDto;
import com.training.dto.user.UserDto;
import com.training.model.Comment;
import com.training.model.User;
import com.training.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentConverterTest {

    @InjectMocks
    private CommentConverter commentConverter;

    @Mock
    private UserConverter userConverter;

    @Mock
    private UserService userService;

    @Test
    void toCommentDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("goof@ggdsg.com");
        userDto.setFirstName("FirstName");
        userDto.setFirstName("LastName");

        User user = new User();
        user.setEmail("goof@ggdsg.com");
        user.setFirstName("FirstName");
        user.setFirstName("LastName");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("GG");
        comment.setUser(user);

        Mockito.when(userConverter.toDto(comment.getUser())).thenReturn(userDto);

        CommentDto actual = commentConverter.toDto(comment);

        Assertions.assertEquals(comment.getText(), actual.getText());
        Assertions.assertEquals(comment.getUser().getEmail(), actual.getUserDto().getEmail());
        Assertions.assertEquals(comment.getUser().getLastName(), actual.getUserDto().getLastName());
        Assertions.assertEquals(comment.getUser().getFirstName(), actual.getUserDto().getFirstName());
    }

    @Test
    void toComment() {

        UserDto userDto = new UserDto();
        userDto.setEmail("goof@ggdsg.com");
        userDto.setFirstName("FirstName");
        userDto.setFirstName("LastName");

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText("GG");
        commentDto.setUserDto(userDto);

        User user = new User();
        user.setEmail("goof@ggdsg.com");
        user.setFirstName("FirstName");
        user.setFirstName("LastName");

        Comment comment = new Comment();
        comment.setText("GG");
        comment.setUser(user);

        Mockito.when(userService.getCurrent()).thenReturn(user);
        Comment actual = commentConverter.toEntity(commentDto);

        Assertions.assertEquals(comment.getText(), actual.getText());
    }
}