package Analizador;

/**
 *
 * @author iparr
 */
import com.formdev.flatlaf.FlatIntelliJLaf;
import compilerTools.CodeBlock;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import compilerTools.Directory;
import compilerTools.ErrorLSSL;
import compilerTools.Functions;
import compilerTools.Grammar;
import compilerTools.Production;
import compilerTools.TextColor;
import compilerTools.Token;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Compilador extends javax.swing.JFrame {

    private String titulo;//Titulo
    //Nombre del directorio (marca de agua: creditos de la libreria)
    private Directory directorio;//Directorio
    private ArrayList<Token> tokens;//ArrayList de tipo Token (tokens)
    private ArrayList<ErrorLSSL> errores;//Guarda errores lexicos, sintacticos, semanticos, logico
    private ArrayList<TextColor> colorPalabras;//Color de las palabras reservads
    private Timer timerKeyReleased;//Ejecuta la funcion para activar el color de las palabras del editor
    private ArrayList<Production> prodIdentificador;//Extraer los identificadores del analisis sintactico
    private ArrayList<Production> asignaciones;

    private ArrayList<Production> estructuras;
    private ArrayList<Production> llaves_a;
    private ArrayList<Production> opAritmeticas;

    private HashMap<String, String> identificadoresNiveles;
    private HashMap<String, String> tiposDatos;
    private HashMap<String, String> identificadores;//Guardar los identificadores
    private boolean codigoCompilado = false;//Indica si el codigo ya ha sido compilado

    /**
     * Creates new form Compilador
     */
    public Compilador() {
        initComponents();//metodo 
        init();
    }

    private void init() {
        titulo = "J-LyA";//Inicializamos la variable titulo con el nombre del compilador
        setLocationRelativeTo(null);//Metodo para centrar la ventana
        setTitle(titulo);//Poner de titulo en la ventana a la variable titulo
        //parametros: 1ero nombre del jFrame principal, 2do nombre del editor de codigo, 3ero titulo compilador, 4to es la extencion archivos de codigo
        //Genera la marca de agua con los derechos de la libreria utilizada
        directorio = new Directory(this, jtpCodigo, titulo, ".txt");//.robo antes
        addWindowListener(new WindowAdapter() {//Se activa cuando presiona la "X" de la esquina superior derecha
            @Override
            public void windowClosing(WindowEvent e) {
                directorio.Exit();//Pregunta para guardar o descartar cambios antes de cerrar
                System.exit(0);//Salir del programa sin errores
            }
        });
        //Metodo Functions de la libreria compilerTools
        //Dentro del editor de codigo hace que se muestren los numeros de linea
        //Genera la marca de agua con los derechos de la libreria utilizada
        Functions.setLineNumberOnJTextComponent(jtpCodigo);//Pone los numeros de linea
        timerKeyReleased = new Timer((int) (1000 * 0.3), (ActionEvent e) -> {
            timerKeyReleased.stop();//Se detiene la ejecucion del Timer
            int posicion = jtpCodigo.getCaretPosition();
            jtpCodigo.setText(jtpCodigo.getText().replaceAll("[\r]+", ""));
            jtpCodigo.setCaretPosition(posicion);
            analisisColor();//Funcion creada
        });
        //Metodo que le pone un * en el nombre de la ventana cada que modificamos algo en el editor
        Functions.insertAsteriskInName(this, jtpCodigo, () -> {//nombre de la ventana, editor de codigo, funcion anonima (runable) que se ejecuta cada vez que se hace una edicion
            timerKeyReleased.restart();//Cada vez que se hace una edicion llama a este metodo
        });
        //Inicializacion de cada ArrayList a un ArrayList vacio
        tokens = new ArrayList<>();
        errores = new ArrayList<>();
        colorPalabras = new ArrayList<>();
        prodIdentificador = new ArrayList<>();//Identificadores de produccion

        asignaciones = new ArrayList<>();
        estructuras = new ArrayList<>();
        opAritmeticas = new ArrayList<>();
        llaves_a = new ArrayList<>();

        identificadores = new HashMap<>();
        identificadoresNiveles = new HashMap<>();
        tiposDatos = new HashMap<>();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        tblTokens = new javax.swing.JTable();
        buttonsFilePanel = new javax.swing.JPanel();
        btnAbrir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        panelButtonCompilerExecute = new javax.swing.JPanel();
        btnCompilar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtpCodigo = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaSalidaConsola = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Componente léxico", "Lexema", "[Línea, Columna]"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblTokens);

        btnAbrir.setText("Abrir");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsFilePanelLayout = new javax.swing.GroupLayout(buttonsFilePanel);
        buttonsFilePanel.setLayout(buttonsFilePanelLayout);
        buttonsFilePanelLayout.setHorizontalGroup(
            buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAbrir, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        buttonsFilePanelLayout.setVerticalGroup(
            buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbrir)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        btnCompilar.setText("Compilar");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonCompilerExecuteLayout = new javax.swing.GroupLayout(panelButtonCompilerExecute);
        panelButtonCompilerExecute.setLayout(panelButtonCompilerExecuteLayout);
        panelButtonCompilerExecuteLayout.setHorizontalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addComponent(btnCompilar, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelButtonCompilerExecuteLayout.setVerticalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCompilar)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jtpCodigo);

        jtaSalidaConsola.setEditable(false);
        jtaSalidaConsola.setColumns(20);
        jtaSalidaConsola.setRows(5);
        jScrollPane2.setViewportView(jtaSalidaConsola);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Compilador J-LyA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(314, 314, 314)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//Metodos ActionPerformed para cada boton
    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        if (directorio.Open()) {//Si abre el archivo de manera exitosa manda a llamar los metodos de abajo
            analisisColor();
            limpiarCampos();
        }
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        directorio.New();
        limpiarCampos();//Creado para limpiar los campos del editor
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (directorio.Save()) {
            limpiarCampos();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        if (getTitle().contains("*") || getTitle().equals(titulo)) {//Si aun no se ha guardado o tienen un *
            if (directorio.Save()) {//manda a llamar el metodo save del objeto directorio
                compilar();
            }
        } else {//En caso de que el archivo ya haya sido creado y guardado
            compilar();
        }
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void compilar() {//Manda a llamar los sig. metodos:
        limpiarCampos();
        analisisLexico();
        llenarTablaTokens();//Llenar tabla de tokens
        analisisSintactico();
        analisisSemantico();
        imprimirConsola();//Imprimir en consola
        codigoCompilado = true;//Variable de control de compilacion en true
    }

    private void analisisLexico() {
        // Extraer tokens
        Lexer lexer;//Objeto de tipo Lexer
        try {//Buffer de entrada al objeto lexer
            File codigo = new File("code.encrypter");//Archivo de entrada
            FileOutputStream output = new FileOutputStream(codigo);//Archivo de salida
            byte[] bytesText = jtpCodigo.getText().getBytes();//Obtener los bytes del texto
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));//Archivo con los byte en UTF8 para que reconozca los acentos y caracteres extraños
            lexer = new Lexer(entrada);
            //Se ejecuta de forma indefinida el while
            while (true) {
                Token token = lexer.yylex();//yylex retorna cada uno de los tokens de manera consecutiva hasta retornar un null (no tokens)
                if (token == null) {//Salir del ciclo
                    break;
                }//En caso de que no, se agrega el token al ArrayList tokens
                tokens.add(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
    }

    private void analisisSintactico() {
        //Objeto de tipo Grammar de la clase CompillerTools
        Grammar gramatica = new Grammar(tokens, errores);//Arreglo de tokens y arreglo de errores

        /* Deshabilitar mensajes y validaciones */
        gramatica.disableMessages();
        gramatica.disableValidations();

        /* Eliminación de errores del analisis */
        //Error de token desconocido, numero entero que empiece con 0, identificador sin $
        gramatica.group("ERRORES", "(ERROR | ERROR_1 | ERROR_2)", true);//Agrupacion de errores
        gramatica.delete("ERRORES",
                0, " × Línea # - Error: No se esperaba encontrar el token \"[]\", favor de eliminarlo.");

        /* Agrupación de valores (lo que puede valer una variable) */
        gramatica.group("VALOR", "(STRING | NUMERO_INT)", true);//Un valor puede ser un numero entero, decimal, ..., que se detenga a la primera aparicion
        gramatica.group("VALOR_DOUBLE", "(NUMERO_DOUBLE)", true);

        /* Declaración de variables */
        //Estructura de como se declara una variable, parametro adicional para agregar una variable al arreglo de producciones
        gramatica.group("VARIABLE", "TIPO_DATO IDENTIFICADOR OP_ASIG VALOR", true, prodIdentificador);
        gramatica.group("VARIABLE", "IDENTIFICADOR OP_ASIG OP_ARITMETICA", true);
        //Estructura de como se declara un numero decimal Double
        gramatica.group("VARIABLE2", "TIPO_DATO IDENTIFICADOR OP_ASIG VALOR_DOUBLE", true, prodIdentificador);
        /* Errores en la declaración de variables */
        //Estructura en caso de error, se coloca el numero de error, el mensaje y numero de linea
        gramatica.group("VARIABLE", "TIPO_DATO OP_ASIG VALOR", true,
                1, " × Línea # - Error sintáctico {}: falta el identificador en la declaración de variable.");
        gramatica.group("VARIABLE2", "TIPO_DATO OP_ASIG VALOR_DOUBLE", true,
                1, " × Línea # - Error sintáctico {}: falta el identificador en la declaración de variable.");
        //Permite que se coloque el numero del error en la linea y columna final
        //gramatica.finalLineColumn();
        gramatica.group("VARIABLE", "TIPO_DATO IDENTIFICADOR OP_ASIG", true,
                2, " × Línea # - Error sintáctico {}: falta el valor en la declaración de variable.");
        gramatica.group("VARIABLE2", "TIPO_DATO IDENTIFICADOR OP_ASIG", true,
                2, " × Línea # - Error sintáctico {}: falta el valor en la declaración de variable o valor no compatible.");
        //Regresar a la linea y columna inicial
        //gramatica.initialLineColumn();
        gramatica.group("VARIABLE", "TIPO_DATO IDENTIFICADOR VALOR", true,
                3, " × Línea # - Error sintáctico {}: falta el operador de asignación en la declaración de variable.");
        gramatica.group("VARIABLE2", "TIPO_DATO IDENTIFICADOR VALOR_DOUBLE", true,
                3, " × Línea # - Error sintáctico {}: falta el operador de asignación en la declaración de variable.");
        /*gramatica.group("VARIABLE", "IDENTIFICADOR OP_ASIG VALOR", true,
                4, " × Línea # - Error sintáctico {}: falta el tipo de dato en la declaración de variable.");
        gramatica.group("VARIABLE2", "IDENTIFICADOR OP_ASIG VALOR_DOUBLE", true,
                4, " × Línea # - Error sintáctico {}: falta el tipo de dato en la declaración de variable.");*/
        //Punto y coma al final de la declaracion de variables e identificadores
        gramatica.group("VARIABLE_PC", "VARIABLE PUNTO_COMA", true);
        gramatica.group("VARIABLE_PC", "IDENTIFICADOR OP_ASIG OP_ARITMETICA PUNTO_COMA", true);
        gramatica.group("VARIABLE_PC2", "VARIABLE2 PUNTO_COMA", true);
        gramatica.group("VARIABLE_PC", "VARIABLE", true,
                18, " × Línea # - Error sintáctico {}: falta el punto y coma al final de la declaración de variable.");
        gramatica.group("VARIABLE_PC", "IDENTIFICADOR OP_ASIG OP_ARITMETICA", true,
                18, " × Línea # - Error sintáctico {}: falta el punto y coma al final de la declaración de variable.");
        gramatica.group("VARIABLE_PC2", "VARIABLE2", true,
                18, " × Línea # - Error sintáctico {}: falta el punto y coma al final de la declaración de variable.");

        /* Eliminación de tipos de datos y operadores de asignación */
        //Cada vez que encuentre un tipo de dato solo, que no este agrupado lo elimina y muestra el error
        /* gramatica.delete("TIPO_DATO",
                5, " × Línea # - Error sintáctico {}: declaración incompleta");
        gramatica.delete("OP_ASIG",
                6, " × Línea # - Error sintáctico {}: el operador de asignación no está en la declaración de una variable.");
         */
 /* Definición de parametros *//* Agrupación de identificadores como valor y definición de parámetros */
        gramatica.group("VALOR", "(IDENTIFICADOR | TIPO_DATO IDENTIFICADOR)", true);
        // Parametro
        gramatica.group("PARAMETROS", "VALOR (COMA VALOR)+");

        // Agrupacion de funciones // Agrupar la expresión booleana (CASI NO SE USAN LOS ERRORES)
        gramatica.group("FUNCION_COMP", "FUNCION PARENTESIS_A (VALOR | PARAMETROS)? PARENTESIS_C", true);
        gramatica.group("FUNCION_COMP", "FUNCION (VALOR | PARAMETROS)? PARENTESIS_C", true,
                6, " × Línea # - Error sintáctico {}: Falta el paréntesis que abre en la sentencia.");
        gramatica.finalLineColumn();
        gramatica.group("FUNCION_COMP", "FUNCION PARENTESIS_A (VALOR | PARAMETROS)", true,
                7, " × Línea # - Error sintáctico {}: Falta el paréntesis que cierra en la sentencia.");
        // Eliminacion de funciones incompletas
        gramatica.delete("FUNCION", 8,
                " × Línea # - Error sintáctico {}: La sentencia no está declarada correctamente.");

        //Expresion logica y relacional para las estructuras IF y WHILE
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
            gramatica.group("EXP_LOGICA", "(FUNCION_COMP | EXP_LOGICA) (OP_LOGICO_RELACIONAL (FUNCION_COMP | EXP_LOGICA))+");
            gramatica.group("EXP_LOGICA", "PARENTESIS_A (EXP_LOGICA | FUNCION_COMP) PARENTESIS_C");
            gramatica.group("EXP_LOGICA", "PARENTESIS_A VALOR OP_LOGICO_RELACIONAL VALOR PARENTESIS_C");
            gramatica.group("EXP_LOGICA", "PARENTESIS_A VALOR OP_LOGICO_RELACIONAL PARENTESIS_C", true,
                    9, " × Línea # - Error sintáctico {}: Falta el valor en la condición.");
            // En caso de hacer una asignacion (=) y no una evaluacion con un operador relacional (==)
            gramatica.group("EXP_LOGICA", "PARENTESIS_A VALOR OP_ASIG VALOR PARENTESIS_C", true,
                    10, " × Línea # - Error sintáctico {}: No es posible evaluar la condición, error en el operador logico/relacional. [Linea: #, Columna: %]");
        });

        // Eliminacion de operadores logicos y relacionales en caso de error
        gramatica.delete("OP_LOGICO_RELACIONAL", 10,
                " × Línea # - Error sintáctico {}: El operador lógico/relacional no está contenido en una expresión.");

        // Agrupacion de expresiones logicas como valor y parametros
        gramatica.group("VALOR", "EXP_LOGICA");
        gramatica.group("PARAMETROS", "VALOR (COMA VALOR)+");

        // Agrupacion de estructuras de control que puede ser un IF o un WHILE
        gramatica.group("EST_CONTROL", "(ESTRUCTURA_WHILE | ESTRUCTURA_IF)", true);
        //gramatica.group("EST_CONTROL_ELSE", "(ESTRUCTURA_ELSE)", true);

        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL PARENTESIS_A PARENTESIS_C", true,
                11, " × Línea # - Error sintáctico {}: Falta el valor (condición) dentro de la estructura de control.");
        //Permiten que se acepte la estructura completa con los parentesis y la condicion
        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL (VALOR | PARAMETROS)", estructuras);
        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL PARENTESIS_A VALOR PARENTESIS_C", estructuras);

        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL PARENTESIS_A (VALOR | PARAMETROS) PARENTESIS_C", estructuras);
        gramatica.group("EST_CONTROL_COMP", "EST_CONTROL PARENTESIS_A (IDENTIFICADOR) PARENTESIS_C", estructuras);

        // Eliminacion de estructuras de control incompletas (verifica errores cuando faltan parentesis_a o _C)
        gramatica.delete("EST_CONTROL", 12,
                " × Línea # - Error sintáctico {}: La estructura de control no está declarada correctamente.");
        // Eliminacion de parentesis
        gramatica.delete(new String[]{"PARENTESIS_A", "PARENTESIS_C"}, 13,
                " × Línea # - Error sintáctico {}: El paréntesis [] no está contenido en una agrupación.");
        gramatica.finalLineColumn();

        /* Agrupación de sentencias */
        //Declaracion de variable o de funcion
        gramatica.group("SENTENCIAS", "(VARIABLE_PC | VARIABLE_PC2)+");

        /* Estructuras de control completas */
        //Una estructura de controlpuede tener una  sentencia en una llave abre, sentencia, llave cierra
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
            //gramatica.initialLineColumn();
            gramatica.group("EST_CONTROL_COMP_LASLC", "EST_CONTROL_COMP LLAVE_A (SENTENCIAS)? LLAVE_C", true, llaves_a);
            gramatica.group("EST_CONTROL_COMP_LASLC_ELSE", "EST_CONTROL_COMP_LASLC ESTRUCTURA_ELSE LLAVE_A (SENTENCIAS)? LLAVE_C", true, llaves_a);
            gramatica.group("SENTENCIAS", "(SENTENCIAS | EST_CONTROL_COMP_LASLC | EST_CONTROL_COMP_LASLC_ELSE)+");
        });
        //Errores
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
            gramatica.initialLineColumn();
            gramatica.group("EST_CONTROL_COMP_LASLC", "EST_CONTROL_COMP (SENTENCIAS)? LLAVE_C", true,
                    21, " × Línea # - Error sintáctico {}: falta la llave que abre en la estructura de control.", llaves_a);
            //gramatica.finalLineColumn();
            gramatica.group("EST_CONTROL_COMP_LASLC", "EST_CONTROL_COMP LLAVE_A (SENTENCIAS)?",
                    22, " × Línea # - Error sintáctico {}: falta la llave que cierra en la estructura de control.", llaves_a);
            gramatica.group("SENTENCIAS", "(SENTENCIAS | EST_CONTROL_COMP_LASLC)+");
            gramatica.group("EST_CONTROL_COMP_LASLC_ELSE", "ESTRUCTURA_ELSE LLAVE_A (SENTENCIAS)? LLAVE_C", true,
                    23, " × Línea # - Error sintáctico {}: falta la estructura de control: if.", llaves_a);
            gramatica.group("EST_CONTROL_COMP_LASLC_ELSE", "EST_CONTROL_COMP_LASLC ESTRUCTURA_ELSE (SENTENCIAS)? LLAVE_C", true,
                    21, " × Línea # - Error sintáctico {}: falta la llave que abre en la estructura de control: else.", llaves_a);
            //gramatica.finalLineColumn();
            gramatica.group("EST_CONTROL_COMP_LASLC_ELSE", "EST_CONTROL_COMP_LASLC ESTRUCTURA_ELSE LLAVE_A (SENTENCIAS)?",
                    22, " × Línea # - Error sintáctico {}: falta la llave que cierra en la estructura de control: else.", llaves_a);
            gramatica.group("SENTENCIAS", "(SENTENCIAS | EST_CONTROL_COMP_LASLC)+");
        });
        // Eliminacion de operadores logicos y relacionales en caso de error
        gramatica.delete("ESTRUCTURA_ELSE", 29,
                " × Línea # - Error sintáctico {}: Error en la declaración de estructura: else.");
        /* Eliminación de llaves */
        gramatica.delete(new String[]{"LLAVE_A", "LLAVE_C"},
                23, " × Línea # - Error sintáctico {}: la llave [] no está contenida en una agrupación.");
                
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
            gramatica.group("OPERADOR_ARITMETICO", "(OP_SUMA | OP_RESTA | OP_MULTIPLICACION | OP_DIVISION)");
            gramatica.group("OPERANDO", "VALOR | VARIABLE");
            gramatica.group("OP_ARITMETICA", "OPERANDO (OPERADOR_ARITMETICO OPERANDO)+ PUNTO_COMA",opAritmeticas);
            
            gramatica.group("OP_ARITMETICA", "OPERANDO (OPERADOR_ARITMETICO OPERANDO)+ ", true,
                    19, " × Línea # - Error sintáctico {}: El punto y coma no está al final de la operacion aritmetica.");
        });
        /* Eliminación de punto y coma */
        gramatica.delete("PUNTO_COMA",
                20, " × Línea # - Error sintáctico {}: el punto y coma ausente o mal colocado al final de una sentencia.");

        /* Metodo para mostrar gramáticas o producciones agrupadas creadas en consola */
        gramatica.show();

    }

    private void analisisSemantico() {
        //HashMap para asociar un determinado tipo de dato con un valor
        HashMap<String, String> identTipoDato = new HashMap<>();
        //En el hashMap introducimos el tipo de dato y valor correspondiente
        identTipoDato.put("int", "NUMERO_INT");//Clave y valor
        identTipoDato.put("double", "NUMERO_DOUBLE");
        identTipoDato.put("String", "STRING");
        //Para recorrer cada uno de los identificadores del arraylist
        for (Production id : prodIdentificador) {
            //Metodo lexemeRank para imprimir el lexema y lexicalCompRank para imprimir el componente lexico
            //Comparacion del componente lexico y el lexema, en caso de tener una varaible int con un String marca error
            //En caso contrario de que identTipoDato.getLexema(llave) no sea igual al componente lexico
            if (!identTipoDato.get(id.lexemeRank(0)).equals(id.lexicalCompRank(-1))) {//Desde la posicion 0 hasta la ultima posicion
                errores.add(new ErrorLSSL(1, " × Línea # - Error semántico {}: valor no compatible con el tipo de dato.", id, true));//Booleano en caso de que sea la linea inicial
            }
            // verificar declaracion de variables multiples
            if (identificadores.get(id.lexemeRank(1)) != null) {
                errores.add(new ErrorLSSL(3, " × Línea # - Error semántico {}: la variable ya está declarada, no se puede declarar nuevamente.", id, true));
            }

            //Si no hay errores guarda el identificador en el hashmap
            identificadores.put(id.lexemeRank(1), id.lexemeRank(-1));
        }

        int j = 0;
        for (Production id : opAritmeticas) {
            j += 1;
            for (int i = 0; i < id.getSizeTokens(); i++) {
                if (id.lexicalCompRank(i) == "IDENTIFICADOR" || id.lexicalCompRank(i) == "NUMERO_INT" || id.lexicalCompRank(i) == "STRING" || id.lexicalCompRank(i) == "NUMERO_DOUBLE") {
                    if (id.lexicalCompRank(i) == "IDENTIFICADOR") {
                        String valorTipoDato = tiposDatos.get(id.lexemeRank(i));
                        // todo soluciona valortipodato da null
                        System.out.println("-----------------AVERIGUANDO----------------");
                        System.out.println("LEXEME RANK");
                        System.out.println(id.lexemeRank(i));
                        System.out.println("valorTipoDato");
                        System.out.println(valorTipoDato);
                        System.out.println("id lexical comp");
                        System.out.println(id.lexicalCompRank(i + 2));
                        System.out.println("---------------------------------------");
                        if (valorTipoDato != null) {
                            if (!identTipoDato.get(valorTipoDato).equals(id.lexicalCompRank(i + 2))) {
                                errores.add(new ErrorLSSL(4, " × Línea # - Error semántico {}: Los tipos de datos de los operandos no son compatibles.", id, true));
                            }
                        }
                    } else {
                        if (!id.lexicalCompRank(i).equals(id.lexicalCompRank(i + 2)) && id.lexicalCompRank(i + 2) != null) {
                            errores.add(new ErrorLSSL(5, " × Línea # - Error semántico {}: Los tipos de datos de los operandos no son compatibles.", id, true));
                        }
                    }
                }
            }
        }
    }

    private void analisisColor() {
        /* Limpiar el arreglo de textos de color */
        colorPalabras.clear();
        /* Extraer rangos de colores */
        LexerColor lexer;
        try {
            File codigo = new File("color.encrypter");//el archivo que se genera
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = jtpCodigo.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexer = new LexerColor(entrada);
            while (true) {
                TextColor textColor = lexer.yylex();//retorna un textColor
                if (textColor == null) {
                    break;
                }//Agregamos el textColor al arreglo de colorPalabras
                colorPalabras.add(textColor);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }//Paremetros: arreglo de listas de color, nombre del frame, color negro
        Functions.colorTextPane(colorPalabras, jtpCodigo, new Color(40, 40, 40));
    }

    private void llenarTablaTokens() {
        //Recorre todos los tokens
        tokens.forEach(token -> {
            //Cada posicion sera igual a una columna, son 3 columnas: componente lexico, lexema, linea y columna
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
            //Metodo, pasamos como parametro la tabla y el arreglo de objetos
            Functions.addRowDataInTable(tblTokens, data);
        });
    }
//Metodo para mostrar errores e indicar cuando el codigo ya haya sido compilado

    private void imprimirConsola() {
        int sizeErrors = errores.size();//Guardamos en una variable el numero de errores
        if (sizeErrors > 0) {//Si cantidad de errores mayor a 0
            //Metodo para ordenar los errores por numero de linea y columna
            Functions.sortErrorsByLineAndColumn(errores);
            //Variable con cadena vacia (salto de linea) para guardar todos los errores
            String strErrors = "\n";
            //Recorremos cada uno de los errores
            for (ErrorLSSL error : errores) {
                //Obtiene la cadena del error
                String strError = String.valueOf(error);
                //El valor anterior lo concatenamos con la cadena vacia
                strErrors += strError + "\n";
            }//Minimo un error, coloca:
            jtaSalidaConsola.setText("Compilación terminada...\n" + strErrors + "\nLa compilación terminó con errores...");
        } else {//Si no hay errores coloca:
            jtaSalidaConsola.setText("Compilación terminada...");
        }
        jtaSalidaConsola.setCaretPosition(0);
    }

    private void limpiarCampos() {
        //Metodo para limpiar datos de la tabla de tokens
        Functions.clearDataInTable(tblTokens);
        //Limpia el texto de la consola
        jtaSalidaConsola.setText("");
        //Limpia todos los ArrayList
        tokens.clear();
        errores.clear();
        prodIdentificador.clear();
        identificadores.clear();//hashmap

        identificadoresNiveles.clear();
        asignaciones.clear();
        opAritmeticas.clear();
        estructuras.clear();
        tiposDatos.clear();

        codigoCompilado = false;//varaible de control en false
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {//En caso de que no pueda establecer el LookAndFeel
                //Para cambiar el diseño de los botones y del editor
                //Manda a llamar el emtodo setLookAndFeel de la libreria flatlaf
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (UnsupportedLookAndFeelException ex) {
                System.out.println("LookAndFeel no soportado: " + ex);
            }
            new Compilador().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel buttonsFilePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jtaSalidaConsola;
    private javax.swing.JTextPane jtpCodigo;
    private javax.swing.JPanel panelButtonCompilerExecute;
    private javax.swing.JTable tblTokens;
    // End of variables declaration//GEN-END:variables
}
