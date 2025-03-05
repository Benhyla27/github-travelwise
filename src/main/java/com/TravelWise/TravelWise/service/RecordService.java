package com.TravelWise.TravelWise.service;

import com.TravelWise.TravelWise.model.Record;
import com.TravelWise.TravelWise.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    // Injects the Record repository to manage record data
    private final RecordRepository recordRepository;

    // Constructor
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    // Retrieve all records linked to a username
    public List<Record> getRecordsByUsername(String username) {
        return recordRepository.findByUsername(username);
    }

    // Save a new record for the user
    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }
}
