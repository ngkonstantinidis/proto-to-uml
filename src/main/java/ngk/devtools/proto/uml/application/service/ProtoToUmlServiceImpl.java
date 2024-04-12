package ngk.devtools.proto.uml.application.service;

import lombok.NonNull;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlAssociation;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlClass;
import ngk.devtools.proto.uml.domain.service.parser.ProtoParserImpl;
import ngk.devtools.proto.uml.domain.service.plantuml.PlantUmlClassDiagram;

import java.util.List;
import java.util.stream.Stream;

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

        final List<UmlClass> plantUmlClassesForService = protoParser.getServices().stream()
                .map(UmlClass::fromProtoService)
                .toList();

        final List<UmlAssociation> plantUmlAssociations = protoParser.getMessages()
                .stream()
                .flatMap(it -> it.getFields().stream())
                .flatMap(it -> it.toTuple().stream())
                .filter(it -> !it.getValue3())
                .map(it -> UmlAssociation.of(it.getValue0(), it.getValue1(), it.getValue2()))
                .toList();

        final List<UmlAssociation> plantUmlAssociationsForService = protoParser.getServices()
                .stream()
                .flatMap(it -> it.getMethods().stream())
                .flatMap(it -> it.toTuple().stream())
                .map(it -> UmlAssociation.of(it.getValue0(), it.getValue1(), it.getValue2()))
                .toList();

        final PlantUmlClassDiagram plantUmlClassDiagram = PlantUmlClassDiagram.of(
                Stream.concat(plantUmlClasses.stream(), plantUmlClassesForService.stream()).toList(),
                Stream.concat(plantUmlAssociations.stream(), plantUmlAssociationsForService.stream()).toList()
        );
        return plantUmlClassDiagram.toString();
    }
}
