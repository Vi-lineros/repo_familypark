package cl.familypark.familyparksolicitud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controller de demo/health-check del microservicio.
 *
 * GET /api/v1/demo → Estado del microservicio (200 OK)
 */
@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    /**
     * GET /api/v1/demo
     * Endpoint de verificación rápida del estado del microservicio.
     *
     * @return 200 OK con información básica del servicio.
     */
    @GetMapping
    public ResponseEntity<Map<String, String>> demo() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("microservicio", "familypark-solicitud");
        response.put("estado", "activo");
        response.put("version", "1.0");
        return ResponseEntity.ok(response);
    }
}
