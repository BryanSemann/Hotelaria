package com.example.Hotelaria.CheckIn;

import com.example.Hotelaria.CheckIn.CheckIn;
import com.example.Hotelaria.Hospede.Hospede;
import com.example.Hotelaria.Hospede.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/checkIn")
public class CheckInController {

    @Autowired
    private CheckInRepository checkInRepository;
    @Autowired
    private HospedeRepository hospedeRepository;

    @GetMapping("/todos")
    public List<CheckIn> getAll() {
        return (List<CheckIn>) checkInRepository.findAll();
    }

    @GetMapping
    public ResponseEntity<CheckIn> findById(@RequestParam("id")Long id) {
        Optional<CheckIn> checkIn = checkInRepository.findById(id);

        if(checkIn.isPresent()) {
            return ResponseEntity.ok().body(checkIn.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CheckIn> createCheckIn(@RequestBody CheckIn checkIn) {
        Long id = checkIn.getHospede().getId();
        String nome = checkIn.getHospede().getNome();
        String documento = checkIn.getHospede().getDocumento();
        String telefone = checkIn.getHospede().getTelefone();

        Hospede hospede = hospedeRepository.findByIdOrNomeOrDocumentoOrTelefone(id,nome,documento,telefone);

        if (hospede == null) {
            hospede = new Hospede(nome,documento,telefone);
            hospedeRepository.save(hospede);
        }

        checkIn.setHospede(hospede);
        checkInRepository.save(checkIn);

        return ResponseEntity.ok(checkIn);
    }

    @PutMapping
    public CheckIn updateCheckIn(@RequestBody CheckIn newCheckIn, @RequestParam("id") Long id) {

        return checkInRepository.findById(id)
                .map(checkIn -> {
                    checkIn.setHospede(newCheckIn.getHospede());
                    checkIn.setDataEntrada(newCheckIn.getDataEntrada());
                    checkIn.setDataSaida(newCheckIn.getDataSaida());
                    checkIn.setAdicVeiculo(newCheckIn.getAdicVeiculo());
                    return checkInRepository.save(checkIn);
                })
                .orElseGet(() -> {
                    newCheckIn.setId(id);
                    return checkInRepository.save(newCheckIn);
                });
    }

    @DeleteMapping
    public void deleteCheckIn(@RequestParam("id") Long id) {
        checkInRepository.deleteById(id);
    }
}
