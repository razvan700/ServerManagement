package com.jetbrains.servermanager.service.ServerServiceImplementation;

import com.jetbrains.servermanager.enumeration.Status;
import com.jetbrains.servermanager.model.Server;
import com.jetbrains.servermanager.repository.ServerRepository;
import com.jetbrains.servermanager.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServcieImpl implements ServerService {

    private final ServerRepository serverRepository;
    @Override
    public Server create(Server server){
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);

    }


    @Override
    public Server ping(String ipAddress) {
        log.info("Pinging server id {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = null;
        try {
            //get by address needs to be handled
            address = InetAddress.getByAddress(ipAddress.getBytes());
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        };
        try {
            // is reachable needs to be surrounded with try catch
            server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serverRepository.save(serverRepository.findByIpAddress(ipAddress));
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers {}");
        List<Server> resultList;
        resultList = serverRepository.findAll();
        List<Server>finalRestultList = resultList.stream()
                .limit(limit)
                .collect(Collectors.toList());
        return finalRestultList;

    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server with id {}", id);
        return serverRepository.findById(id).get();
    }
    //changed
    @Override
    public Server update(Server server) {
        log.info("Updating new server: {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server with id: {}", id);
        serverRepository.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] imageNames = {};
        Random r=new Random();
        // Store images in a path and retrieve them.. r is a random number, bound is set to 4 because you will use 4 images.
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image" + imageNames[r.nextInt(4)]).toUriString();
    }
}
