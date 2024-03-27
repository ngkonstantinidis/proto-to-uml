package ngk.devtools.proto.uml.domain.service.parser;

import ngk.devtools.proto.uml.domain.entities.proto.ProtoMessage;
import ngk.devtools.proto.uml.domain.entities.proto.ProtoService;

import java.util.List;

public interface ProtoParser {

    List<ProtoMessage> getMessages();

    List<ProtoMessage.ProtoField> getFieldsForMessage(String Message);

    List<ProtoService> getServices();
}
