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
    private static final String SIMPLE_PROTO_FILE = "simple.proto";
    private static final String ALL_TYPES_PROTO_FILE = "all_types.proto";
    private static final String SIMPLE_PLANT_FILE = "simple.plantuml";
    private static final String ALL_TYPES_PLANT_FILE = "all_types.plantuml";

    @Test
    public void testValid_validSimpleProto_validSimplePlantUML() {

        final String pathToProto = getProtoPathFromClasspath(SIMPLE_PROTO_FILE);
        final String generatedPlantUml = protoToUmlService.generatePlantUmlDiagramCode(pathToProto);

        assertEquals(getExpected(SIMPLE_PLANT_FILE), generatedPlantUml);
    }

    @Test
    public void testValid_validAllTypesProto_validAllTypesPlantUML() {

        final String pathToProto = getProtoPathFromClasspath(ALL_TYPES_PROTO_FILE);
        final String generatedPlantUml = protoToUmlService.generatePlantUmlDiagramCode(pathToProto);

        assertEquals(getExpected(ALL_TYPES_PLANT_FILE), generatedPlantUml);
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