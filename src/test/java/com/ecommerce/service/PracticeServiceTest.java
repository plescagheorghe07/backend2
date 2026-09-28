package com.ecommerce.service;

import com.ecommerce.dto.practice.PatchPracticeRequest;
import com.ecommerce.dto.practice.PracticeRequest;
import com.ecommerce.dto.practice.PracticeResponse;
import com.ecommerce.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PracticeServiceTest {

    private PracticeService practiceService;

    @BeforeEach
    void setUp() {
        practiceService = new PracticeService();
    }

    @Test
    void createAndList() {
        PracticeResponse created = practiceService.create(new PracticeRequest("salut"));

        assertEquals("salut", created.message());
        assertEquals(1, practiceService.list().size());
    }

    @Test
    void getById() {
        PracticeResponse created = practiceService.create(new PracticeRequest("salut"));

        PracticeResponse found = practiceService.getById(created.id());

        assertEquals("salut", found.message());
    }

    @Test
    void getMissingThrows() {
        assertThrows(ResourceNotFoundException.class, () -> practiceService.getById(99L));
    }

    @Test
    void replace() {
        PracticeResponse created = practiceService.create(new PracticeRequest("vechi"));

        PracticeResponse replaced = practiceService.replace(created.id(), new PracticeRequest("nou"));

        assertEquals("nou", replaced.message());
    }

    @Test
    void patch() {
        PracticeResponse created = practiceService.create(new PracticeRequest("vechi"));

        PracticeResponse patched = practiceService.patch(created.id(), new PatchPracticeRequest("patch"));

        assertEquals("patch", patched.message());
    }

    @Test
    void delete() {
        PracticeResponse created = practiceService.create(new PracticeRequest("salut"));

        practiceService.delete(created.id());

        assertTrue(practiceService.list().isEmpty());
    }

    @Test
    void deleteMissingThrows() {
        assertThrows(ResourceNotFoundException.class, () -> practiceService.delete(99L));
    }
}
