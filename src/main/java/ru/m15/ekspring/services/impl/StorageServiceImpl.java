package ru.m15.ekspring.services.impl;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.services.StorageService;

import java.io.ByteArrayInputStream;
import java.io.File;
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
        return null;
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
