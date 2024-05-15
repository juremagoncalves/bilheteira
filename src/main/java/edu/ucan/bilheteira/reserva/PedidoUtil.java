package edu.ucan.bilheteira.reserva;

import java.util.Random;

public class PedidoUtil {

    static public String generateRandomReference() {
        // Define o limite para números entre 0 e 9
        int leftLimit = 48; // código ASCII para '0'
        int rightLimit = 57; // código ASCII para '9'
        int targetStringLength = 9;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
