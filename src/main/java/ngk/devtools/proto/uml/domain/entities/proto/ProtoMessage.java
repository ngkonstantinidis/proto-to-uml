package ngk.devtools.proto.uml.domain.entities.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

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
        private String type;
        /**
         * The full qualified name of the type
         */
        private String messageTypeFullyQualifiedName;

        /**
         * Evaluates if the field is of primitive type by checking the {@link ProtoPrimitiveType}
         * enumeration
         *
         * @return True if the type of the field is primitive, otherwise False
         */
        public boolean isPrimitive() {
            return ProtoPrimitiveType.isPrimitive(type);
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
