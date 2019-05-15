package io.swagger.codegen;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.junit.rules.TemporaryFolder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class asvTestCases {
    private static final String TEST_SKIP_OVERWRITE = "testSkipOverwrite";
    private static final String POM_FILE = "pom.xml";
    private static final String MODEL_ORDER_FILE = "/src/main/java/io/swagger/client/model/Order.java";
    private static final String API_CLIENT_FILE = "/src/main/java/io/swagger/client/ApiClient.java";
    private static final String BUILD_GRADLE_FILE = "build.gradle";

    private static final String LIBRARY_COMMENT = "//overloaded template file within library folder to add this comment";
    private static final String TEMPLATE_COMMENT = "//overloaded main template file to add this comment";
    private static final String MODEL_DEFAULT_API_FILE = "/src/main/java/io/swagger/client/api/DefaultApi.java";

    public TemporaryFolder folder = new TemporaryFolder();

    @BeforeMethod
    public void setUp() throws Exception {
        folder.create();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        folder.delete();
    }

    @Test
    public void testTest() throws Exception {
        final Swagger swagger = new SwaggerParser().read("src/test/resources/2_0/petstore.json");

        if (swagger.getHost().toString().equals("petstore.swagger.io")){
            System.out.println("hallo");
        }
    }
}