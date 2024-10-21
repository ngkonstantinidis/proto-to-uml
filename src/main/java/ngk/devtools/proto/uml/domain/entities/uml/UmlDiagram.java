package ngk.devtools.proto.uml.domain.entities.uml;

import lombok.NonNull;
import ngk.devtools.proto.uml.domain.service.parser.ProtoParser;

import java.util.List;
import java.util.stream.Stream;

public record UmlDiagram(List<UmlClass> classes, List<UmlAssociation> associations) {

    public static UmlDiagram fromParser(final @NonNull ProtoParser protoParser) {

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

        return new UmlDiagram(
                Stream.concat(plantUmlClasses.stream(), plantUmlClassesForService.stream()).toList(),
                Stream.concat(plantUmlAssociations.stream(), plantUmlAssociationsForService.stream()).toList()
        );
    }
}