package com.example.Hotelaria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
public class HospedeController {

    @Autowired
    private HospedeRepository hospedeRepository;

    @GetMapping
    List<Hospede> all() {
        return (List<Hospede>) hospedeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    Hospede one(@PathVariable Long id) {

        return hospedeRepository.findById(id)
                .orElseThrow(() -> new HospedeNotFoundException(id));
    }
}
