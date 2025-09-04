package com.example.telequiz_20211602.data;

import com.example.telequiz_20211602.model.Question;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizRepository {

    public static final String TOPIC_REDES = "Redes";
    public static final String TOPIC_CIBER = "Ciberseguridad";
    public static final String TOPIC_MICRO = "Microondas";

    public static List<Question> getQuestions(String topic) {
        List<Question> list = new ArrayList<>();
        switch (topic) {
            case TOPIC_REDES:
                list.add(new Question("¿Qué dispositivo conecta redes distintas?",
                        Arrays.asList("Switch", "Router", "Hub", "Repeater"), 1));
                list.add(new Question("¿Capa de TCP/IP donde vive HTTP?",
                        Arrays.asList("Acceso a red", "Transporte", "Internet", "Aplicación"), 3));
                list.add(new Question("¿Puerto por defecto de HTTPS?",
                        Arrays.asList("80", "21", "443", "25"), 2));
                list.add(new Question("¿Dirección privada válida?",
                        Arrays.asList("10.0.0.5", "8.8.8.8", "172.33.0.1", "1.1.1.1"), 0));
                list.add(new Question("¿Qué protocolo resuelve nombres?",
                        Arrays.asList("NTP", "DNS", "SSH", "SNMP"), 1));
                list.add(new Question("¿Tipo de cable UTP para larga distancia?",
                        Arrays.asList("Cat5e", "Cat3", "Fibra óptica", "Coaxial RG-59"), 2));
                list.add(new Question("¿Rango Wi-Fi típico?",
                        Arrays.asList("2.4 GHz y 5 GHz", "900 MHz y 1.8 GHz", "10–20 GHz", "440–550 MHz"), 0));
                break;

            case TOPIC_CIBER:
                list.add(new Question("¿Qué es phishing?",
                        Arrays.asList("Ataque por fuerza bruta", "Suplantación para robar datos", "Malware autorreplicable", "Escaneo de puertos"), 1));
                list.add(new Question("¿Doble factor de autenticación agrega…",
                        Arrays.asList("Disponibilidad", "Confidencialidad", "No repudio", "Autenticación"), 3));
                list.add(new Question("¿Cifra navegaciones web?",
                        Arrays.asList("TLS", "NAT", "FTP", "ARP"), 0));
                list.add(new Question("¿Archivo malicioso que se adjunta en correo?",
                        Arrays.asList("Exploit kit", "Payload", "Phishing", "Adjunto malicioso"), 3));
                list.add(new Question("¿Buena práctica de contraseñas?",
                        Arrays.asList("Reutilizarlas", "Escribirlas en post-it", "Gestor de contraseñas", "Compartirlas"), 2));
                list.add(new Question("¿Herramienta para análisis de tráfico?",
                        Arrays.asList("Wireshark", "Word", "Excel", "Paint"), 0));
                list.add(new Question("¿Ataque DDoS busca…",
                        Arrays.asList("Quitar confidencialidad", "Interrumpir servicio", "Modificar datos", "Secuestrar DNS"), 1));
                break;

            default: // Microondas
                list.add(new Question("¿Bandas típicas Wi-Fi?",
                        Arrays.asList("10–20 GHz", "2.4 y 5 GHz", "900 MHz y 1.8 GHz", "440–550 MHz"), 1));
                list.add(new Question("¿Guía donde el modo dominante es TE10?",
                        Arrays.asList("Guía rectangular", "Guía circular", "Línea coaxial", "Microstrip"), 0));
                list.add(new Question("¿Impedancia característica típica de RF?",
                        Arrays.asList("25 Ω", "50 Ω", "100 Ω", "120 Ω"), 1));
                list.add(new Question("¿Qué mide un VNA?",
                        Arrays.asList("Potencia de audio", "Parámetros S", "Latencia de red", "Temperatura"), 1));
                list.add(new Question("¿Filtro que deja pasar bajas frecuencias?",
                        Arrays.asList("Pasa alto", "Pasa banda", "Pasa bajo", "Rechaza banda"), 2));
                list.add(new Question("¿Unidad de ganancia logarítmica?",
                        Arrays.asList("Volt", "Watt", "Bel", "dB"), 3));
                list.add(new Question("¿Longitud de onda λ depende de…",
                        Arrays.asList("Frecuencia y medio", "Sólo potencia", "Temperatura", "Tiempo de muestreo"), 0));
                break;
        }
        return list;
    }

    public static List<Question> shuffledGame(String topic) {
        List<Question> qs = new ArrayList<>(getQuestions(topic));
        Collections.shuffle(qs);
        for (int i = 0; i < qs.size(); i++) {
            Question q = qs.get(i);
            List<String> ops = new ArrayList<>(q.options);
            String correct = q.options.get(q.correctIndex);
            Collections.shuffle(ops);
            int newIndex = ops.indexOf(correct);
            qs.set(i, new Question(q.text, ops, newIndex));
        }
        return qs;
    }
}
