package com.example.int221integratedkk1_backend.Services;

import com.example.int221integratedkk1_backend.Entities.StatusEntity;
import com.example.int221integratedkk1_backend.Repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<StatusEntity> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Optional<StatusEntity> getStatusById(int id) {
        return statusRepository.findById(id);
    }

    public StatusEntity createStatus(StatusEntity statusEntity) {
        return statusRepository.save(statusEntity);
    }

    public StatusEntity updateStatus(int id, StatusEntity updatedStatus) {
        if (statusRepository.existsById(id)) {
            updatedStatus.setStatusId(id);
            return statusRepository.save(updatedStatus);
        } else {

            return null;
        }
    }

    public void deleteStatus(int id) {
        statusRepository.deleteById(id);
    }
}
