package com.xmltojson.conversion;

import java.io.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service
public class XmlToJsonService {

    public String convertXmlToJson(MultipartFile file, String savePath) {
        try {
            // Ensure the save directory exists
            File directory = new File(savePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Convert XML to JSON
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(file.getInputStream());

            ObjectMapper jsonMapper = new ObjectMapper();
            String jsonString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

            // Create the JSON file at user-specified location
            String outputFilePath = savePath + File.separator + file.getOriginalFilename().replace(".xml", ".json");
            jsonMapper.writeValue(new File(outputFilePath), jsonNode);

            return outputFilePath;
        } catch (IOException e) {
            throw new RuntimeException("Error processing XML file: " + e.getMessage(), e);
        }
    }
}
