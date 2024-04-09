package ngk.devtools.proto.uml.domain.entities.plantuml;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ngk.devtools.proto.uml.domain.entities.proto.ProtoMessage;
import org.checkerframework.checker.units.qual.N;
import org.javatuples.Pair;

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
                .map(it -> UmlField.of(it.getName(), fieldType(it.getType().stream().map(Pair::getValue0).toList()), it.isRepeated(), it.isOneofPart()))
                .toList();

        return UmlClass.of(protoMessage.getFullyQualifiedName(), fieldsList);
    }

    @NonNull
    private static String fieldType(final @NonNull List<String> fromProtoFieldType) {
        return String.join(",", fromProtoFieldType);
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
        /**
         * A flag to indicate that the field is a collection of
         * items
         */
        private boolean isCollection;
        /**
         * A flag to indicate that the field is part of oneOf
         * structure
         */
        private boolean isOneOfPart;

        /**
         * Returns if the field is of Map type or not
         *
         * @return True if the field is map, otherwise False
         */
        private boolean isMap() {
            return this.isCollection && type.contains(",");
        }

        /**
         * Generates the field code for the diagram
         *
         * @return The code for the UML diagram
         */
        public String toString() {
            if (this.isCollection && !type.contains(",")) {
                return addOneOfHighlighter(String.format("%s: List<%s>", name, type), isOneOfPart);
            } else if (isMap()) {
                return addOneOfHighlighter(String.format("%s: Map<%s>", name, type), isOneOfPart);
            }
            return addOneOfHighlighter(String.format("%s: %s", name, type), isOneOfPart);
        }

        /**
         * Highlight the field if it is part an oneOf structure in a Protocol Buffer
         * message
         *
         * @param code        The diagram code
         * @param isOneOfPart A flag to denote if it is part of oneOf
         * @return The String that import as code in the method with a highlighter if it
         * is part of a oneOf structure, otherwise it is returned as inserted
         */
        @NonNull
        private static String addOneOfHighlighter(final @NonNull String code,
                                                  final boolean isOneOfPart) {
            return isOneOfPart ? "-> " + code : code;
        }
    }
}
