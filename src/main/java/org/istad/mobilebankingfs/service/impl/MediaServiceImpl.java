package org.istad.mobilebankingfs.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.istad.mobilebankingfs.domain.Media;
import org.istad.mobilebankingfs.dto.media.MediaResponse;
import org.istad.mobilebankingfs.repository.MediaRepository;
import org.istad.mobilebankingfs.service.MediaService;
import org.istad.mobilebankingfs.util.MediaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;


    @Override
    public MediaResponse upload(MultipartFile file) {

        // create file name
        String name = UUID.randomUUID().toString();

        // find last index of dot (.)
//        int lastIndex = file.getOriginalFilename().lastIndexOf(".");

        // get extension
        String extension = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        // path in server
        Path path = Paths.get(serverPath + String.format("/%s.%s", name, extension));

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Media upload failed.");
        }
        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setMimeTypeFile(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .extension(media.getExtension())
                .mimeTypeFile(media.getMimeTypeFile())
                .uri(baseUri + name + "." + media.getExtension())
                .size(file.getSize())
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files) {
        // create empty array list , wait to add single file
        List<MediaResponse> mediaResponses = new ArrayList<>();

        files.forEach(file -> {
            MediaResponse mediaResponse = upload(file);
            mediaResponses.add(mediaResponse);
        });
        return mediaResponses;
    }

    @Override
    public int deleteMediaByName(String mediaName) {
        Path path = Paths.get(serverPath + mediaName);
        log.info("File path: {}", path);
        try {
            if(Files.deleteIfExists(path)){
                return 1;
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Media has not been found.");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }
    }

    @Override
    public Resource downloadMediaByName(String mediaName) {
        Path path = Paths.get(serverPath + mediaName );
        try {
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists()){
                return resource;
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Media has not been found.");
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }
    }

    @Override
    public List<MediaResponse> loadAllMedia() {
        Path path = Paths.get(serverPath);
        log.info("File path: {}", path);

        try {
            // Check if the path exists and is a directory
            if (!Files.exists(path)) {
                log.warn("Path does not exist: {}", path);
                return List.of();
            }

            if (!Files.isDirectory(path)) {
                log.warn("Path is not a directory: {}", path);
                return List.of();
            }

            // Read all files in the directory
            List<MediaResponse> mediaResponses = new ArrayList<>();

            Files.list(path)
                    .filter(Files::isRegularFile) // Only include regular files
                    .forEach(file -> {
                        try {
                            Resource resource = new UrlResource(file.toUri());
                            if (resource.exists()) {
                                MediaResponse response = MediaResponse.builder()
                                        .name(resource.getFilename())
                                        .mimeTypeFile(Files.probeContentType(file))
                                        .extension(MediaUtil.extractExtension(resource.getFilename()))
                                        .size(Files.size(file))
                                        .uri(baseUri + resource.getFilename())
                                        .build();
                                mediaResponses.add(response);
                            }
                        } catch (Exception e) {
                            log.error("Error processing file: {}", file.getFileName(), e);
                        }
                    });

            return mediaResponses;

        } catch (IOException e) {
            log.error("Error reading directory: {}", path, e);
            throw new RuntimeException("Failed to load media files", e);
        }
    }
}
