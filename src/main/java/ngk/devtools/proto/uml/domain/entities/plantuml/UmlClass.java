package ngk.devtools.proto.uml.domain.entities.plantuml;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ngk.devtools.proto.uml.domain.entities.proto.ProtoMessage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a class object in the UML diagram
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
@Getter
public class UmlClass {

    /**
     * The name of the class
     */
    private String name;
    /**
     * The list of the fields in the class object
     */
    private List<UmlField> fields;

    /**
     * Generates a class for the UML diagram
     *
     * @param protoMessage The proto message from which a class object is generated
     * @return An initialized {@link UmlClass}
     */
    @NonNull
    public static UmlClass fromProtoMessage(final @NonNull ProtoMessage protoMessage) {
        final List<UmlField> fieldsList = protoMessage.getFields()
                .stream()
                .map(it -> UmlField.of(it.getName(), it.getType(), it.isRepeated()))
                .toList();

        return UmlClass.of(protoMessage.getFullyQualifiedName(), fieldsList);
    }

    /**
     * Generates the class code for the diagram
     *
     * @return The code for the UML diagram
     */
    @Override
    public String toString() {
        return String.format(
                "class %s {\n\t%s\n}\n",
                name,
                fields.stream().map(UmlField::toString).collect(Collectors.joining(",\n\t"))
        );
    }

    /**
     * Represents a field in a UML diagram
     */
    @AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
    public static class UmlField {

        /**
         * The name of the field
         */
        private String name;
        /**
         * The type of the field
         */
        private String type;

        private boolean isList;

        /**
         * Generates the field code for the diagram
         *
         * @return The code for the UML diagram
         */
        public String toString() {
            if (this.isList) {
                return String.format("%s: List<%s>", name, type);
            }
            return String.format("%s: %s", name, type);
        }
    }
}
