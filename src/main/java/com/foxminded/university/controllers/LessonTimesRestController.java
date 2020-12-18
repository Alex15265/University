package com.foxminded.university.controllers;

import com.foxminded.university.dto.lessonTime.LessonTimeDTORequest;
import com.foxminded.university.dto.lessonTime.LessonTimeDTOResponse;
import com.foxminded.university.entities.LessonTime;
import com.foxminded.university.mappers.LessonTimeMapper;
import com.foxminded.university.service.LessonTimeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LessonTimesRestController {
    private final Logger logger = LoggerFactory.getLogger(LessonTimesRestController.class);
    private final LessonTimeService lessonTimeService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @GetMapping("/api/lessonTimes")
    public List<LessonTimeDTOResponse> showLessonTimes() {
        logger.debug("showing all lessonTimes");
        List<LessonTime> lessonTimes = lessonTimeService.readAll();
        return lessonTimes.stream().map(LessonTimeMapper.INSTANCE::lessonTimeToLessonTimeDTOResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/lessonTimes/{id}")
    public LessonTimeDTOResponse showLessonTime(@PathVariable("id") Integer timeId) {
        logger.debug("showing lessonTime with ID: {}", timeId);
        LessonTime lessonTime = lessonTimeService.readByID(timeId);
        return LessonTimeMapper.INSTANCE.lessonTimeToLessonTimeDTOResponse(lessonTime);
    }

    @PostMapping("/api/lessonTimes")
    public LessonTimeDTOResponse saveLessonTime(@Valid @RequestBody LessonTimeDTORequest lessonTimeDTORequest) {
        logger.debug("saving new lessonTime: {}", lessonTimeDTORequest);
        LessonTime lessonTime =
                lessonTimeService.create(LocalDateTime.parse(lessonTimeDTORequest.getLessonStart(), formatter),
                        LocalDateTime.parse(lessonTimeDTORequest.getLessonEnd(), formatter));
        return LessonTimeMapper.INSTANCE.lessonTimeToLessonTimeDTOResponse(lessonTime);
    }

    @PatchMapping("/api/lessonTimes/{id}")
    public LessonTimeDTOResponse update(@Valid @RequestBody LessonTimeDTORequest lessonTimeDTORequest,
                             @PathVariable("id") Integer timeId) {
        logger.debug("updating lessonTime with ID: {}", timeId);
        LessonTime lessonTime =
                lessonTimeService.update(timeId, LocalDateTime.parse(lessonTimeDTORequest.getLessonStart(), formatter),
                        LocalDateTime.parse(lessonTimeDTORequest.getLessonEnd(), formatter));
        return LessonTimeMapper.INSTANCE.lessonTimeToLessonTimeDTOResponse(lessonTime);
    }

    @DeleteMapping("/api/lessonTimes/{id}")
    public void deleteLessonTime(@PathVariable("id") Integer timeId) {
        logger.debug("deleting lessonTime with ID: {}", timeId);
        lessonTimeService.delete(timeId);
    }
}

