package com.moiso.myaws.service;

import java.net.URL;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.moiso.myaws.AwsController;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Service
public class S3Service {
    private String accessKeyId;    
    private String secretAccessKey;

    private final AwsController awsData;
    
    private final String region = "sa-east-1";
    private final String bucketName = "tyquy-bucket";
    
    private final AmazonS3 s3Client;



    public S3Service(AwsController awsData) {
        this.awsData = awsData;

        this.accessKeyId = awsData.accessKeyId();
        this.secretAccessKey = awsData.secretAccessKey();

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                            .withCredentials(new AWSStaticCredentialsProvider(credentials))
                            .withRegion(region)
                            .build();
    }

    /* S3 Services */
    public String uploadFile(MultipartFile file) {
        try {
            s3Client.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), file.getInputStream(), null));
            return "Image uploaded";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public URL getPreSignedUrl(String fileName) throws Exception {
        try {
            Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                    .withMethod(com.amazonaws.HttpMethod.GET)
                    .withExpiration(expiration);
    
            return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        } catch (Exception e) {
            throw e;
        }
    }
}
