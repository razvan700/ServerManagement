package com.jetbrains.servermanager.service.ServerServiceImplementation;

import com.jetbrains.servermanager.enumeration.Status;
import com.jetbrains.servermanager.model.Server;
import com.jetbrains.servermanager.repository.ServerRepository;
import com.jetbrains.servermanager.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        List<Server> resultList = new ArrayList<>();

    }

    @Override
    public Server get(Long id) {
        return null;
    }
    //changed
    @Override
    public Server update(Server server) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private String setServerImageUrl() {
        return null;
    }
}
