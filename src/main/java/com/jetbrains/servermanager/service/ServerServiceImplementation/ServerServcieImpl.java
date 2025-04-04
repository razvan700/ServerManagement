package com.jetbrains.servermanager.service.ServerServiceImplementation;

import com.jetbrains.servermanager.model.Server;
import com.jetbrains.servermanager.repository.ServerRepository;
import com.jetbrains.servermanager.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServcieImpl implements ServerService {

    private final ServerRepository serverRepository;
    @Override
    public Server create(Server server){
        return new Server();
    }

    @Override
    public Server ping(String ipAddress) {
        return null;
    }

    @Override
    public Collection<Server> list(int limit) {
        return null;
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
}
