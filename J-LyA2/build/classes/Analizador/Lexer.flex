package Analizador;
import compilerTools.Token;

%%
%class Lexer
%type Token
%line
%column
%{/* Metodo que retorna un objeto de tipo Token */
    private Token token(String lexema, String componenteLexico, int linea, int columna){
        return new Token(lexema, componenteLexico, linea+1, columna+1);
    }
%}
/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinDeLineaComentario = "//" {EntradaDeCaracter}* {TerminadorDeLinea}?
ContenidoComentario = ( [^*] | \*+ [^/*] )*
ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"

/* Comentario */
Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Identificador */
Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*

/* Número */
Numero = 0 | [1-9][0-9]*
/* Decimal */
Decimal = {Digito}+"."{Digito}*
/* Tipo de dato String */
Cadena = [{Letra}+" "({Letra}|{Digito})]*
%%

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }

/* Identificador */
\${Identificador} { return token(yytext(), "IDENTIFICADOR", yyline, yycolumn); }

/* Tipos de dato */
int | double | String { return token(yytext(), "TIPO_DATO", yyline, yycolumn); }

/* Número */
{Numero} { return token(yytext(), "NUMERO_INT", yyline, yycolumn); }
/* Número Decimal */
{Decimal} { return token(yytext(), "NUMERO_DOUBLE", yyline, yycolumn); }
/* String */
\"{Cadena}\" { return token(yytext(), "STRING", yyline, yycolumn); }

/* Operadores de agrupación */
"(" { return token(yytext(), "PARENTESIS_A", yyline, yycolumn); }
")" { return token(yytext(), "PARENTESIS_C", yyline, yycolumn); }
"{" { return token(yytext(), "LLAVE_A", yyline, yycolumn); }
"}" { return token(yytext(), "LLAVE_C", yyline, yycolumn); }

/* Signos de puntuación */
"," { return token(yytext(), "COMA", yyline, yycolumn); }
";" { return token(yytext(), "PUNTO_COMA", yyline, yycolumn); }
"." { return token(yytext(), "PUNTO", yyline, yycolumn); }
"\"" { return token(yytext(), "COMILLA_DOBLE", yyline, yycolumn); }

/* Operador de asignación */
= { return token (yytext(), "OP_ASIG", yyline, yycolumn); }

/* Operadores logicos y operadores relacionales*/
"&&" |
"||" |
">" |
"<" |
"==" |
"!=" |
">=" |
"<="
{ return token(yytext(), "OP_LOGICO_RELACIONAL", yyline, yycolumn); }

/* Operadores relacionales 
">" |
"<" |
"==" |
"!=" |
">=" |
"<=" { return token(yytext(), "OP_RELACIONAL", yyline, yycolumn); }*/

/* Operadores aritméticos */
"+" { return token(yytext(), "OP_SUMA", yyline, yycolumn); }
"-" { return token(yytext(), "OP_RESTA", yyline, yycolumn); }
"*" { return token(yytext(), "OP_MULTIPLICACION", yyline, yycolumn); }
"/" { return token(yytext(), "OP_DIVISION", yyline, yycolumn); }

/* Marcador de inicio*/
"main" { return token(yytext(), "MAIN_PRINCIPAL", yyline, yycolumn); }

/* Palabra reservada Function para la declaracion de funciones*/
"function" { return token(yytext(), "FUNCTION", yyline, yycolumn); }

/* if */
"if" { return token(yytext(), "ESTRUCTURA_IF", yyline, yycolumn); }
"else" { return token(yytext(), "ESTRUCTURA_ELSE", yyline, yycolumn); }

/* while */
"while" |
"doWhile" { return token(yytext(), "ESTRUCTURA_WHILE", yyline, yycolumn); }

/* break */
"break" { return token(yytext(), "BREAK_WHILE", yyline, yycolumn); }

/* FINAL */
"exit" { return token(yytext(), "EXIT", yyline, yycolumn); }

/* Errores */
// Número erróneo que inicia con 0 seguido de otro numero
0 {Numero}+ { return token(yytext(), "ERROR_1", yyline, yycolumn); }
// Declarar un identificador sin $ y con espacios
{Identificador} { return token(yytext(), "ERROR_2", yyline, yycolumn); }
// Cualquier caracter desconocido
. { return token(yytext(), "ERROR", yyline, yycolumn); }