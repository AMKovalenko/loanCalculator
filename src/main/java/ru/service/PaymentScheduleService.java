package ru.service;

import org.springframework.stereotype.Service;
import ru.dto.ScheduleItem;
import ru.request.GetScheduleRequest;
import ru.engine.ScheduleEngine;

import java.util.List;

@Service
public class PaymentScheduleService {

    public List<ScheduleItem> getSchedule(GetScheduleRequest request) {
        ScheduleEngine scheduleEngine = ScheduleEngine.init(request);
        scheduleEngine.buildFullSchedule();
        return scheduleEngine.getResult();
    }
}
