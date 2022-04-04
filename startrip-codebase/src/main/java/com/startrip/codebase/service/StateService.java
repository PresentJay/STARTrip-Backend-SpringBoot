package com.startrip.codebase.service;

import com.startrip.codebase.domain.state.State;
import com.startrip.codebase.domain.state.StateRepository;
import com.startrip.codebase.dto.state.CreateStateDto;
import com.startrip.codebase.dto.state.UpdateStateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class StateService {
    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    // Create
    public void createState(CreateStateDto dto) {
        State state = State.builder()
                .stateId(dto.getStateID())
                .stateNum(dto.getStateNum())
                .build();
        stateRepository.save(state);
    }

    // Get
    public State getState(UUID id){
        return stateRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("없는 데이터입니다.");
        });
    }

    // Update
    public void updateState(UUID id, UpdateStateDto dto){
        State state = stateRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 State 입니다.");
        });
        state.update(dto);
        stateRepository.save(state);
    }

    public void deleteState(UUID id) { stateRepository.deleteById(id); }
}
