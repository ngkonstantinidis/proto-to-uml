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

public class ProtoParserImpl implements ProtoParser {

    private final ProtoContext protoContext;

    public ProtoParserImpl(final @NonNull String pathToProtoFile) {

        final Injector injector = Guice.createInjector(new ParserModule());
        final Importer importer = injector.getInstance(Importer.class);
        Path path = Path.of(pathToProtoFile);
        this.protoContext = importer.importFile(new LocalFileReader(path.getParent()), path.getFileName().toString());
    }

    @Override
    public List<ProtoMessage> getMessages() {
        return protoContext.getProto().getMessages().stream()
                .map(it -> ProtoMessage.of(it.getName(), cleanupPackage(it.getFullyQualifiedName()), extractFieldsFromMessage(it)))
                .toList();
    }

    @NonNull
    private static List<ProtoField> extractFieldsFromMessage(final @NonNull Message message) {

        return message.getFields()
                .stream()
                .map(it -> ProtoField.of(message.getName(), it.getName(), it.getTypeName(), cleanupPackage(it.getType().getFullyQualifiedName())))
                .toList();
    }

    @NonNull
    private static String cleanupPackage(final @NonNull String packageName) {
        return packageName.replaceFirst("\\.", "");
    }

    @Override
    public List<ProtoField> getFieldsForMessage(String Message) {
        throw new RuntimeException("Not supported yet");
    }

    @Override
    public List<ProtoService> getServices() {
        throw new RuntimeException("Not supported yet");
    }
}
