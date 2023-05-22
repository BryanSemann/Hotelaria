package com.example.Hotelaria.Hospede;

import com.example.Hotelaria.CheckIn.CheckIn;
import com.example.Hotelaria.CheckIn.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
public class HospedeController {

    private CheckInRepository checkInRepository;
    private HospedeRepository hospedeRepository;

    @Autowired
    public HospedeController(CheckInRepository checkInRepository, HospedeRepository hospedeRepository){
        this.checkInRepository = checkInRepository;
        this.hospedeRepository = hospedeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Hospede>> findHospedeById(@RequestParam(name = "id", required = false)Long id,
                                                         @RequestParam(name = "tci", required = false)Boolean temCheckin
                                                         ) {
        List<Hospede> hospede;
        List<Long> ids = new ArrayList<>();


        if ((temCheckin != null) && (temCheckin)){
            List<Hospede> hospedesComCheckIn = new ArrayList<>();
            List<CheckIn> checkIns = checkInRepository.findAll();

            for (CheckIn checkIn : checkIns) {
                if (checkIn.getDataSaida().toInstant().isBefore(Instant.from(LocalDate.now()))){
                    Hospede hospede1 = checkIn.getHospede();
                    if (hospede1 != null)
                        hospedesComCheckIn.add(hospede1);
                }
            }
            hospede = hospedesComCheckIn;
        } else
        if (id == null) {
            hospede = hospedeRepository.findAll();
        }else {
            ids.add(id);
            hospede = hospedeRepository.findAllById(ids);
        }

        if(hospede.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(hospede);
        }
    }

    @PostMapping
    public Hospede newHospede(@RequestBody Hospede newHospede) {
        return hospedeRepository.save(newHospede);
    }

    @PutMapping
    public Hospede replaceHospede(@RequestBody Hospede newHospede, @RequestParam("id") Long id) {

        return hospedeRepository.findById(id)
                .map(hospede -> {
                    hospede.setNome(newHospede.getNome());
                    hospede.setDocumento(newHospede.getDocumento());
                    return hospedeRepository.save(hospede);
                })
                .orElseGet(() -> {
                    newHospede.setId(id);
                    return hospedeRepository.save(newHospede);
                });
    }

    @DeleteMapping
    public void deleteHospede(@RequestParam("id") Long id) {
        hospedeRepository.deleteById(id);
    }
}
