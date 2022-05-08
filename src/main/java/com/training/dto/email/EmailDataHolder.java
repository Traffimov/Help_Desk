package com.training.dto.email;

import com.training.model.User;
import com.training.model.enums.Template;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmailDataHolder {

    private List<User> users = new ArrayList<>();

    private Template template;
}
