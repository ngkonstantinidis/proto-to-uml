package ngk.devtools.proto.uml;


import ngk.devtools.proto.uml.application.service.ProtoToUmlService;
import ngk.devtools.proto.uml.application.service.ProtoToUmlServiceImpl;

public class ProtoToUmlApp {

    private static final ProtoToUmlService protoToUmlService = new ProtoToUmlServiceImpl();

    public static void main(String[] args) {
        final String protoFilePath = args[0];
        final String plantUmlClassDiagram = protoToUmlService.generatePlantUmlDiagramCode(protoFilePath);
        System.out.println(plantUmlClassDiagram);
    }

}

