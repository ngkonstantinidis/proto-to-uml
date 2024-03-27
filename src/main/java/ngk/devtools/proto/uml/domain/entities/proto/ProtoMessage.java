package ngk.devtools.proto.uml.domain.entities.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class ProtoMessage {

    private String name;
    private String fullyQualifiedName;
    private List<ProtoField> fields;

    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class ProtoField {

        private String messageName;
        private String name;
        private String type;
        private String messageTypeFullyQualifiedName;

        public boolean isPrimitive() {
            return ProtoPrimitiveType.isPrimitive(type);
        }
    }

    @AllArgsConstructor
    @Getter
    enum ProtoPrimitiveType {
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

        private final String value;

        public static boolean isPrimitive(String text) {
            for (ProtoPrimitiveType type : ProtoPrimitiveType.values()) {
                if (type.value.equalsIgnoreCase(text)) {
                    return true;
                }
            }
            return false;
        }
    }
}
