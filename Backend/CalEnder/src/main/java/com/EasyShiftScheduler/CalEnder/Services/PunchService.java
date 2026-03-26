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

    @PostMapping
    public Punch CreatePunch(Punch punch) {
        return punchRepository.save(punch);
    }

    @PostMapping
    public Punch ReadPunch(Long id) {
        return punchRepository.findById(id).orElse(null);
    }


    @PostMapping
    public Punch UpdatePunch(Punch punch, Long id) {
        return punchRepository.save(punch);
    }

    @DeleteMapping
    public void DeletePunch(Long id) {
        punchRepository.deleteById(id);
    }
    
}

