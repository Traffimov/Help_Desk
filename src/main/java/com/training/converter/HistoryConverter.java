package com.training.converter;

import com.training.dto.history.HistoryDto;
import com.training.model.History;
import com.training.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryConverter implements Converter<HistoryDto, History> {

    private final UserConverter userConverter;

    private final UserService userService;

    @Override
    public HistoryDto toDto(History history) {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(history.getId());
        historyDto.setAction(history.getAction());
        historyDto.setUserDto(userConverter.toDto(history.getUser()));
        historyDto.setDescription(history.getDescription());
        historyDto.setDate(history.getDate());
        return historyDto;
    }

    @Override
    public History toEntity(HistoryDto historyDto) {
        History history = new History();
        history.setAction(historyDto.getAction());
        history.setUser(userService.getCurrent());
        history.setDescription(historyDto.getDescription());
        history.setDate(historyDto.getDate());
        return history;
    }
}
