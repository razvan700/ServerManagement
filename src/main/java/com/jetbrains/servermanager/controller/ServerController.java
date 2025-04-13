package com.jetbrains.servermanager.controller;

import com.jetbrains.servermanager.enumeration.Status;
import com.jetbrains.servermanager.model.Response;
import com.jetbrains.servermanager.model.Server;
import com.jetbrains.servermanager.service.ServerService;
import com.jetbrains.servermanager.service.ServerServiceImplementation.ServerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/server")
public class ServerController {

    private final ServerServiceImpl serverService;

    public ServerController(ServerServiceImpl serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.from(now()))
                        .data(Map.of("Servers", serverService.list(30)))
                        .message("Servers retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.from(now()))
                        .data(Map.of("Servers", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServers(@RequestBody @Valid Server server) {
        Response response = Response.builder()
                .timeStamp(LocalDateTime.from(now()))
                .data(Map.of("Servers", serverService.create(server)))
                .message("Server created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build();
        return ResponseEntity.ok(
                response
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        Response response = Response.builder()
                .timeStamp(LocalDateTime.from(now()))
                .data(Map.of("Servers", serverService.get(id)))
                .message("Server with specified id retrieved successfully")
                .status(OK)
                .statusCode(OK.value())
                .build();
        return ResponseEntity.ok(
                response
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        Response response = Response.builder()
                .timeStamp(LocalDateTime.from(now()))
                .data(Map.of("deleted", serverService.delete(id)))
                .message("Server with specified id deleted successfully")
                .status(OK)
                .statusCode(OK.value())
                .build();

        return ResponseEntity.ok(
                response
        );
    }

    @GetMapping(path="/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) {
        try {
            return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/serverImages/" + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
