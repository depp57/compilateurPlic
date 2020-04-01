package plic.repint;

public abstract class Outils {

    public static String empilerV0() {
        return "sw $v0, ($sp)   # Empile v0" +
                "\n\tadd $sp, $sp, -4";
    }

    public static String depiler(String registre) {
        return "add $sp, $sp, 4    # Depile v0" +
                "\n\tlw " + registre + ", ($sp)";
    }

    public static int getCptBranch() {
        int cptBranch = TDS.getInstance().getCptBranch();
        TDS.getInstance().incrementsCptBranch();
        return cptBranch;
    }
}