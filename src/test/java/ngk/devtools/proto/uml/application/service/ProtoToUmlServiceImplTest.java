package ngk.devtools.proto.uml.application.service;

import com.google.common.io.Resources;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProtoToUmlServiceImplTest {

    private final ProtoToUmlService protoToUmlService = new ProtoToUmlServiceImpl();

    @Test
    public void testValid_validSimpleProto_validSimplePlantUML() {

        final String pathToProto = getProtoPathFromClasspath("simple.proto");
        final String generatedPlantUml = protoToUmlService.generatePlantUmlDiagramCode(pathToProto);

        assertEquals(getExpected("simple.plantuml"), generatedPlantUml);
    }

    private static String getProtoPathFromClasspath(final @NonNull String fileClasspath) {

        return Optional.ofNullable(Thread.currentThread().getContextClassLoader().getResource(fileClasspath))
                .map(URL::getPath)
                .orElseThrow();

    }

    private static String getExpected(final @NonNull String fileClasspath) {
        return Optional.ofNullable(Thread.currentThread().getContextClassLoader().getResource(fileClasspath))
                .map(ProtoToUmlServiceImplTest::resourceToStringUnchecked)
                .orElseThrow();

    }

    private static String resourceToStringUnchecked(final URL resourceUrl) {
        try {
            return Resources.toString(resourceUrl, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}