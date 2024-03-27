package ngk.devtools.proto.uml.domain.service.plantuml;

import lombok.AllArgsConstructor;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlAssociation;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlClass;

import java.util.List;

/**
 * The UML diagram in PlantUML format
 */
@AllArgsConstructor(staticName = "of")
public class PlantUmlClassDiagram {

    /**
     * The list of classes in the PlantUML diagram
     */
    private List<UmlClass> classes;
    /**
     * The list of associations in the plantUML diagram
     */
    private List<UmlAssociation> associations;

    /**
     * The UML diagram in PlantUML code
     *
     * @return The code for the PlantUML to generate the diagram
     */
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("@startuml\n");
        classes.forEach(it -> stringBuilder.append(it.toString()));
        associations.forEach(it -> stringBuilder.append(it.toString()));
        stringBuilder.append("hide empty methods\n");
        stringBuilder.append("@enduml");

        return stringBuilder.toString();
    }
}
