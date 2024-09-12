package ngk.devtools.proto.uml.service;

import lombok.NonNull;
import ngk.devtools.proto.uml.domain.entities.uml.UmlDiagram;
import ngk.devtools.proto.uml.domain.service.parser.ProtoParserImpl;
import ngk.devtools.proto.uml.domain.entities.plantuml.PlantUmlClassDiagram;

/**
 * The Implementation of the {@link ProtoToUmlService} service
 */
public class ProtoToUmlServiceImpl implements ProtoToUmlService {

    /**
     * {@inheritDoc}
     */
    public String generatePlantUmlDiagramCode(final @NonNull String pathToProtoFile) {

        final ProtoParserImpl protoParser = new ProtoParserImpl(pathToProtoFile);
        final UmlDiagram umlDiagram = UmlDiagram.fromParser(protoParser);
        final PlantUmlClassDiagram plantUmlClassDiagram = PlantUmlClassDiagram.of(umlDiagram);
        return plantUmlClassDiagram.code();
    }
}
