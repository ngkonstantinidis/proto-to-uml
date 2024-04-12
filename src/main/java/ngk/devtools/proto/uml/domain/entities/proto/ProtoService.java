package ngk.devtools.proto.uml.domain.entities.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a Service in the proto file
 */
@AllArgsConstructor(staticName = "of")
@Getter
public class ProtoService {
    /**
     * The service name
     */
    private String name;
    /**
     * The service fully qualified name (including the package)
     */
    private String fullyQualifiedName;
    /**
     * The list of the service methods
     */
    private List<ProtoMethod> methods;

    /**
     * Represents a method in a proto service in the proto file
     */
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class ProtoMethod {
        /**
         * The name of the service that the field belongs
         */
        private String serviceName;
        /**
         * The name of the method
         */
        private String name;
        /**
         * The type of the input parameter, in simple and fqdn
         * format
         */
        private Pair<String, String> inputParamType;
        /**
         * The type of the return value, in simple and fqdn
         * format
         */
        private Pair<String, String> returnType;

        /**
         * Provides some details for the method in a Tuple format to be used
         * on the processing
         *
         * @return THe service name, the method name and the type of the input
         * parameter or return
         */
        @NonNull
        public List<Triplet<String, String, String>> toTuple() {
            return Stream.of(inputParamType, returnType)
                    .map(it -> Triplet.with(this.serviceName, this.name, it.getValue0()))
                    .toList();
        }
    }
}
