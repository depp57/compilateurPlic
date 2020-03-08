import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Moulinette {

    private static int errorCounter = 0;
    private final String DIR;
    private final static String PATH_TO_JAR = "Plic.jar";
    private final int PLIC_VERSION;

    private Moulinette(String DIR, int PLIC_VERSION) {
        this.DIR = DIR;
        this.PLIC_VERSION = PLIC_VERSION;
        File checkDir = new File(DIR);
        if (!checkDir.exists() || !checkDir.isDirectory())
            System.err.println("ERREUR: Le dossier " + DIR + " n'existe pas");
        else if (isJARpresent())
            lancerTests();
        else
            System.err.println("ERREUR: Veuillez lancer la moulinette à la même arborescence que Plic.jar");
    }

    private void lancerTests() {
        try (Stream<Path> paths = Files.walk(Paths.get(DIR))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(this::lancerTest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nNb d'erreurs : " + errorCounter);
    }

    private void lancerTest(Path path) {
        if (!isTest(path))
            return;
        System.out.println(path);

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", PATH_TO_JAR, path.toString());
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
            BufferedReader in2 = new BufferedReader(new FileReader(getResPath(path.toString())));
            String output, attendu;
            boolean juste = true;

            output = in.readLine();
            attendu = in2.readLine();

            if (output != null && attendu == null || (output == null && attendu != null))
                juste = false;

            else if (output != null)
                juste = output.startsWith(attendu);

            if (!juste) {
                System.out.println("ERREUR: " + path + " " + output);
                errorCounter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResPath(String sourcePath) {
        return sourcePath.replace("source_" + PLIC_VERSION, "res_" + PLIC_VERSION);
    }

    private boolean isTest(Path path) {
        String pathStr = path.toString();
        if (pathStr.endsWith(".plic") && pathStr.contains("source_" + PLIC_VERSION)) {
            boolean resPath = Path.of(getResPath(pathStr)).toFile().exists();
            if (resPath)
                return true;
            else
                System.err.println("Le fichier " + pathStr + " n'a pas de résultat attendu (res_" + PLIC_VERSION + "_[num].plic)");
        }
        return false;
    }

    private boolean isJARpresent() {
        //TODO a modifier avec chemin relatif quand .jar
        File tempFile = new File(PATH_TO_JAR);
        return tempFile.exists();
    }

    public static void main(String[] args) {
        if (args.length == 2)
            new Moulinette(args[0], Integer.parseInt(args[1]));
        else
            System.out.println("usage : java -jar Moulinette.java [pathToTests] [plicVersion (0/1...)]");
    }
}