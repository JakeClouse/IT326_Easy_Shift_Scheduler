package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import com.EasyShiftScheduler.CalEnder.Repositories.PunchRepository;
import org.springframework.stereotype.Service;

@Service
public class PunchService {
    private PunchRepository punchRepository;

    public PunchService(PunchRepository punchRepository){
        this.punchRepository = punchRepository;
    }

    public Punch assignReason(Long id, String reason) {
        Punch punch = punchRepository.findById(id).orElse(null);
        if (punch != null) {
            punch.setReason(reason);
            punchRepository.save(punch);
        }
        return punch;
    }
}
