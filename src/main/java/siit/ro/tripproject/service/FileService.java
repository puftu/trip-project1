package siit.ro.tripproject.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileService {
    private static final String ROOT_DIR = "E:/SIIT/Saved Pictures/";  // The directory where all photos are saved

    public void store(String uuid, MultipartFile file) {

        try (OutputStream outputStream = new FileOutputStream(new File(ROOT_DIR + uuid));
             InputStream inputStream = file.getInputStream();) {

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }
    }

    // When a trip is loaded this method is called the inject the photos in the page

    public Resource loadAsResource(String filename) {
        return new FileSystemResource(ROOT_DIR + filename);
    }

}
