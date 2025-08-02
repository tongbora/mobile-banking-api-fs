package org.istad.mobilebankingfs.controller;

import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.dto.media.MediaResponse;
import org.istad.mobilebankingfs.service.MediaService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public MediaResponse upload(@RequestPart MultipartFile file) {
        return mediaService.upload(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-multiple")
    public List<MediaResponse> uploadMultiple(@RequestPart List<MultipartFile> files) {
        return mediaService.uploadMultiple(files);
    }

    @GetMapping("/download/{mediaName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resource> downloadMediaByName(@PathVariable String mediaName) {
        Resource resource = mediaService.downloadMediaByName(mediaName);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                // this line tells the browser to download resources
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .body(resource);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{mediaName}")
    public int deleteMediaByName(@PathVariable String mediaName) {
        return mediaService.deleteMediaByName(mediaName);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MediaResponse> loadAllMedia() {
        return mediaService.loadAllMedia();
    }
}
