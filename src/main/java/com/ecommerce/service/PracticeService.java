package com.ecommerce.service;

import com.ecommerce.dto.practice.PatchPracticeRequest;
import com.ecommerce.dto.practice.PracticeRequest;
import com.ecommerce.dto.practice.PracticeResponse;
import com.ecommerce.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PracticeService {

    private final Map<Long, PracticeResponse> items = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public List<PracticeResponse> list() {
        return new ArrayList<>(items.values());
    }

    public PracticeResponse getById(Long id) {
        return findOrThrow(id);
    }

    public PracticeResponse create(PracticeRequest request) {
        long id = sequence.getAndIncrement();
        PracticeResponse created = new PracticeResponse(id, request.message().trim(), Instant.now());
        items.put(id, created);
        return created;
    }

    public PracticeResponse replace(Long id, PracticeRequest request) {
        findOrThrow(id);
        PracticeResponse replaced = new PracticeResponse(id, request.message().trim(), Instant.now());
        items.put(id, replaced);
        return replaced;
    }

    public PracticeResponse patch(Long id, PatchPracticeRequest request) {
        PracticeResponse current = findOrThrow(id);
        String message = request.message() == null || request.message().isBlank()
                ? current.message()
                : request.message().trim();
        PracticeResponse patched = new PracticeResponse(id, message, Instant.now());
        items.put(id, patched);
        return patched;
    }

    public void delete(Long id) {
        findOrThrow(id);
        items.remove(id);
    }

    private PracticeResponse findOrThrow(Long id) {
        PracticeResponse item = items.get(id);
        if (item == null) {
            throw new ResourceNotFoundException("Resursa de practica nu a fost gasita");
        }
        return item;
    }
}
