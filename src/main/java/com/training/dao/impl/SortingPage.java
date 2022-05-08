package com.training.dao.impl;

import com.training.model.enums.Direction;
import com.training.model.enums.Sort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SortingPage {

    private Sort sort;
    private Direction direction;
    private int page;
}
