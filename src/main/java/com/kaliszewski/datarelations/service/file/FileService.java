package com.kaliszewski.datarelations.service.file;

import java.nio.file.Path;

public interface FileService {
    Path downloadFile();
    Path unzipFile(Path source);
}
