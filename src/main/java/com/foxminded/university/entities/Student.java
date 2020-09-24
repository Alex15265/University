package com.foxminded.university.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Student {
    @NonNull
    private Integer studentID;
    @NonNull
    private String name;
    @NonNull
    private String lastName;
}
