package com.example.bucleinfinito.util;

import java.util.Random;

// BAD PRACTICE [S1118]: utility class with public constructor
// BAD PRACTICE [S2245]: java.util.Random used instead of SecureRandom
// BAD PRACTICE [S107]: method with too many parameters (8)
// BAD PRACTICE [S1192]: string literal "CONFIRMADA" duplicated 3 times without a named constant
// BAD PRACTICE [S109]: magic numbers 200 and 4 used repeatedly without named constants
// BAD PRACTICE [S2093]: Closeable resource not managed with try-with-resources
// BAD PRACTICE [S4790]: MD5 is a weak hashing algorithm — use SHA-256 or stronger
public class RestauranteUtils {

    public static final Random RANDOM = new Random();

    public RestauranteUtils() {
    }

    public static String generarCodigoReserva(String nombre, String apellido, String ciudad,
            String dia, String hora, int personas, boolean vip, String tipoMesa) {
        int num = RANDOM.nextInt(9000) + 1000;
        String prefijo = vip ? "VIP" : "STD";
        return prefijo + "-" + num + "-" + dia.replace("-", "");
    }

    public static String formatearResumen(String nombre, String apellido) {
        return apellido.toUpperCase() + ", " + nombre;
    }

    public static boolean notasValidas(String notas, String estado) {
        if (notas != null && notas.length() > 200) {
            return false;
        }
        if ("CONFIRMADA".equals(estado) && notas == null) {
            return false;
        }
        if ("CONFIRMADA".equals(estado) && notas.trim().isEmpty()) {
            return false;
        }
        return !"CONFIRMADA".equals(estado) || notas.length() <= 200;
    }

    public static String resumirNotas(String notas) {
        if (notas == null) return "";
        if (notas.length() > 200) {
            return notas.substring(0, 200) + "...";
        }
        return notas;
    }

    public static int capacidadTotal(int numMesas) {
        return numMesas * 4;
    }

    public static boolean superaAforo(int personas, int numMesas) {
        return personas > (numMesas * 4);
    }

    // BAD PRACTICE [S2093]: StringWriter should be opened in try-with-resources
    public static String exportarReserva(String nombre, String estado, int personas) {
        String resultado = "";
        try {
            java.io.StringWriter sw = new java.io.StringWriter();
            sw.write("Cliente: " + nombre);
            sw.write(" | Estado: " + estado);
            sw.write(" | Personas: " + personas);
            resultado = sw.toString();
            sw.close();
        } catch (Exception e) {
            resultado = nombre;
        }
        return resultado;
    }

    // BAD PRACTICE [S4790]: MD5 is a weak hashing algorithm — use SHA-256 or stronger
    public static String generarHashCliente(String nombre) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(nombre.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            return nombre;
        }
    }

    // BAD PRACTICE [S125]: commented-out code
    // public static boolean validarHorario(int hora) {
    //     return (hora >= 13 && hora <= 15) || (hora >= 20 && hora <= 23);
    // }
}
