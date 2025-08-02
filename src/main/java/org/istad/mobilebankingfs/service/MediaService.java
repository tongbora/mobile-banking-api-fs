package org.istad.mobilebankingfs.service;

import org.istad.mobilebankingfs.dto.media.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    MediaResponse upload(MultipartFile file);
    List<MediaResponse> uploadMultiple(List<MultipartFile> files);
    int deleteMediaByName(String mediaName);
    Resource downloadMediaByName(String mediaName);
    List<MediaResponse> loadAllMedia();
}
