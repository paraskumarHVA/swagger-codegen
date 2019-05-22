package io.swagger.codegen;

import io.swagger.codegen.languages.JavaClientCodegen;
import io.swagger.codegen.languages.LumenServerCodegen;
import io.swagger.codegen.languages.StaticHtmlGenerator;
import io.swagger.codegen.languages.SwaggerGenerator;
import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.Assert.assertEquals;

public class asvTestCases {
    private static final String POM_FILE = "pom.xml";
    private static final String MODEL_ORDER_FILE = "/src/main/java/io/swagger/client/model/Order.java";
    private static final String MODEL_USER_FILE = "/src/main/java/io/swagger/client/model/User.java";
    private static final String MODEL_CATEGORY_FILE = "/src/main/java/io/swagger/client/model/Category.java";
    private static final String MODEL_TAG_FILE = "/src/main/java/io/swagger/client/model/Tag.java";
    private static final String MODEL_PET_FILE = "/src/main/java/io/swagger/client/model/Pet.java";
    private static final String GENERATED_PHP_CONTROLLER_FILE = "lib/app/Http/Controllers/Controller.php";
    private static final String GENERATED_PHP_COMPOSER_FILE = "lib/composer.json";
    private static final String GENERATED_HTML_FILE = "index.html";
    private static final String GENERATED_SWAGGER_FILE = "swagger.json";

    private static final String SWAGGER_VERSION = "2.0";
    private static final String SWAGGER_INFO_TITLE = "Swagger Petstore";
    private static final String SWAGGER_INFO_VERSION = "1.0.0";
    private static final String SWAGGER_HOST = "petstore.swagger.io";
    private static final String SWAGGER_BASE_PATH = "/v2";
    private static final String LUMEN_VERSION = "5.2.*";

    private static final String PET_STORE_JSON_FILE = "src/test/resources/petstore.json";
    private static final String STUDENT_YAML_FILE = "src/test/resources/student.yml";

    @Mock
    private TemporaryFolder folder = new TemporaryFolder();
    @Mock
    private SwaggerParser swaggerParser = new SwaggerParser();

    private static String getFileExtension(File file) {
        String extension = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }

