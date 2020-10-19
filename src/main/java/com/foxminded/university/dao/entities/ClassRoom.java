package com.foxminded.university.dao.entities;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class ClassRoom {
    private Integer roomId;
    private Integer roomNumber;
}
