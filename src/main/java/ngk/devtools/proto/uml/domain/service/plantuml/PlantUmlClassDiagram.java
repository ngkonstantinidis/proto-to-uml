package ngk.devtools.proto.uml.domain.service.plantuml;

import lombok.AllArgsConstructor;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlAssociation;
import ngk.devtools.proto.uml.domain.entities.plantuml.UmlClass;

import java.util.List;

@AllArgsConstructor(staticName = "of")
public class PlantUmlClassDiagram {

    private List<UmlClass> classes;
    private List<UmlAssociation> associations;

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
