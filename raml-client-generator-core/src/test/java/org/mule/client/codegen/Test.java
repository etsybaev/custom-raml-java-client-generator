package org.mule.client.codegen;

import com.sun.codemodel.JClassAlreadyExistsException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Test {


    private static final List<String> resources =
            Arrays.asList(
                    "tasks/api.raml");

    public static void main(String[] args) {
        new Test().generate();
    }

    private void generate() {
        String projectName = "com.example.generated.pojo";
        final File actualTarget = new File("src/main/java");
        System.out.println("targetFolder = " + actualTarget);
        actualTarget.mkdirs();

        resources.forEach(
                path -> {
                    final URL resource = this.getClass().getClassLoader().getResource(path);
                    try {
                        new RamlJavaClientGenerator(
                                projectName + "." + (path.split("/")[0]).replace("-", ""), actualTarget)
                                .generate(resource);
                    } catch (JClassAlreadyExistsException | IOException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException(
                                "File not found "
                                        + path);
                    }
                });
    }
}
