package com.EasyShiftScheduler.CalEnder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.EasyShiftScheduler.CalEnder.Repositories.PunchRepository;

@Service
public class PunchService {

    @Autowired
    private PunchRepository punchRepository;

    public Punch CreatePunch(Punch punch) {
        return punchRepository.save(punch);
    }

    public Punch ReadPunch(Long id) {
        return punchRepository.findById(id).orElse(null);
    }

    public Punch UpdatePunch(Punch punch, Long id) {
        return punchRepository.save(punch);
    }

    public void DeletePunch(Long id) {
        punchRepository.deleteById(id);
    }
    
    //Use Case 25: Assign Punch Reason
    //Finds an existing punch by ID and updates its reason field.
    public Punch assignReason(Long id, String reason) {
        Punch punch = punchRepository.findById(id).orElse(null);
        if (punch != null) {
            punch.setReason(reason);
            punchRepository.save(punch);
        }
        return punch;
    }
}

