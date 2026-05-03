package com.EasyShiftScheduler.CalEnder.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import com.EasyShiftScheduler.CalEnder.Repositories.PunchRepository;

@Service
public class PunchService {
    private PunchRepository punchRepository;

    public PunchService(PunchRepository punchRepository){
        this.punchRepository = punchRepository;
    }

    public String assignReason(Long id, String reason) {
        Punch punch = punchRepository.findById(id).orElse(null);
        if (punch != null) {
            punch.setReason(reason);
            punchRepository.save(punch);
        }
        return punch.toString();
    }

    public String getPunch(Long id){
        Optional<Punch> punchOptional = punchRepository.findById(id);
        if (punchOptional.isEmpty()){
            return "Punch Not Found";
        }
        Punch punch = punchOptional.get();

        return punch.toString();
    }

}
