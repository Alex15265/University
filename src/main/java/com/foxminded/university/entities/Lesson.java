package com.foxminded.university.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Lesson {
    @NonNull
    private Professor professor;
    @NonNull
    private Course course;
    @NonNull
    private ClassRoom classRoom;
    @NonNull
    private LessonTime time;
    private List<Group> groups = new ArrayList<>();

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void addGroups(Group ... group) {
        groups.addAll(Arrays.asList(group));
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }
}
