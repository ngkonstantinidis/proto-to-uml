package ngk.devtools.proto.uml.service;

import lombok.NonNull;

/**
 * The service to orchestrate the generation of the UML
 * diagrams
 */
public interface ProtoToUmlService {

    /**
     * Generates a PlantUML diagram from a provided proto file
     *
     * @param pathToProtoFile The path to the proto file
     * @return The PlantUML code of the UML diagram
     */
    String generatePlantUmlDiagramCode(final @NonNull String pathToProtoFile);
}
