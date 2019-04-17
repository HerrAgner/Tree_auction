package web.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {
    private static String currentWorkingDir = System.getProperty("user.dir");
    private static String uploadDir = currentWorkingDir + "/src/main/resources/static/";

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

    @PostMapping
    ModelAndView uploadFile(@RequestParam List<MultipartFile> files) {
        for (MultipartFile file : files) {
            try {
                file.transferTo(new File(
                        uploadDir + file.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
                return new ModelAndView("redirect:/?message=error");
            }

        }
        return new ModelAndView("redirect:/?message=success");

    }
}
