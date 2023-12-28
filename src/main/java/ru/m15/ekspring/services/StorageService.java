package ru.m15.ekspring.services;

import org.springframework.stereotype.Service;
import ru.m15.ekspring.entities.enums.ContentType;

import java.io.File;
import java.util.UUID;

@Service
public interface StorageService {

    Boolean saveData(UUID uuid, File data, ContentType cType );

    Boolean saveData(UUID uuid, String data, ContentType cType );

    String loadData(UUID uuid);

    Boolean checkAndCreatePlace( String name );

}
