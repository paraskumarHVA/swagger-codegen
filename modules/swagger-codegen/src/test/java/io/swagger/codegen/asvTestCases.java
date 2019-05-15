package io.swagger.codegen;

import io.swagger.codegen.languages.JavaClientCodegen;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;

public class asvTestCases {
    private static final String TEST_SKIP_OVERWRITE = "testSkipOverwrite";
    private static final String POM_FILE = "pom.xml";
    private static final String MODEL_ORDER_FILE = "/src/main/java/io/swagger/client/model/Order.java";
    private static final String MODEL_USER_FILE = "/src/main/java/io/swagger/client/model/User.java";
    private static final String MODEL_CATEGORY_FILE = "/src/main/java/io/swagger/client/model/Category.java";
    private static final String MODEL_TAG_FILE = "/src/main/java/io/swagger/client/model/Tag.java";
    private static final String MODEL_PET_FILE = "/src/main/java/io/swagger/client/model/Pet.java";
    private static final String MODEL_API_RESPONSE_FILE = "/src/main/java/io/swagger/client/model/ApiResponse.java";
    private static final String API_CLIENT_FILE = "/src/main/java/io/swagger/client/ApiClient.java";
    private static final String BUILD_GRADLE_FILE = "build.gradle";

    private static final String LIBRARY_COMMENT = "//overloaded template file within library folder to add this comment";
    private static final String TEMPLATE_COMMENT = "//overloaded main template file to add this comment";
    private static final String MODEL_DEFAULT_API_FILE = "/src/main/java/io/swagger/client/api/DefaultApi.java";

    private static final String SWAGGER_VERSION = "2.0";
    private static final String SWAGGER_INFO_TITLE = "Swagger Petstore";
    private static final String SWAGGER_INFO_VERSION = "1.0.0";
    private static final String SWAGGER_HOST = "petstore.swagger.io";
    private static final String SWAGGER_BASE_PATH = "/v2";

    @Mock
    private TemporaryFolder folder = new TemporaryFolder();
    @Mock
    private SwaggerParser swaggerParser = new SwaggerParser();

    @BeforeMethod
    public void setUp() throws Exception {
        folder.create();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        folder.delete();
    }

    @Test
    public void testRequirementOne(){
        final File output = folder.getRoot();

        final Swagger swagger = swaggerParser.read("src/test/resources/petstore.json");
        CodegenConfig codegenConfig = new JavaClientCodegen();
        codegenConfig.setOutputDir(output.getAbsolutePath());

        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);

        DefaultGenerator gen = new DefaultGenerator();
        gen.opts(clientOptInput);
        final File order = new File(output, MODEL_ORDER_FILE);
        assertTrue(order.exists());
    }

    @Test
    public void testRequirementTwo(){
        final Swagger swagger = swaggerParser.read("src/test/resources/2_0/petstore.json");

        assertEquals(swagger.getSwagger().toString(), SWAGGER_VERSION);
        assertEquals(swagger.getInfo().getTitle().toString(), SWAGGER_INFO_TITLE);
        assertEquals(swagger.getInfo().getVersion().toString(), SWAGGER_INFO_VERSION);
        assertEquals(swagger.getHost().toString(), SWAGGER_HOST);
        assertEquals(swagger.getBasePath().toString(), SWAGGER_BASE_PATH);
    }

    @Test
    public void testRequirementThree(){
        final File output = folder.getRoot();

        final Swagger swagger = swaggerParser.read("src/test/resources/petstore.json");
        CodegenConfig codegenConfig = new JavaClientCodegen();
        codegenConfig.setOutputDir(output.getAbsolutePath());

        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);

        DefaultGenerator gen = new DefaultGenerator();
        gen.opts(clientOptInput);

        final File order = new File(output, MODEL_ORDER_FILE);
        assertNotNull(order);

    }

    @Test
    public void testRequirementFour(){

    }

    @Test
    public void testRequirementFive(){
        final File output = folder.getRoot();

        final Swagger swagger = new SwaggerParser().read("src/test/resources/petstore.json");
        CodegenConfig codegenConfig = new JavaClientCodegen();
        codegenConfig.setOutputDir(output.getAbsolutePath());

        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);

        DefaultGenerator gen = new DefaultGenerator();
        gen.opts(clientOptInput);

        final File order = new File(output, MODEL_ORDER_FILE);
        final File category = new File(output, MODEL_CATEGORY_FILE);
        final File tag = new File(output, MODEL_TAG_FILE);
        final File user = new File(output, MODEL_USER_FILE);
        final File pet = new File(output, MODEL_PET_FILE);
        final File apiResponse = new File(output, MODEL_API_RESPONSE_FILE);

        assertNotNull(order);
        assertNotNull(category);
        assertNotNull(tag);
        assertNotNull(user);
        assertNotNull(pet);
        assertNotNull(apiResponse);

    }

    @Test
    public void testRequirementSix(){

    }

    @Test
    public void testRequirementSeven(){

    }

    @Test
    public void testRequirementEight(){

    }
}