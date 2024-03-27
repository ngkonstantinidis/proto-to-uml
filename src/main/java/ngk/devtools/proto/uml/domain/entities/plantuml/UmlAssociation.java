package ngk.devtools.proto.uml.domain.entities.plantuml;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(staticName = "withName")
@RequiredArgsConstructor(staticName = "of")
public class UmlAssociation {

    @NonNull
    private AssociationNode from;
    @NonNull
    private AssociationNode to;
    private String associationName;

//    public static UmlAssociation fromProtoMessage(ProtoMessage protoMessage,
//                                                  ProtoMessage.ProtoField protoField) {
//
//        String messageTypeFullyQualifiedName = protoField.getMessageTypeFullyQualifiedName();
//
//    }

    public String toString() {
        return Optional.ofNullable(associationName)
                .map(it -> String.format("%s -> %s: %s\n", from, to, it))
                .orElse(String.format("%s -> %s\n", from, to));
    }

    @AllArgsConstructor(staticName = "of")
    public static class AssociationNode {

        private String className;
        private String fieldName;

        public String toString() {
            return Optional.ofNullable(fieldName)
                    .map(it -> String.format("%s::%s", className, fieldName))
                    .orElse(className);
        }
    }
}
