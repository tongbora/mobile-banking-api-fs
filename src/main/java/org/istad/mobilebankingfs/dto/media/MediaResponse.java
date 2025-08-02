package org.istad.mobilebankingfs.dto.media;

import lombok.Builder;

@Builder
public record MediaResponse(
        String name,
        String mimeTypeFile,
        String uri,
        String extension,
        Long size
) {
}
