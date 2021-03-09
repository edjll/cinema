package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileService {

    @Value("${trailer.upload.path}")
    private String trailerUploadPath;

    @Value("${preview.upload.path}")
    private String previewUploadPath;

    @Value("${film.upload.path}")
    private String filmUploadPath;

    public String saveTrailer(MultipartFile trailer) throws IOException {
        return createFile(trailerUploadPath, trailer);
    }

    public String savePreview(MultipartFile preview) throws IOException {
        return createFile(previewUploadPath, preview);
    }

    public String saveFilm(MultipartFile video) throws IOException {
        return createFile(filmUploadPath, video);
    }

    public void checkDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdir();
    }

    private String createFile(String path, MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;
        checkDir(path);
        String filename = generateFilename(file.getOriginalFilename());
        file.transferTo(new File(path + "/" + filename));
        return filename;
    }

    public String generateFilename(String filename) {
        return UUID.randomUUID().toString() + "." + getExtensionFile(filename);
    }

    public String getExtensionFile(String filename) {
        Pattern pattern = Pattern.compile("(?<=\\.)\\w+$");
        Matcher matcher = pattern.matcher(filename);
        matcher.find();
        return matcher.group();
    }

    public void deletePreview(String filename) {
        deleteFile(previewUploadPath, filename);
    }

    public void deleteTrailer(String filename) {
        deleteFile(trailerUploadPath, filename);
    }

    public void deleteFilm(String filename) {
        deleteFile(filmUploadPath, filename);
    }

    private void deleteFile(String path, String filename) {
        new File(path + "/" + filename).delete();
    }
}
