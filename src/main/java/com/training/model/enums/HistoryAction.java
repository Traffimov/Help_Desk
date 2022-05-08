package com.training.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HistoryAction {

    CREATED("Ticket is created", "Ticket is created"),
    EDITED("Ticket is edited ", "Ticket is edited "),
    STATUS_CHANGED("Ticket Status is changed", "Ticket Status is changed from %s to %s"),
    ATTACHED_FILE("File is attached", "File is attached: %s"),
    REMOVED_FILE("File is removed", "File is removed: %s");

    private final String name;

    private final String description;
}
