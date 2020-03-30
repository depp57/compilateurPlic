import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Moulinette {

    private static int errorCounter = 0;
    private String DIR ="sources/plic";
    private final static String PATH_TO_JAR = "Plic.jar";
    private final String PATH_TO_MARS = "Mars4_5.jar";
    private final int PLIC_VERSION;
    private final String PATH_TEMP_FILE = "output.asm";

    private Moulinette(int PLIC_VERSION) {
        this.PLIC_VERSION = PLIC_VERSION;
        DIR += PLIC_VERSION;
        File checkDir = new File(DIR);
        if (!checkDir.exists() || !checkDir.isDirectory())
            System.err.println("ERREUR: Le dossier " + DIR + " n'existe pas");
        else if (!isJARpresent())
            System.err.println("ERREUR: Veuillez lancer la moulinette à la même arborescence que Plic.jar");
        else if (!isMARSpresent())
            System.err.println("ERREUR: " + PATH_TO_MARS + " incorrect");
        else
            lancerTests();

        File tempFile = new File(PATH_TEMP_FILE);
        tempFile.delete();
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
            String current;
            StringBuffer output = new StringBuffer(), attendu = new StringBuffer();

            while ((current = in.readLine()) != null)
                output.append(current).append("\n");

            while ((current = in2.readLine()) != null)
                attendu.append(current);

            if (!sameOutput(output, attendu)) {
                if (output.length() < 100)
                    System.out.println("ERREUR: " + path + " " + output);
                else
                    System.out.println("ERREUR: " + path + " résultat du Mips n'est pas égal au res attendu");
                errorCounter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean sameOutput(StringBuffer output, StringBuffer attendu) {
        String a = output.toString(), b = attendu.toString();
        if (a.startsWith("ERREUR") && b.startsWith("ERREUR"))
            return true;
        else if (a.startsWith("ERREUR"))
            return false;
        else
            return checkAssemblyOutput(a, b);
    }

    private boolean checkAssemblyOutput(String output, String waitingFor) {
        try {
            FileWriter tempFile = new FileWriter(PATH_TEMP_FILE);
            tempFile.write(output);
            tempFile.close();

            ProcessBuilder pb = new ProcessBuilder("java", "-jar", PATH_TO_MARS, PATH_TEMP_FILE, "nc");
            BufferedReader in = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));

            String current;
            StringBuffer buffer = new StringBuffer();
            while ((current = in.readLine()) != null)
                buffer.append(current);

            return buffer.toString().equals(waitingFor);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    private boolean isMARSpresent() {
        File tempFile = new File(PATH_TO_MARS);
        return tempFile.exists();
    }

    public static void main(String[] args) {
        if (args.length == 1)
            new Moulinette(Integer.parseInt(args[0]));
        else
            System.out.println("usage : java -jar Moulinette.java [plicVersion (0/1...)]");
    }
}