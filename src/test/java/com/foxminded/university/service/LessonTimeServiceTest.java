package com.foxminded.university.service;

import com.foxminded.university.entities.LessonTime;
import com.foxminded.university.repositories.LessonTimeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LessonTimeServiceTest {
    LessonTimeRepository mockedLessonTimeRep;
    LessonTimeService lessonTimeService;

    @BeforeEach
    void init() {
        mockedLessonTimeRep = mock(LessonTimeRepository.class);
        lessonTimeService = new LessonTimeService(mockedLessonTimeRep);
    }

    @Test
    void create() {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setLessonStart(LocalDateTime.of(2020, 9, 1, 8, 0));
        lessonTime.setLessonEnd(LocalDateTime.of(2020, 9, 1, 9, 30));

        when(mockedLessonTimeRep.save(lessonTime)).thenReturn(lessonTime);

        assertEquals(lessonTime, lessonTimeService.create(LocalDateTime.of(2020, 9, 1, 8, 0),
                LocalDateTime.of(2020, 9, 1, 9, 30)));

        verify(mockedLessonTimeRep, times(1)).save(lessonTime);
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

        when(mockedLessonTimeRep.findByOrderByTimeIdAsc()).thenReturn(lessonTimes);

        assertEquals(lessonTimes, lessonTimeService.readAll());
        assertEquals(lessonTime1, lessonTimeService.readAll().get(0));
        assertEquals(lessonTime2, lessonTimeService.readAll().get(1));
        assertEquals(lessonTime3, lessonTimeService.readAll().get(2));

        verify(mockedLessonTimeRep, times(4)).findByOrderByTimeIdAsc();
    }

    @Test
    void readByID() {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setLessonStart(LocalDateTime.of(2020, 10, 4, 11, 0));
        lessonTime.setLessonEnd(LocalDateTime.of(2020, 10, 4, 12, 30));

        when(mockedLessonTimeRep.findById(1)).thenReturn(java.util.Optional.of(lessonTime));

        assertEquals(lessonTime, lessonTimeService.readByID(1));
        assertEquals(LocalDateTime.of(2020, 10, 4, 11, 0),
                lessonTimeService.readByID(1).getLessonStart());
        assertEquals(LocalDateTime.of(2020, 10, 4, 12, 30),
                lessonTimeService.readByID(1).getLessonEnd());

        verify(mockedLessonTimeRep, times(3)).findById(1);
    }

    @Test
    void update() {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setTimeId(9);
        lessonTime.setLessonStart(LocalDateTime.of(2020, 9, 1, 8, 0));
        lessonTime.setLessonEnd(LocalDateTime.of(2020, 9, 1, 9, 30));

        when(mockedLessonTimeRep.save(lessonTime)).thenReturn(lessonTime);
        when(mockedLessonTimeRep.existsById(9)).thenReturn(true);

        assertEquals(lessonTime, lessonTimeService.update(9,
                LocalDateTime.of(2020, 9, 1, 8, 0),
                LocalDateTime.of(2020, 9, 1, 9, 30)));

        verify(mockedLessonTimeRep, times(1)).save(lessonTime);
    }

    @Test
    void delete() {
        when(mockedLessonTimeRep.existsById(1)).thenReturn(true);

        lessonTimeService.delete(1);

        verify(mockedLessonTimeRep, times(1)).deleteById(1);
    }

    @Test
    void readByID_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedLessonTimeRep.findById(1234)).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                lessonTimeService.readByID(1234));
    }

    @Test
    void update_ShouldThrowExceptionWhenInputIsNonExistentID() {
        when(mockedLessonTimeRep.save(anyObject())).thenThrow(EmptyResultDataAccessException.class);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                lessonTimeService.update(234, LocalDateTime.of(2020, 9, 1, 8, 0),
                        LocalDateTime.of(2020, 9, 1, 9, 30)));
    }
}