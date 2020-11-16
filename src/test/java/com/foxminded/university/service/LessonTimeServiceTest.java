package com.foxminded.university.service;

import com.foxminded.university.dao.LessonTimeDAO;
import com.foxminded.university.dao.entities.ClassRoom;
import com.foxminded.university.dao.entities.LessonTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LessonTimeServiceTest {
    LessonTimeDAO mockedLessonTimeDAO;
    LessonTimeService lessonTimeService;

    @BeforeEach
    void init() {
        mockedLessonTimeDAO = mock(LessonTimeDAO.class);
        lessonTimeService = new LessonTimeService(mockedLessonTimeDAO);
    }

    @Test
    void create() {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setLessonStart(LocalDateTime.of(2020, 9, 1, 8, 0));
        lessonTime.setLessonEnd(LocalDateTime.of(2020, 9, 1, 9, 30));

        when(mockedLessonTimeDAO.create(lessonTime)).thenReturn(lessonTime);

        assertEquals(lessonTime, lessonTimeService.create(LocalDateTime.of(2020, 9, 1, 8, 0),
                LocalDateTime.of(2020, 9, 1, 9, 30)));

        verify(mockedLessonTimeDAO, times(1)).create(lessonTime);
    }

    @Test
    void readAll() {
        List<LessonTime> lessonTimes = new ArrayList<>();
        LessonTime lessonTime1 = new LessonTime();
        lessonTime1.setLessonStart(LocalDateTime.of(2020, 9, 1, 8, 0));
        lessonTime1.setLessonEnd(LocalDateTime.of(2020, 9, 1, 9, 30));
        LessonTime lessonTime2 = new LessonTime();
        lessonTime2.setLessonStart(LocalDateTime.of(2020, 10, 4, 11, 0));
        lessonTime2.setLessonEnd(LocalDateTime.of(2020, 10, 4, 12, 30));
        LessonTime lessonTime3 = new LessonTime();
        lessonTime3.setLessonStart(LocalDateTime.of(2021, 2, 21, 9, 30));
        lessonTime3.setLessonEnd(LocalDateTime.of(2021, 2, 21, 11, 0));
        lessonTimes.add(lessonTime1);
        lessonTimes.add(lessonTime2);
        lessonTimes.add(lessonTime3);

        when(mockedLessonTimeDAO.readAll()).thenReturn(lessonTimes);

        assertEquals(lessonTimes, lessonTimeService.readAll());
        assertEquals(lessonTime1, lessonTimeService.readAll().get(0));
        assertEquals(lessonTime2, lessonTimeService.readAll().get(1));
        assertEquals(lessonTime3, lessonTimeService.readAll().get(2));

        verify(mockedLessonTimeDAO, times(4)).readAll();
    }

    @Test
    void readByID() throws NoSuchObjectException {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setLessonStart(LocalDateTime.of(2020, 10, 4, 11, 0));
        lessonTime.setLessonEnd(LocalDateTime.of(2020, 10, 4, 12, 30));

        when(mockedLessonTimeDAO.readByID(1)).thenReturn(lessonTime);

        assertEquals(lessonTime, lessonTimeService.readByID(1));
        assertEquals(LocalDateTime.of(2020, 10, 4, 11, 0),
                lessonTimeService.readByID(1).getLessonStart());
        assertEquals(LocalDateTime.of(2020, 10, 4, 12, 30),
                lessonTimeService.readByID(1).getLessonEnd());

        verify(mockedLessonTimeDAO, times(3)).readByID(1);
    }

    @Test
    void update() throws NoSuchObjectException {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setTimeId(9);
        lessonTime.setLessonStart(LocalDateTime.of(2020, 9, 1, 8, 0));
        lessonTime.setLessonEnd(LocalDateTime.of(2020, 9, 1, 9, 30));

        when(mockedLessonTimeDAO.update(lessonTime)).thenReturn(lessonTime);

        assertEquals(lessonTime, lessonTimeService.update(9,
                LocalDateTime.of(2020, 9, 1, 8, 0),
                LocalDateTime.of(2020, 9, 1, 9, 30)));

        verify(mockedLessonTimeDAO, times(1)).update(lessonTime);
    }

    @Test
    void delete() {
        lessonTimeService.delete(1);

        verify(mockedLessonTimeDAO, times(1)).delete(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedLessonTimeDAO.readByID(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(NoSuchObjectException.class, () ->
                lessonTimeService.readByID(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() throws NoSuchObjectException {
        when(mockedLessonTimeDAO.update(anyObject())).thenThrow(NoSuchObjectException.class);
        Assertions.assertThrows(NoSuchObjectException.class, () ->
                lessonTimeService.update(234, LocalDateTime.of(2020, 9, 1, 8, 0),
                        LocalDateTime.of(2020, 9, 1, 9, 30)));
    }
}