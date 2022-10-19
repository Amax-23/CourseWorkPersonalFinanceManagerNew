package ru.netology;


import edu.emory.mathcs.backport.java.util.Collections;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class Category implements Serializable {
    public Category() {
        categoriesTsv = new HashMap<>();
        categoryJson = new HashMap<>();
        mapValueMax = new HashMap<>();
        outJsonFile = "answer.json";
    }

    protected Map<String, String> categoriesTsv;
    protected Map<String, Long> categoryJson;
    protected Map <String, Object>  mapValueMax;
    private final String outJsonFile;


    public String getOutJsonFile() {
        return outJsonFile;
    }

    public void saveJsonFile() {
        JSONObject obj = new JSONObject();
        obj.put("maxCategory", maxValueMap());
        try (PrintWriter file = new PrintWriter(outJsonFile)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readJsonFile(String jsonFile) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(jsonFile));
            JSONObject jsonObject = (JSONObject) obj;
            String title = (String) jsonObject.get("title");
            long sum = (Long) jsonObject.get("sum");
            if (categoriesTsv.containsKey(title)) {
                if (!categoryJson.containsKey(categoriesTsv.get(title))) {
                    categoryJson.put(categoriesTsv.get(title), sum);
                } else {
                    Long sumOld = (Long) categoryJson.get(categoriesTsv.get(title));
                    categoryJson.put(categoriesTsv.get(title), sumOld + sum);
                }
            } else if (categoryJson.containsKey("другое")) {
                long sumOld = (long) categoryJson.get("другое");
                categoryJson.put("другое", sumOld + sum);
            } else {
                categoryJson.put("другое", sum);
            }
            //System.out.println(categoryJson);

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void readTsvFile() {
        try (BufferedReader tsVFile = new BufferedReader(new FileReader("categories.tsv"))) {
            String dataTsv = tsVFile.readLine();
            while (dataTsv != null) {
                String[] dataArray = dataTsv.split("\t");
                categoriesTsv.put(dataArray[0], dataArray[1]);
                dataTsv = tsVFile.readLine();
            }
            //System.out.println(categoriesTsv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> maxValueMap() {
        String category = null;
        long sum = (long) Collections.max(categoryJson.values());
        for (Object s : categoryJson.keySet()) {
            if (categoryJson.get(s).equals(sum)) {
                category = (String) s;
            }
        }
        mapValueMax.put("category", category);
        mapValueMax.put("sum", sum);
        //System.out.println(mapValueMax);
        return mapValueMax;
    }

    public void saveLogServerBin(File dataLogFile) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataLogFile))) {
            out.writeObject(this);
        }
    }

    public static Category loadFromBinFile(File dataLogFile) throws IOException {
        System.out.println("Method call made - loadFromBinFile");
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataLogFile))) {
            Category categoryObjekt = (Category) in.readObject();
            in.close();
            return categoryObjekt;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}




