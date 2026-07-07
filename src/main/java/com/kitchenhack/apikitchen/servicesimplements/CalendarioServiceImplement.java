package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.dtos.CitaRequestDTO;
import com.kitchenhack.apikitchen.servicesinterfaces.ICalendarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarioServiceImplement implements ICalendarioService {

    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String EVENTS_URL = "https://www.googleapis.com/calendar/v3/calendars/primary/events";

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.refresh-token}")
    private String refreshToken;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map<String, Object> crearCita(CitaRequestDTO dto) {
        String accessToken = obtenerAccessToken();

        Map<String, Object> payload = new HashMap<>();
        payload.put("summary", "Cita con " + dto.getRol());
        payload.put("start", Map.of("dateTime", dto.getFechaInicio()));
        payload.put("end", Map.of("dateTime", dto.getFechaFin()));
        payload.put("conferenceData", Map.of(
                "createRequest", Map.of("requestId", Long.toString(System.nanoTime()))
        ));
        payload.put("extendedProperties", Map.of(
                "private", Map.of("kitchenhackUsername", dto.getUsername())
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = UriComponentsBuilder.fromHttpUrl(EVENTS_URL)
                .queryParam("conferenceDataVersion", 1)
                .build()
                .encode()
                .toUri();

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        return response.getBody();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> listarMisCitas(String username) {
        String accessToken = obtenerAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        URI uri = UriComponentsBuilder.fromHttpUrl(EVENTS_URL)
                .queryParam("privateExtendedProperty", "kitchenhackUsername=" + username)
                .queryParam("singleEvents", true)
                .queryParam("orderBy", "startTime")
                .queryParam("timeMin", Instant.now().toString())
                .build()
                .encode()
                .toUri();

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        Object items = response.getBody() != null ? response.getBody().get("items") : null;
        return items instanceof List ? (List<Map<String, Object>>) items : List.of();
    }

    private String obtenerAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", refreshToken);
        form.add("grant_type", "refresh_token");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        Object accessToken = response.getBody() != null ? response.getBody().get("access_token") : null;
        if (accessToken == null) {
            throw new RuntimeException("No se pudo obtener el access token de Google");
        }

        return accessToken.toString();
    }
}
