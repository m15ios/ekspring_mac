package ru.m15.ekspring.services.impl;

import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.services.StorageService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    private final MinioClient minioClient;

    private final String bucketName = "prigalki";

    StorageServiceImpl( MinioClient minioClient ){
        log.info( "StorageServiceImpl started" );
        this.minioClient = minioClient;
        checkAndCreatePlace( this.bucketName );
    }

    @Override
    public Boolean saveData( UUID uuid, File data ) {
        return null;
    }

    @Override
    public Boolean saveData( UUID uuid, String data ) {
        // if bais closably catchFinaly does not needed
        try( ByteArrayInputStream bais = new ByteArrayInputStream( data.getBytes("UTF-8") ) ) {
            PutObjectArgs args = PutObjectArgs.builder()
                    .region("eu-central-1")
                    .bucket( this.bucketName )
                    .stream( bais, bais.available(), -1)
                    .object( uuid.toString() )
                    .build();
            minioClient.putObject(args);
        } catch ( Exception e) {
            log.error( "SaveData Error: ", e );
        }
        return true;
    }

    @Override
    public String loadData(UUID uuid) {
        GetObjectArgs args = GetObjectArgs.builder()
                .region("eu-central-1")
                .bucket(this.bucketName)
                .object(uuid.toString())
                .build();
        StringBuilder data = new StringBuilder();
        try (InputStream stream = minioClient.getObject(args);
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data.toString().trim();

    }

    @Override
    public Boolean checkAndCreatePlace(String name) {
        BucketExistsArgs args = BucketExistsArgs.builder().region("eu-central-1").bucket(name).build();
        MakeBucketArgs margs = MakeBucketArgs.builder().region("eu-central-1").bucket(name).build();
        try {
            if( !minioClient.bucketExists(args) ) {
                minioClient.makeBucket(margs);
            }
        } catch( Exception e ){
            return false;
        }
        return true;
    }


}
