package ngk.devtools.proto.uml.domain.entities.plantuml;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Represents an association between two entities in the
 * UML diagram
 */
@AllArgsConstructor(staticName = "withName")
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class UmlAssociation {

    /**
     * Represents the start node of the association
     */
    @NonNull
    private AssociationNode from;
    /**
     * Represents the destination node of the association
     */
    @NonNull
    private AssociationNode to;
    /**
     * Represents the name of the association
     */
    private String associationName;

    /**
     * Creates an association between two entities in the UML diagram
     *
     * @param fromClassName The name of the object of the start node
     * @param fromFieldName The name of the object's field of the start node
     * @param toClassName   The name of the object of the destination node
     * @return The {@link UmlAssociation} initialized
     */
    @NonNull
    public static UmlAssociation of(final @NonNull String fromClassName,
                                    final @NonNull String fromFieldName,
                                    final @NonNull String toClassName) {
        return UmlAssociation.of(
                UmlAssociation.AssociationNode.of(fromClassName, fromFieldName),
                UmlAssociation.AssociationNode.of(toClassName, null)
        );
    }

    /**
     * Generates the association code for the diagram
     *
     * @return The code for the UML diagram
     */
    public String toString() {
        return Optional.ofNullable(associationName)
                .map(it -> String.format("%s -> %s: %s\n", from, to, it))
                .orElse(String.format("%s -> %s\n", from, to));
    }

    /**
     * Represents an association node in the diagram
     */
    @AllArgsConstructor(staticName = "of")
    public static class AssociationNode {

        /**
         * The name of the class for an association node
         */
        private String className;
        /**
         * The name of the field for an association node
         */
        private String fieldName;

        /**
         * Generates the association node for the diagram
         *
         * @return The code for the UML diagram
         */
        public String toString() {
            return Optional.ofNullable(fieldName)
                    .map(it -> String.format("%s::%s", className, fieldName))
                    .orElse(className);
        }
    }
}
