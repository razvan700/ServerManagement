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
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final Logger logger = Logger.getLogger(ServerServiceImpl.class.getName());
    private final ServerRepository serverRepository;
    @Override
    public Server create(Server server){
        logger.info("Saving new server");
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Server ping(String ipAddress) {

        logger.info("Pinging server with id: " + ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = null;
        try {
            //get by address needs to be handled
            address = InetAddress.getByName(ipAddress);
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
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        logger.info("Fetching all" + limit + "servers {}");
        List<Server> resultList;
        resultList = serverRepository.findAll();
        List<Server>finalResultList = resultList.stream()
                .limit(limit)
                .collect(Collectors.toList());
        return finalResultList;
    }

    @Override
    public Server get(Long id) {
        logger.info("Fetching server with id: " + id);
        return serverRepository.findById(id).get();
    }
    //changed
    @Override
    public Server update(Server server) {
        logger.info("Updating new server:" + server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        logger.info("Deleting server with id:" + id);
        serverRepository.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] imagesNames = {"Server1.png", "Server2.png", "Server3.png", "Server4.png"};
        Random r=new Random();
        // Store images in a path and retrieve them.. r is a random number, bound is set to 4 because you will use 4 images.
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image" + imagesNames[r.nextInt(4)]).toUriString();
    }
}
