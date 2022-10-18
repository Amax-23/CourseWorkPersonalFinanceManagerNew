package ru.netology;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;


public class CategoryTest {
    Category category = new Category();
    String testJson = "testJson.json";

    @BeforeEach
    void setUp() {
        category.readTsvFile();
        category.readJsonFile(testJson);
        category.maxValueMap();
        System.out.println("Тест запущен!\n");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test OK!");
    }

    @Test
    public void maxValueMapTest() {
        Assertions.assertEquals(2, category.mapValueMax.size());
        Assertions.assertEquals("еда", category.mapValueMax.get("category"));
        Assertions.assertEquals(70000L, (Long) category.mapValueMax.get("sum"));
        category.readJsonFile(testJson);
        category.maxValueMap();
        Assertions.assertEquals("еда", category.mapValueMax.get("category"));
        Assertions.assertEquals(140000L, (Long) category.mapValueMax.get("sum"));
    }

    @Test
    void readTsvFileTest() {
        Assertions.assertEquals(8, category.categoriesTsv.size());
    }

    @Test
    void readJsonFileTest() {
        Assertions.assertEquals(70000L, (Long) category.categoryJson.get("еда"));
    }
}