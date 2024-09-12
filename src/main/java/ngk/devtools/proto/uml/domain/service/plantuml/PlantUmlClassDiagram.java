package ngk.devtools.proto.uml.domain.service.plantuml;

import ngk.devtools.proto.uml.domain.entities.plantuml.UmlDiagram;

/**
 * The UML diagram in PlantUML format
 */
public record PlantUmlClassDiagram(String code) {

    public static PlantUmlClassDiagram of(UmlDiagram umlDiagram) {
        return new PlantUmlClassDiagram(getCodeOf(umlDiagram));
    }

    /**
     * Create the UML diagram in PlantUML code
     *
     * @return The code for the PlantUML to generate the diagram
     */
    private static String getCodeOf(UmlDiagram umlDiagram) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("@startuml\n");
        umlDiagram.classes().forEach(it -> stringBuilder.append(it.toString()));
        umlDiagram.associations().forEach(it -> stringBuilder.append(it.toString()));
        stringBuilder.append("hide empty methods\n");
        stringBuilder.append("hide empty fields\n");
        stringBuilder.append("@enduml");

        return stringBuilder.toString();
    }
}
