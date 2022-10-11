import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;


import java.io.File;


public class CategoryTest {
    Category category = new Category();
    File testJson = new File("testJson.json");

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
        //System.out.println(2 + " " + category.mapValueMax.size());
        Assertions.assertEquals("еда", category.mapValueMax.get("category"));
        //System.out.println("еда" + " " + category.mapValueMax.get("category"));
        Assertions.assertEquals(71000L, (Long) category.mapValueMax.get("sum"));
        //System.out.println(71000 + " " + category.mapValueMax.get("sum"));
        category.readJsonFile(testJson);
        category.maxValueMap();
        Assertions.assertEquals("еда", category.mapValueMax.get("category"));
        Assertions.assertEquals(142000L, (Long) category.mapValueMax.get("sum"));

    }

    @Test
    void readTsvFileTest() {
        Assertions.assertEquals(4, category.food.size());
        //System.out.println(4 + " " + category.food.size());
        Assertions.assertEquals(2, category.clothing.size());
        //System.out.println(2 + " " + category.clothing.size());
        Assertions.assertEquals(1, category.life.size());
        //System.out.println(1 + " " + category.life.size());
        Assertions.assertEquals(1, category.finance.size());
        //System.out.println(1 + " " + category.finance.size());
    }

    @Test
    void readJsonFileTest() {
        Assertions.assertEquals(71000L, (Long) category.food.get("булка"));
        //System.out.println(71000 + " " + category.food.get("булка"));
    }
}