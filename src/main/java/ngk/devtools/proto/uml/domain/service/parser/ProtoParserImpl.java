package ngk.devtools.proto.uml.domain.service.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.protostuff.compiler.ParserModule;
import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.parser.Importer;
import io.protostuff.compiler.parser.LocalFileReader;
import io.protostuff.compiler.parser.ProtoContext;
import lombok.NonNull;
import ngk.devtools.proto.uml.domain.entities.proto.ProtoMessage;
import ngk.devtools.proto.uml.domain.entities.proto.ProtoMessage.ProtoField;
import ngk.devtools.proto.uml.domain.entities.proto.ProtoService;

import java.nio.file.Path;
import java.util.List;

/**
 * Implementation of the {@link ProtoParser} using the protostuff library
 */
public class ProtoParserImpl implements ProtoParser {

    /**
     * The context of the protostuff parser
     */
    private final ProtoContext protoContext;

    /**
     * Constructor of the {@link ProtoParserImpl}
     *
     * @param pathToProtoFile The path to the proto file to be parsed
     */
    public ProtoParserImpl(final @NonNull String pathToProtoFile) {

        final Injector injector = Guice.createInjector(new ParserModule());
        final Importer importer = injector.getInstance(Importer.class);
        final Path path = Path.of(pathToProtoFile);
        this.protoContext = importer.importFile(new LocalFileReader(path.getParent()), path.getFileName().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProtoMessage> getMessages() {
        return protoContext.getProto().getMessages().stream()
                .map(it -> ProtoMessage.of(it.getName(), cleanupPackage(it.getFullyQualifiedName()), extractFieldsFromMessage(it)))
                .toList();
    }

    /**
     * Extract the fields of the proto message
     *
     * @param message The message from which the fields are extracted
     * @return The list of {@link ProtoField}
     */
    @NonNull
    private static List<ProtoField> extractFieldsFromMessage(final @NonNull Message message) {

        return message.getFields()
                .stream()
                .map(it -> ProtoField.of(message.getName(), it.getName(), it.getTypeName(), cleanupPackage(it.getType().getFullyQualifiedName()), it.isRepeated()))
                .toList();
    }

    /**
     * Cleanup the fully qualified name by removing the first dot as it is added by the parser
     *
     * @param packageName The name to be corrected
     * @return The corrected fully qualified name
     */
    @NonNull
    private static String cleanupPackage(final @NonNull String packageName) {
        return packageName.replaceFirst("\\.", "");
    }

    @Override
    public List<ProtoField> getFieldsForMessage(String Message) {
        throw new RuntimeException("Not supported yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProtoService> getServices() {
        throw new RuntimeException("Not supported yet");
    }
}
