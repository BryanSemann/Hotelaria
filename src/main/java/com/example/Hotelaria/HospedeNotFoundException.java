package com.example.Hotelaria;

public class HospedeNotFoundException extends RuntimeException {
    HospedeNotFoundException(Long id) {
        super("Não encontrado ospede " + id);
    }
}
