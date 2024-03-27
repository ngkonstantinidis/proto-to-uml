package ngk.devtools.proto.uml.application.service;

import lombok.NonNull;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlAssociation;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlClass;
import ngk.devtools.proto.uml.domain.service.parser.ProtoParserImpl;
import ngk.devtools.proto.uml.domain.service.plantuml.PlantUmlClassDiagram;

import java.util.List;

/**
 * The Implementation of the {@link ProtoToUmlService} service
 */
public class ProtoToUmlServiceImpl implements ProtoToUmlService {

    /**
     * {@inheritDoc}
     */
    public String generatePlantUmlDiagramCode(final @NonNull String pathToProtoFile) {

        final ProtoParserImpl protoParser = new ProtoParserImpl(pathToProtoFile);
        final List<UmlClass> plantUmlClasses = protoParser.getMessages().stream()
                .map(UmlClass::fromProtoMessage)
                .toList();

        final List<UmlAssociation> plantUmlAssociations = protoParser.getMessages()
                .stream()
                .flatMap(it -> it.getFields().stream())
                .filter(it -> !it.isPrimitive())
                .map(it -> UmlAssociation.of(it.getMessageName(), it.getName(), it.getType()))
                .toList();

        final PlantUmlClassDiagram plantUmlClassDiagram = PlantUmlClassDiagram.of(plantUmlClasses, plantUmlAssociations);
        return plantUmlClassDiagram.toString();
    }
}
