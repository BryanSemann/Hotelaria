package com.example.Hotelaria.Hospede;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hospedes")
public class HospedeController {

    @Autowired
    private HospedeRepository repository;

    @GetMapping
    public ResponseEntity<List<Hospede>> findHospedeById(@RequestParam(name = "id", required = false)Long id) {
        List<Hospede> hospede;
        List<Long> ids = new ArrayList<>();

        if (id == null) {
            hospede = repository.findAll();
        }else {
            ids.add(id);
            hospede = repository.findAllById(ids);
        }

        if(hospede.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(hospede);
        }
    }

    @PostMapping
    public Hospede newHospede(@RequestBody Hospede newHospede) {
        return repository.save(newHospede);
    }

    @PutMapping
    public Hospede replaceHospede(@RequestBody Hospede newHospede, @RequestParam("id") Long id) {

        return repository.findById(id)
                .map(hospede -> {
                    hospede.setNome(newHospede.getNome());
                    hospede.setDocumento(newHospede.getDocumento());
                    return repository.save(hospede);
                })
                .orElseGet(() -> {
                    newHospede.setId(id);
                    return repository.save(newHospede);
                });
    }

    @DeleteMapping
    public void deleteHospede(@RequestParam("id") Long id) {
        repository.deleteById(id);
    }
}
