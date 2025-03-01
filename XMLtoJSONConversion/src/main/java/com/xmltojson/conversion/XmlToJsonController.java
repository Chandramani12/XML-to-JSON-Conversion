package com.xmltojson.conversion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/convert")
public class XmlToJsonController {

    private final XmlToJsonService xmlToJsonService;

    @Autowired
    public XmlToJsonController(XmlToJsonService xmlToJsonService) {
        this.xmlToJsonService = xmlToJsonService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("savePath") String savePath) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty. Please upload a valid file.");
        }

        String outputFilePath = xmlToJsonService.convertXmlToJson(file, savePath);
        return ResponseEntity.ok("JSON file saved at: " + outputFilePath);
    }
}
