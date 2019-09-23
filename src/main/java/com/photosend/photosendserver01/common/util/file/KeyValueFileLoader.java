package com.photosend.photosendserver01.common.util.file;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class KeyValueFileLoader {

    private String filePathStartWithUserHome = System.getProperty("user.home") + "/";

    private Map<String, Optional<String>> valueMap;

    public String getValueFromFile(String filePath, String key) {
        if (this.valueMap == null) {
            initValueMap(filePath);
        }

        return this.valueMap.get(key)
                .orElseThrow(() -> new IllegalArgumentException("키 값에 해당하는 값이 KeyValueStore에 존재하지 않습니다."));
    }

    private void initValueMap(String filePath) {
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

        this.valueMap = keyValueStore;
    }
}
