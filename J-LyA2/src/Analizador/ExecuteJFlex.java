package Analizador;


import jflex.exceptions.SilentExit;

/**
 *
 *
 */
public class ExecuteJFlex {//Se crea para llamar el metodo de la libreria JFlex

    public static void main(String omega[]) {
        String lexerFile = System.getProperty("user.dir") + "/src/Analizador/Lexer.flex",
                lexerColor = System.getProperty("user.dir") + "/src/Analizador/LexerColor.flex";
        try {
            jflex.Main.generate(new String[]{lexerFile, lexerColor});
        } catch (SilentExit ex) {
            System.out.println("Error al compilar/generar el archivo flex: " + ex);
        }
    }
}
