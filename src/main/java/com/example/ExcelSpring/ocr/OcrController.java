package com.example.ExcelSpring.ocr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ocr")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @Autowired
    TesseractOCRService tesseractOCRService ;

    @PostMapping(value = "/ocrLocal")
    public ResponseEntity<String> processImage(@RequestParam("image") MultipartFile image) {
        String result = ocrService.processImage(image);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/ocrDocker")
    public ResponseEntity<String> processImageDockerOcr(@RequestParam("image") MultipartFile image) throws IOException, InterruptedException {
        String result = TesseractOCRService.performOCR(image);
        return ResponseEntity.ok(result);
    }
}
