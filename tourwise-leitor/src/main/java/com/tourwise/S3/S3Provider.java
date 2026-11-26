package com.tourwise.S3;

import java.util.List;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

public class S3Provider {
    private final AwsCredentialsProvider credentials;
    private final S3Client s3Client;

    public S3Provider() {
        this.credentials = DefaultCredentialsProvider.create();
        this.s3Client = getS3Client();
    }

    public S3Client getS3Client() {
        return S3Client.builder()
              .region(Region.US_EAST_1)
              .credentialsProvider(credentials)
              .build();
    }

    //tourwise
    public List<S3Object> getObjects(String bucketName) {
        ListObjectsRequest listObjects = ListObjectsRequest.builder()
            .bucket(bucketName)
            .build();

        List<S3Object> objects = s3Client.listObjects(listObjects).contents();
        return objects;
    }

    public 


    

    
}
