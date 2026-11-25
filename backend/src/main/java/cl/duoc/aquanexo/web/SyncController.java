package cl.duoc.aquanexo.web;

import cl.duoc.aquanexo.model.PreChequeoEntity;
import cl.duoc.aquanexo.repo.PreChequeoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SyncController {
    private final PreChequeoRepository repo;
    private final ObjectMapper mapper;

    public SyncController(
        PreChequeoRepository repo,
        ObjectMapper mapper
    ) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
            "ok", true,
            "servicio", "AquaNexo Spring Boot",
            "modo", "demo académico"
        );
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(
        @RequestBody Map<String, String> body
    ) {
        String user = body.getOrDefault("usuario", "");
        String pass = body.getOrDefault("clave", "");

        if (pass.equals("1234")
            && (user.equals("supervisor") || user.equals("admin"))) {
            return ResponseEntity.ok(
                Map.of(
                    "token",
                    user.equals("admin")
                        ? "demo-admin-token"
                        : "demo-supervisor-token",
                    "rol",
                    user.equals("admin")
                        ? "ADMIN"
                        : "SUPERVISOR"
                )
            );
        }

        return ResponseEntity.status(401)
            .body(Map.of("mensaje", "Credenciales demo inválidas"));
    }

    @GetMapping("/prechequeos")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> all(
        @RequestHeader(
            value = "Authorization",
            required = false
        ) String auth
    ) {
        if (!tokenValido(auth)) {
            return ResponseEntity.status(401)
                .body(Map.of("mensaje", "Token demo requerido"));
        }

        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping("/prechequeos/sync")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> sync(
        @RequestHeader(
            value = "Authorization",
            required = false
        ) String auth,
        @RequestBody JsonNode body
    ) throws Exception {
        if (!"Bearer demo-supervisor-token".equals(auth)) {
            return ResponseEntity.status(403)
                .body(
                    Map.of(
                        "mensaje",
                        "Solo supervisor puede sincronizar/actualizar pre-chequeos"
                    )
                );
        }

        JsonNode reg = body.path("registro");
        long localId = reg.path("id").asLong();

        if (localId <= 0) {
            return ResponseEntity.badRequest()
                .body(Map.of("mensaje", "localId inválido"));
        }

        boolean bloqueo = false;

        for (JsonNode item : body.path("items")) {
            String nombre = item.path("nombre").asText();
            String estado = item.path("estado").asText();

            if ((nombre.equals("Compresor operativo")
                || nombre.equals("Buzo de emergencia disponible"))
                && estado.equals("NO_CUMPLE")) {
                bloqueo = true;
            }
        }

        if (reg.path("buzoEdad").asInt() > 45
            && !reg.path("testEsfuerzoVigente").asBoolean(true)) {
            bloqueo = true;
        }

        if (reg.path("cerrado").asBoolean() && bloqueo) {
            return ResponseEntity.unprocessableEntity()
                .body(
                    Map.of(
                        "mensaje",
                        "Bloqueo de seguridad: registro no autorizable"
                    )
                );
        }

        PreChequeoEntity entity =
            repo.findByLocalId(localId)
                .orElseGet(PreChequeoEntity::new);

        entity.localId = localId;
        entity.centro = reg.path("centro").asText();
        entity.buzo = reg.path("buzo").asText();
        entity.supervisor = reg.path("supervisor").asText();
        entity.fecha = reg.path("fecha").asText();
        entity.hora = reg.path("hora").asText();
        entity.resultado = reg.path("resultado").asText();
        entity.cerrado = reg.path("cerrado").asBoolean();
        entity.payloadJson = mapper.writeValueAsString(body);
        entity.actualizado = LocalDateTime.now();

        entity = repo.save(entity);

        return ResponseEntity.ok(
            Map.of(
                "id", entity.id,
                "localId", localId,
                "estado", "SINCRONIZADO"
            )
        );
    }

    private boolean tokenValido(String auth) {
        return "Bearer demo-supervisor-token".equals(auth)
            || "Bearer demo-admin-token".equals(auth);
    }
}
