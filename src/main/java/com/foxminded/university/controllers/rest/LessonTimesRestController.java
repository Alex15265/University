package com.foxminded.university.controllers.rest;

import com.foxminded.university.dto.lessonTime.LessonTimeDTORequest;
import com.foxminded.university.dto.lessonTime.LessonTimeDTOResponse;
import com.foxminded.university.entities.LessonTime;
import com.foxminded.university.mappers.LessonTimeMapper;
import com.foxminded.university.service.LessonTimeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessontimes")
public class LessonTimesRestController {
    private final Logger logger = LoggerFactory.getLogger(LessonTimesRestController.class);
    private final LessonTimeService lessonTimeService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @GetMapping("/")
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to fetch all lessontimes")
    public List<LessonTimeDTOResponse> showLessonTimes() {
        logger.debug("showing all lessonTimes");
        List<LessonTime> lessonTimes = lessonTimeService.readAll();
        return lessonTimes.stream().map(LessonTimeMapper.INSTANCE::lessonTimeToLessonTimeDTOResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    @ApiOperation(value = "Method used to fetch a lessontime by ID")
    public LessonTimeDTOResponse showLessonTimeByID(
            @ApiParam(value = "ID value of the lessontime you need to retrieve", required = true)
            @PathVariable("id") Integer timeId) {
        logger.debug("showing lessonTime with ID: {}", timeId);
        LessonTime lessonTime = lessonTimeService.readByID(timeId);
        return LessonTimeMapper.INSTANCE.lessonTimeToLessonTimeDTOResponse(lessonTime);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('write')")
    @ApiOperation(value = "Method used to save a new lessontime")
    public LessonTimeDTOResponse saveLessonTime(
            @ApiParam(value = "lessontimeDTORequest for the lessontime you need to save", required = true)
            @Valid @RequestBody LessonTimeDTORequest lessonTimeDTORequest) {
        logger.debug("saving new lessonTime: {}", lessonTimeDTORequest);
        LessonTime lessonTime =
                lessonTimeService.create(LocalDateTime.parse(lessonTimeDTORequest.getLessonStart(), formatter),
                        LocalDateTime.parse(lessonTimeDTORequest.getLessonEnd(), formatter));
        return LessonTimeMapper.INSTANCE.lessonTimeToLessonTimeDTOResponse(lessonTime);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    @ApiOperation(value = "Method used to update a lessontime by ID")
    public LessonTimeDTOResponse updateLessonTime(
            @ApiParam(value = "lessontimeDTORequest for the lessontime you need to update", required = true)
            @Valid @RequestBody LessonTimeDTORequest lessonTimeDTORequest,
            @ApiParam(value = "ID value of the lessontime you need to update", required = true)
            @PathVariable("id") Integer timeId) {
        logger.debug("updating lessonTime with ID: {}", timeId);
        LessonTime lessonTime =
                lessonTimeService.update(timeId, LocalDateTime.parse(lessonTimeDTORequest.getLessonStart(), formatter),
                        LocalDateTime.parse(lessonTimeDTORequest.getLessonEnd(), formatter));
        return LessonTimeMapper.INSTANCE.lessonTimeToLessonTimeDTOResponse(lessonTime);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    @ApiOperation(value = "Method used to delete a lessontime by ID")
    public void deleteLessonTime(
            @ApiParam(value = "ID value of the lessontime you need to delete", required = true)
            @PathVariable("id") Integer timeId) {
        logger.debug("deleting lessonTime with ID: {}", timeId);
        lessonTimeService.delete(timeId);
    }
}

