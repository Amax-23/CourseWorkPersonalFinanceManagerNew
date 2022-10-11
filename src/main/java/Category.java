
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class Category {
    public Category() {
        food = new HashMap<>();
        clothing = new HashMap<>();
        life = new HashMap<>();
        finance = new HashMap<>();
        others = new HashMap<>();
        mapValueMax = new HashMap<>();
        outJsonFile = new File("answer.json");
    }

    Map<String, Long> food;
    Map<String, Long> clothing;
    Map<String, Long> life;
    Map<String, Long> finance;
    Map<String, Long> others;
    Map mapValueMax;
    private final File outJsonFile;
    private String category;
    private long sum;

    public File getOutJsonFile() {
        return outJsonFile;
    }

    public void saveJsonFile() {
        JSONObject obj = new JSONObject();
        obj.put("maxCategory", maxValueMap());
        try (FileWriter file = new FileWriter(outJsonFile)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Произведен вызов метода - saveJsonFile");
    }

    public void readJsonFile(File jsonFile) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(jsonFile));
            JSONObject jsonObject = (JSONObject) obj;
            String title = (String) jsonObject.get("title");
            long sum = (Long) jsonObject.get("sum");
            if (food.containsKey(title)) {
                food.put(title, food.get(title) + sum);
            } else if (clothing.containsKey(title)) {
                clothing.put(title, clothing.get(title) + sum);
            } else if (life.containsKey(title)) {
                life.put(title, life.get(title) + sum);
            } else if (finance.containsKey(title)) {
                finance.put(title, finance.get(title) + sum);
            } else if (others.containsKey(title)) {
                others.put(title, others.get(title) + sum);
            } else if (others.isEmpty() || !others.containsKey(title)) {
                others.put(title, sum);
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("Произведен вызов метода - readJsonFile");
//        System.out.println(food);
//        System.out.println(clothing);
//        System.out.println(life);
//        System.out.println(finance);
//        System.out.println(others);
    }

    public void readTsvFile() {
        try (BufferedReader tsVFile = new BufferedReader(new FileReader("categories.tsv"))) {
            String dataTsv = tsVFile.readLine();
            while (dataTsv != null) {
                String[] dataArray = dataTsv.split("\t");
                if (dataArray[1].equals("еда")) {
                    food.put(dataArray[0], 0L);
                } else if (dataArray[1].equals("одежда")) {
                    clothing.put(dataArray[0], 0L);
                } else if (dataArray[1].equals("быт")) {
                    life.put(dataArray[0], 0L);
                } else if (dataArray[1].equals("финансы")) {
                    finance.put(dataArray[0], 0L);
                } else others.put(dataArray[1], 0L);
                dataTsv = tsVFile.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       // System.out.println("Произведен вызов метода - readTsvFile");
    }

    public Map maxValueMap() {
        Map<String, Long> mapMax = new HashMap<>();
        if (!food.isEmpty()) {
            long sumMaxMap = 0;
            for (Long value : food.values()) {
                sumMaxMap += value;
            }
            mapMax.put("еда", sumMaxMap);
        }
        if (!clothing.isEmpty()) {
            long sumMaxMap = 0;
            for (Long value : clothing.values()) {
                sumMaxMap += value;
            }
            mapMax.put("одежда", sumMaxMap);
        }
        if (!life.isEmpty()) {
            long sumMaxMap = 0;
            for (Long value : life.values()) {
                sumMaxMap += value;
            }
            mapMax.put("быт", sumMaxMap);
        }
        if (!finance.isEmpty()) {
            long sumMaxMap = 0;
            for (Long value : finance.values()) {
                sumMaxMap += value;
            }
            mapMax.put("финансы", sumMaxMap);
        }
        if (!others.isEmpty()) {
            long sumMaxMap = 0;
            for (Long value : others.values()) {
                sumMaxMap += value;
            }
            mapMax.put("другое", sumMaxMap);
        }
        for (Map.Entry<String, Long> entry : mapMax.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            if (value > sum) {
                sum = value;
                category = key;
            }
        }

        mapValueMax.put("category", category);
        mapValueMax.put("sum", sum);
        //System.out.println("Произведен вызов метода - maxValueMap");
        return mapValueMax;
    }
}




