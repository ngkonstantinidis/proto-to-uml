package ngk.devtools.proto.uml.domain.entities.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.Triple;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Message in the proto file
 */
@AllArgsConstructor(staticName = "of")
@Getter
public class ProtoMessage {

    /**
     * The message name
     */
    private String name;
    /**
     * The message fully qualified name (including the package)
     */
    private String fullyQualifiedName;
    /**
     * A list of the message fields
     */
    private List<ProtoField> fields;

    /**
     * Represents a field in a proto message in the proto file
     */
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class ProtoField {

        /**
         * The name of the message that the field belongs
         */
        private String messageName;
        /**
         * The name of the field
         */
        private String name;
        /**
         * The type of the field
         */
        private List<Pair<String, String>> type;
        /**
         * The full qualified name of the type
         */
//        private String messageTypeFullyQualifiedName;
        /**
         * A flag to identify if the field is repeated,
         * meaning that has many instances (List in Java)
         * or not
         */
        private boolean isRepeated;

        @NonNull
        public static ProtoField of(final @NonNull String messageName,
                                    final @NonNull String name,
                                    final @NonNull String type,
                                    final @NonNull String messageTypeFullyQualifiedName,
                                    final boolean isRepeated) {

            return ProtoField.of(
                    messageName,
                    name,
                    List.of(Pair.with(type, messageTypeFullyQualifiedName)),
                    isRepeated
            );
        }

        @NonNull
        public static ProtoField of(final @NonNull String messageName,
                                    final @NonNull String name,
                                    final @NonNull String keyType,
                                    final @NonNull String keyTypeFullyQualifiedName,
                                    final @NonNull String valueType,
                                    final @NonNull String valueTypeFullyQualifiedName,
                                    final boolean isRepeated) {

            return ProtoField.of(
                    messageName,
                    name,
                    List.of(
                            Pair.with(keyType, keyTypeFullyQualifiedName),
                            Pair.with(valueType, valueTypeFullyQualifiedName)
                    ),
                    isRepeated
            );
        }

        public boolean isMap() {
            return type.size() == 2 && isRepeated;
        }

        public List<Quartet<String, String, String, Boolean>> toTuple() {
            return type.stream()
                    .map(it -> Quartet.with(this.messageName, this.name, it.getValue0(), ProtoPrimitiveType.isPrimitive(it.getValue0())))
                    .toList();
        }
    }

    /**
     * Enumeration with all the primitive types of a protocol buffer field
     */
    @AllArgsConstructor
    @Getter
    private enum ProtoPrimitiveType {
        DOUBLE("double"),
        FLOAT("float"),
        INT32("int32"),
        INT64("int64"),
        UINT32("uint32"),
        UINT64("uint64"),
        SINT32("sint32"),
        SINT64("sint64"),
        FIXED32("fixed32"),
        FIXED64("fixed64"),
        SFIXED32("sfixed32"),
        SFIXED64("sfixed64"),
        BOOL("bool"),
        STRING("string"),
        BYTES("bytes");

        /**
         * The formal definition of the type in protocol buffer
         */
        private final String value;

        /**
         * Evaluates if a given type belongs to the primitive list
         *
         * @param type The type to be checked
         * @return True if the type of the field is primitive, otherwise False
         */
        public static boolean isPrimitive(final @NonNull String type) {
            return Arrays.stream(ProtoPrimitiveType.values())
                    .anyMatch(it -> it.value.equalsIgnoreCase(type));
        }
    }
}
