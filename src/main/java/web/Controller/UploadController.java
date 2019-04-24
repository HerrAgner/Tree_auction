package web.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UploadController {
    private static String currentWorkingDir = System.getProperty("user.dir");
    private static String frontendUploadDirectory = "uploads/";
    private static String uploadDir = currentWorkingDir + "/src/main/resources/static/" + frontendUploadDirectory;

    @PostConstruct
    void createDefaultFolderIfMissing() {
        File f = new File(uploadDir);
        if (!f.exists()) {
            System.out.println("creating upload folder");
            f.mkdirs();
        } else {
            System.out.println("already existed");
        }
    }

    @PostMapping("/upload-files")
    public List<String> handleFileUpload(@RequestParam List<MultipartFile> files) {
        final List<String> supportedFileExtensions = List.of(".png,.jpg,.jpeg,.gif,.bmp".split(","));
        List<String> resultingFilepaths = new ArrayList<String>();

        for (MultipartFile file : files) {
            final UUID uuid = UUID.randomUUID();
            String fileExt = file.getOriginalFilename().toLowerCase();
            fileExt = fileExt.substring(fileExt.lastIndexOf("."));
            final String filename = uuid + fileExt;

            if (!supportedFileExtensions.contains(fileExt)) {
                continue;
            }

            File targetLocation = new File(uploadDir + filename);

            try {
                file.transferTo(targetLocation);
                resultingFilepaths.add(frontendUploadDirectory + filename);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return resultingFilepaths;
    }
}



