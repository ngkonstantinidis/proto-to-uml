package ngk.devtools.proto.uml.application.service;

import lombok.NonNull;

public interface ProtoToUmlService {

    String generatePlantUmlDiagramCode(final @NonNull String pathToProtoFile);
}
