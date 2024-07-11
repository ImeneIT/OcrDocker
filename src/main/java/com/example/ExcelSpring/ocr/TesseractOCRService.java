package com.example.ExcelSpring.ocr;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.concurrent.TimeUnit;

@Service
public class TesseractOCRService {

    public static String performOCR(MultipartFile multipartFile) throws IOException, InterruptedException {
        // Get the user's home directory
        String homeDir = System.getProperty("user.home");
        File tempDir = new File(homeDir, "ocr-temp");

        // Create the directory if it doesn't exist
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        // Save the MultipartFile to a temporary file in the home directory
        File tempFile = new File(tempDir, "upload.png");
        try (InputStream inputStream = multipartFile.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        copyScriptToTempDir(tempDir);
        // Construct the Docker command
        String[] command = {
                "docker", "run", "--rm",
                "-v", tempDir + ":/usr/src/app",
                "tesseract-ocr",
                "./ocr-script.sh", tempFile.getName(), "fra"
        };

        // Execute the Docker command
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor(10, TimeUnit.SECONDS); // Set a timeout for the OCR process

        String result = IOUtils.toString(process.getInputStream(), "UTF-8");


        // Return the OCR result
        return result;
    }
    private static void copyScriptToTempDir(File tempDir) throws IOException {
        File scriptFile = new File("/home/imane-tiabi/Downloads/OCRProject/ocr-script.sh"); // Replace with your actual path
        if (scriptFile.exists()) {
            File destFile = new File(tempDir, scriptFile.getName());
            try (InputStream inputStream = new FileInputStream(scriptFile);
                 OutputStream outputStream = new FileOutputStream(destFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}