        return extension;
    }

    @BeforeMethod
    public void setUp() throws Exception {
        folder.create();
    }

    @AfterMethod
    public void tearDown() {
        folder.delete();
    }

    @Test
    public void testRequirementOne() throws IOException {
        final String PET_STORE_FILE_NAME = "/petstore.json";
        final File output = folder.getRoot();

        File copied = new File(PET_STORE_JSON_FILE);
        FileUtils.copyFileToDirectory(copied, output); //Copy file to temp folder
        File openApiDoc = new File(output, PET_STORE_FILE_NAME); //Get file from temp folder
        Swagger swagger = swaggerParser.read(String.valueOf(openApiDoc));
        File pom = new File(output, POM_FILE);

        CodegenConfig codegenConfig = new JavaClientCodegen();
        DefaultGenerator gen = new DefaultGenerator();
        codegenConfig.setOutputDir(output.getAbsolutePath());
        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);
        gen.opts(clientOptInput).generate();

        assertNotNull(openApiDoc);
        assertTrue(openApiDoc.exists());
        assertNotNull(pom);
        assertTrue(pom.exists());
        assertEquals(pom.getParent(), openApiDoc.getParent());
    }

    @Test
    public void testRequirementTwo() {
        final Swagger swagger = swaggerParser.read(PET_STORE_JSON_FILE);

        assertEquals(swagger.getSwagger(), SWAGGER_VERSION);
        assertEquals(swagger.getInfo().getTitle(), SWAGGER_INFO_TITLE);
        assertEquals(swagger.getInfo().getVersion(), SWAGGER_INFO_VERSION);
        assertEquals(swagger.getHost(), SWAGGER_HOST);
        assertEquals(swagger.getBasePath(), SWAGGER_BASE_PATH);
    }

    @Test
    public void testRequirementThree() {
        final String JAVA_EXTENSION = ".java";
        final File output = folder.getRoot();

        Swagger swagger = swaggerParser.read(PET_STORE_JSON_FILE);
        CodegenConfig codegenConfig = new JavaClientCodegen();
        codegenConfig.setOutputDir(output.getAbsolutePath());
        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);
        DefaultGenerator gen = new DefaultGenerator();
        gen.opts(clientOptInput).generate();

        File order = new File(output, MODEL_ORDER_FILE);
        File category = new File(output, MODEL_CATEGORY_FILE);
        File tag = new File(output, MODEL_TAG_FILE);
        File user = new File(output, MODEL_USER_FILE);
        File pet = new File(output, MODEL_PET_FILE);

        assertNotNull(order);
        assertNotNull(category);
        assertNotNull(tag);
        assertNotNull(user);
        assertNotNull(pet);

        assertEquals(getFileExtension(order), JAVA_EXTENSION);
        assertEquals(getFileExtension(category), JAVA_EXTENSION);
        assertEquals(getFileExtension(tag), JAVA_EXTENSION);
        assertEquals(getFileExtension(user), JAVA_EXTENSION);
        assertEquals(getFileExtension(pet), JAVA_EXTENSION);
    }

    @Test
    public void testRequirementFour() {
        final File output = folder.getRoot();
        final Swagger swagger = swaggerParser.read(STUDENT_YAML_FILE);

        CodegenConfig codegenConfig = new SwaggerGenerator();
        codegenConfig.setOutputDir(output.getAbsolutePath());
        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);
        DefaultGenerator gen = new DefaultGenerator();
        gen.opts(clientOptInput).generate();

        File swaggerFile = new File(output, GENERATED_SWAGGER_FILE);
        Swagger swaggerGenerated = swaggerParser.read(String.valueOf(swaggerFile));

        assertNotNull(swaggerFile);
        assertTrue(swaggerFile.exists());
        assertEquals(swagger.getSwagger(), swaggerGenerated.getSwagger());
        assertEquals(swagger.getInfo().getTitle(), swaggerGenerated.getInfo().getTitle());
        assertEquals(swagger.getInfo().getVersion(), swaggerGenerated.getInfo().getVersion());
        assertEquals(swagger.getHost(), swaggerGenerated.getHost());
        assertEquals(swagger.getBasePath(), swaggerGenerated.getBasePath());
    }

    @Test
    public void testRequirementFive() {
        final File output = folder.getRoot();

        Swagger swagger = swaggerParser.read(PET_STORE_JSON_FILE);
        CodegenConfig codegenConfig = new JavaClientCodegen();
        codegenConfig.setOutputDir(output.getAbsolutePath());
        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);
        DefaultGenerator gen = new DefaultGenerator();
        gen.opts(clientOptInput).generate();

        File order = new File(output, MODEL_ORDER_FILE);
        File category = new File(output, MODEL_CATEGORY_FILE);
        File tag = new File(output, MODEL_TAG_FILE);
        File user = new File(output, MODEL_USER_FILE);
        File pet = new File(output, MODEL_PET_FILE);

        assertNotNull(order);
        assertNotNull(category);
        assertNotNull(tag);
        assertNotNull(user);
        assertNotNull(pet);

        assertTrue(order.exists());
        assertTrue(category.exists());
        assertTrue(tag.exists());
        assertTrue(user.exists());
        assertTrue(pet.exists());
    }

    @Test
    public void testRequirementSix() {
        final String PHP_EXTENSION = ".php";
        final File output = folder.getRoot();

        Swagger swagger = swaggerParser.read(PET_STORE_JSON_FILE);
        CodegenConfig codegenConfig = new LumenServerCodegen();
        codegenConfig.setOutputDir(output.getAbsolutePath());
        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);
        DefaultGenerator gen = new DefaultGenerator();
        gen.opts(clientOptInput).generate();

        String expectLumenVersion = "";
        File phpFile = new File(output, GENERATED_PHP_CONTROLLER_FILE);
        String phpExtension = getFileExtension(phpFile);
        File composerFile = new File(output, GENERATED_PHP_COMPOSER_FILE);
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(composerFile));
            JSONObject require = (JSONObject) jsonObject.get("require");
            expectLumenVersion = (String) require.get("laravel/lumen-framework");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        assertNotNull(phpFile);
        assertTrue(phpFile.exists());
        assertEquals(phpExtension, PHP_EXTENSION);
        assertNotNull(composerFile);
        assertTrue(composerFile.exists());
        assertEquals(LUMEN_VERSION, expectLumenVersion);
    }

    @Test
    public void testRequirementSeven() {
        final File output = folder.getRoot();

        Swagger swagger = swaggerParser.read(STUDENT_YAML_FILE);
        CodegenConfig codegenConfig = new JavaClientCodegen();
        codegenConfig.setOutputDir(output.getAbsolutePath());
        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);
        DefaultGenerator generator = new DefaultGenerator();
        generator.opts(clientOptInput);

        Map<String, List<CodegenOperation>> paths = generator.processPaths(swagger.getPaths());
        Map<String, Model> models = generator.swagger.getDefinitions();

        assertEquals(1, paths.size());
        assertEquals(4, models.size());
        int counter = 0;
        for (String name : models.keySet()) {
            switch (counter) {
                case 0:
                    assertEquals(name, "Student");
                    break;
                case 1:
                    assertEquals(name, "HttpResponse");
                    break;
                case 2:
                    assertEquals(name, "StudentUpdateCommand");
                    break;
                case 3:
                    assertEquals(name, "StudentSaveCommand");
                    break;
            }
            counter++;
        }
    }

    @Test
    public void testRequirementEight() {
        final String HTML_EXTENSION = ".html";
        final File output = folder.getRoot();

        Swagger swagger = swaggerParser.read(PET_STORE_JSON_FILE);
        CodegenConfig codegenConfig = new StaticHtmlGenerator();
        codegenConfig.setOutputDir(output.getAbsolutePath());
        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).swagger(swagger).config(codegenConfig);
        DefaultGenerator gen = new DefaultGenerator();
        gen.opts(clientOptInput).generate();

        File htmlFile = new File(output, GENERATED_HTML_FILE);
        String htmlExtension = getFileExtension(htmlFile);

        assertNotNull(htmlFile);
        assertTrue(htmlFile.exists());
        assertEquals(htmlExtension, HTML_EXTENSION);
    }
}