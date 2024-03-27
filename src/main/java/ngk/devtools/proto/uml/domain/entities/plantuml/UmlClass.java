package ngk.devtools.proto.uml.domain.entities.plantuml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ngk.devtools.proto.uml.domain.entities.proto.ProtoMessage;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(staticName = "of")
@Getter
public class UmlClass {

    private String name;

    private List<UmlField> fields;

    @NonNull
    public static UmlClass fromProtoMessage(final @NonNull ProtoMessage protoMessage) {
        List<UmlField> fieldsList = protoMessage.getFields().stream().map(it -> UmlField.of(it.getName(), it.getType())).toList();

        return UmlClass.of(protoMessage.getFullyQualifiedName(), fieldsList);
    }

    @Override
    public String toString() {
        return String.format("class %s {\n\t%s \n}\n", name, fields.stream().map(UmlField::toString).collect(Collectors.joining(",\n\t")));
    }

    @AllArgsConstructor(staticName = "of")
    public static class UmlField {

        private String name;
        private String type;

        public String toString() {
            return String.format("%s: %s", name, type);
        }
    }
}
