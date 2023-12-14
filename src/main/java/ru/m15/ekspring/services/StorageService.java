package ru.m15.ekspring.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public interface StorageService {

    Boolean saveData(UUID uuid, File data );

    Boolean saveData(UUID uuid, String data );

    String loadData(UUID uuid);

    Boolean checkAndCreatePlace( String name );

}
