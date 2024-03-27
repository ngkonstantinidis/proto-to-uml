package ngk.devtools.proto.uml.application.service;

import lombok.NonNull;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlAssociation;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlClass;
import ngk.devtools.proto.uml.domain.service.plantuml.PlantUmlClassDiagram;
import ngk.devtools.proto.uml.domain.service.parser.ProtoParserImpl;

import java.util.List;

public class ProtoToUmlServiceImpl implements ProtoToUmlService{

    public String generatePlantUmlDiagramCode(final @NonNull String pathToProtoFile)  {

        final ProtoParserImpl protoParser = new ProtoParserImpl(pathToProtoFile);
        final List<UmlClass> plantUmlClasses = protoParser.getMessages().stream()
                .map(UmlClass::fromProtoMessage)
                .toList();

        final List<UmlAssociation> plantUmlAssociations = protoParser.getMessages()
                .stream()
                .flatMap(it -> it.getFields().stream())
                .filter(it -> !it.isPrimitive())
                .map(it -> UmlAssociation.of(UmlAssociation.AssociationNode.of(it.getMessageName(), it.getName()), UmlAssociation.AssociationNode.of(it.getType(), null)))
                .toList();

        final PlantUmlClassDiagram plantUmlClassDiagram = PlantUmlClassDiagram.of(plantUmlClasses, plantUmlAssociations);
        return plantUmlClassDiagram.toString();
    }
}
