package com.example.ExcelSpring.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.IOException;

@Service
public class OcrService {

    public String processImage(MultipartFile file) {
        Tesseract tesseract = new Tesseract();

        // Set the path to the directory containing the Tesseract language data files
        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");

        try {
            File convFile = convertMultipartFileToFile(file);
            String result = tesseract.doOCR(convFile);
            convFile.delete();  // Clean up the temporary file
            return result;
        } catch (TesseractException | IOException e) {
            throw new RuntimeException("Failed to process the image", e);
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
}