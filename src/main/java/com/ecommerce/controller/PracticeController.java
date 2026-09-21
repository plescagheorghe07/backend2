package com.ecommerce.controller;

import com.ecommerce.dto.practice.PatchPracticeRequest;
import com.ecommerce.dto.practice.PracticeRequest;
import com.ecommerce.dto.practice.PracticeResponse;
import com.ecommerce.service.PracticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/practice")
@Tag(name = "Practica", description = "Rute de test pentru GET, POST, PUT, PATCH, DELETE")
public class PracticeController {

    private final PracticeService practiceService;

    public PracticeController(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @GetMapping
    @Operation(summary = "Lista resurselor de practica")
    @ApiResponse(responseCode = "200", description = "OK")
    public List<PracticeResponse> list() {
        return practiceService.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "O resursa de practica dupa id")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Nu exista")
    public PracticeResponse getById(@PathVariable Long id) {
        return practiceService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creeaza o resursa de practica")
    @ApiResponse(responseCode = "201", description = "Creata")
    @ApiResponse(responseCode = "400", description = "Date invalide")
    public PracticeResponse create(@Valid @RequestBody PracticeRequest request) {
        return practiceService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Inlocuieste complet o resursa de practica")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Date invalide")
    @ApiResponse(responseCode = "404", description = "Nu exista")
    public PracticeResponse replace(
            @PathVariable Long id,
            @Valid @RequestBody PracticeRequest request
    ) {
        return practiceService.replace(id, request);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modifica partial o resursa de practica")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Date invalide")
    @ApiResponse(responseCode = "404", description = "Nu exista")
    public PracticeResponse patch(
            @PathVariable Long id,
            @Valid @RequestBody PatchPracticeRequest request
    ) {
        return practiceService.patch(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Sterge o resursa de practica")
    @ApiResponse(responseCode = "204", description = "Stearsa")
    @ApiResponse(responseCode = "404", description = "Nu exista")
    public void delete(@PathVariable Long id) {
        practiceService.delete(id);
    }
}
