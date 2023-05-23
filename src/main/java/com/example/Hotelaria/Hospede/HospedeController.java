package com.example.Hotelaria.Hospede;

import com.example.Hotelaria.CheckIn.CheckIn;
import com.example.Hotelaria.CheckIn.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/hospedes")
public class HospedeController {

    private final CheckInRepository checkInRepository;
    private final HospedeRepository hospedeRepository;

    @Autowired
    public HospedeController(CheckInRepository checkInRepository, HospedeRepository hospedeRepository){
        this.checkInRepository = checkInRepository;
        this.hospedeRepository = hospedeRepository;
    }

    @GetMapping
    public List<Hospede> getAllHospedes() {
        return hospedeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hospede getHospedeById(@PathVariable Long id) {
        return hospedeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hospede não encontrado"));
    }

    @PostMapping
    public Hospede createHospede(@RequestBody Hospede hospede) {
        return hospedeRepository.save(hospede);
    }

    @PutMapping("/{id}")
    public Hospede updateHospede(@PathVariable Long id, @RequestBody Hospede hospedeAtualizado) {
        Hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hospede não encontrado"));

        hospede.setNome(hospedeAtualizado.getNome());
        hospede.setDocumento(hospedeAtualizado.getDocumento());
        hospede.setTelefone(hospedeAtualizado.getTelefone());

        return hospedeRepository.save(hospede);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospede(@PathVariable Long id) {
        Hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hospede não encontrado"));

        hospedeRepository.delete(hospede);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/valor-total")
    public double getValorTotalHospede(@PathVariable Long id) {
        List<CheckIn> checkIns = checkInRepository.findByHospedeId(id);
        return checkIns.stream()
                .mapToDouble(CheckIn::calcularValorTotal)
                .sum();
    }

    @GetMapping("/{id}/valor-ultima-hospedagem")
    public double getValorUltimaHospedagem(@PathVariable Long id) {
        List<CheckIn> checkIns = checkInRepository.findByHospedeIdOrderByDataEntradaDesc(id);
        if (!checkIns.isEmpty()) {
            CheckIn ultimaHospedagem = checkIns.get(0);
            return ultimaHospedagem.calcularValorTotal();
        }
        return 0.0;
    }

}
