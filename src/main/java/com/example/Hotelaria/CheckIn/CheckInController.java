package com.example.Hotelaria.CheckIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/checkins")
public class CheckInController {

    @Autowired
    private CheckInRepository checkInRepository;

    @GetMapping("/hospedes")
    public List<CheckIn> getCheckInsByHospede(@RequestParam String searchTerm) {
        return checkInRepository.findByHospedeNomeContainingOrHospedeDocumentoContainingOrHospedeTelefoneContaining(searchTerm, searchTerm, searchTerm);
    }

    @GetMapping("/concluidos")
    public List<CheckIn> getCheckInsConcluidos() {
        LocalDate hoje = LocalDate.now();
        return checkInRepository.findByDataSaidaBefore(hoje);
    }

    @GetMapping("/ativos")
    public List<CheckIn> getCheckInsAtivos() {
        LocalDate hoje = LocalDate.now();
        return checkInRepository.findByDataSaidaAfter(hoje);
    }

    @PostMapping
    public CheckIn createCheckIn(@RequestBody CheckIn checkIn) {
        return checkInRepository.save(checkIn);
    }

    @GetMapping("/{id}")
    public CheckIn getCheckInById(@PathVariable Long id) {
        return checkInRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Check-in não encontrado"));
    }

    @PutMapping("/{id}")
    public CheckIn updateCheckIn(@PathVariable Long id, @RequestBody CheckIn updatedCheckIn) {
        CheckIn checkIn = checkInRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Check-in não encontrado"));

        checkIn.setHospede(updatedCheckIn.getHospede());
        checkIn.setDataEntrada(updatedCheckIn.getDataEntrada());
        checkIn.setDataSaida(updatedCheckIn.getDataSaida());
        checkIn.setIsAdicionalVeiculo(updatedCheckIn.getIsAdicionalVeiculo());

        return checkInRepository.save(checkIn);
    }

    @DeleteMapping("/{id}")
    public void deleteCheckIn(@PathVariable Long id) {
        CheckIn checkIn = checkInRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Check-in não encontrado"));

        checkInRepository.delete(checkIn);
    }

    @GetMapping
    public List<CheckIn> getAllCheckIns() {
        return checkInRepository.findAll();
    }
}
