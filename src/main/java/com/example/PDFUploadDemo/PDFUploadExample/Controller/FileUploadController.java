package com.example.PDFUploadDemo.PDFUploadExample.Controller;

import ch.qos.logback.core.util.StringUtil;
import com.example.PDFUploadDemo.PDFUploadExample.Model.FileUploadModel;
import com.example.PDFUploadDemo.PDFUploadExample.Sevice.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@Slf4j
public class FileUploadController {
    private final String UPLOAD_DIR = "src/main/resources/";

    @Autowired
    UploadService uploadService;

    @GetMapping("/upload")
    public String signUpCompany(Model model){
        model.addAttribute("upload", new FileUploadModel());
        return "upload";
    }

    @PostMapping("/upload-process")
    public String signUpProcess(@ModelAttribute("upload") FileUploadModel fileUploadModel, Model model,
                                @RequestParam("file")MultipartFile file, RedirectAttributes attributes) throws Exception{
            if (file.isEmpty()){
                attributes.addFlashAttribute("message", "Please select a file" +
                        " to upload.");
                return "redirect:/upload";
            }

            model.addAttribute(file);

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            log.debug("File name {}" + fileName);
            uploadService.encryptPDFFile(UPLOAD_DIR, fileName, file,
                    UUID.randomUUID().toString());
            return "upload-success";
    }
}
