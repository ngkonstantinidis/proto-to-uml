package ngk.devtools.proto.uml.application;


import ngk.devtools.proto.uml.service.ProtoToUmlService;
import ngk.devtools.proto.uml.service.ProtoToUmlServiceImpl;

public class ProtoToUmlApp {

    private static final ProtoToUmlService protoToUmlService = new ProtoToUmlServiceImpl();

    public static void main(String[] args) {
        final String protoFilePath = args[0];
        final String plantUmlClassDiagram = protoToUmlService.generatePlantUmlDiagramCode(protoFilePath);
        System.out.println(plantUmlClassDiagram);
    }

}

