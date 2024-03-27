package ngk.devtools.proto.uml.domain.service.parser;

import ngk.devtools.proto.uml.domain.entities.proto.ProtoMessage;
import ngk.devtools.proto.uml.domain.entities.proto.ProtoService;

import java.util.List;

/**
 * Proto files parser
 */
public interface ProtoParser {

    /**
     * Extract the List of messages in the proto file
     *
     * @return The List of {@link ProtoMessage} in the parsed file
     */
    List<ProtoMessage> getMessages();

    @Deprecated
    List<ProtoMessage.ProtoField> getFieldsForMessage(String Message);

    /**
     * Extract the List of services in the proto file
     *
     * @return The List of {@link ProtoService} in the parsed file
     */
    List<ProtoService> getServices();
}
