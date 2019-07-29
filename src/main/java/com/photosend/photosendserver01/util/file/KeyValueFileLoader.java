package com.photosend.photosendserver01.util.file;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class KeyValueFileLoader {

    private String filePathStartWithUserHome = System.getProperty("user.home") + "/";

    public String getValueFromFile(String filePath, String key) {
        String resultPath = filePathStartWithUserHome + filePath;
        Map<String, Optional<String>> keyValueStore = new HashMap<>();

        File adminCredentailFile = new File(resultPath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(adminCredentailFile));

            String line = "";
            String[] keyValuePair = null;

            while((line = br.readLine()) != null) {
                keyValuePair = line.split("=");
                keyValueStore.put(keyValuePair[0], Optional.of(keyValuePair[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyValueStore.get(key)
                .orElseThrow(() -> new IllegalArgumentException("키 값에 해당하는 값이 KeyValueStore에 존재하지 않습니다."));
    }
}
