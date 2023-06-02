package Analizador;
import compilerTools.TextColor;
import java.awt.Color;

%%
%class LexerColor
%type TextColor
%char
%{/*Retorna un objeto de tipo textColor*/
    private TextColor textColor(long inicio, int tamano, Color color){
        return new TextColor((int) inicio, tamano, color);
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
Cadena = [Letra}+" "{Letra}|{Digito})]*
%%

/* Comentarios o espacios en blanco, color gris rgb */
{Comentario} { return textColor(yychar, yylength(), new Color(146, 146, 146)); }
{EspacioEnBlanco} { /*Ignorar*/ }

/* Identificador */
\${Identificador} { /*Ignorar*/ }

/* Tipos de dato color morado*/
int | double | String { return textColor(yychar, yylength(), new Color(148, 58, 173)); }

/* Número */
{Numero} { return textColor(yychar, yylength(), new Color(35, 120, 147)); }
/* Número  Decimal */
{Decimal} { return textColor(yychar, yylength(), new Color(35, 120, 147)); }
/* String */
\"{Cadena}\" { return textColor(yychar, yylength(), new Color(35, 120, 147)); }

/* Operadores de agrupación */
"("|")"|"{"|"}" { return textColor(yychar, yylength(), new Color(169, 155, 179)); }

/* Signos de puntuación */
","|
";" { return textColor(yychar, yylength(), new Color(169, 155, 179)); }
"." { return textColor(yychar, yylength(), new Color(169, 155, 179)); }
"\"" { return textColor(yychar, yylength(), new Color(169, 155, 179)); }

/* Operador de asignación */
= { return textColor(yychar, yylength(), new Color(169, 155, 179)); }

/* Operadores logicos y relacionales */
"&&" |
"||" |
">" |
"<" |
"==" |
"!=" |
">=" |
"<=" { return textColor(yychar, yylength(), new Color(48, 63, 159));}

/* Operadores relacionales 
">" |
"<" |
"==" |
"!=" |
">=" |
"<=" { return textColor(yychar, yylength(), new Color(48, 65, 159));}*/

/* Operadores aritméticos */
"+" |
"-" |
"*" |
"/" { return textColor(yychar, yylength(), new Color(48, 63, 159)); }

"main" { return textColor(yychar, yylength(), new Color(148, 58, 173)); }

/* Palabra reservada Function para la declaracion de funciones*/
"function" { return textColor(yychar, yylength(), new Color(148, 58, 173)); }

/* if */
"if" |
"else" { return textColor(yychar, yylength(), new Color(121, 107, 255));}

/* while */
"while" |
"doWhile" { return textColor(yychar, yylength(), new Color(121, 107, 255)); }

/* break */
"break" { return textColor(yychar, yylength(), new Color(121, 107, 255)); }

/* FINAL */
"exit" { return textColor(yychar, yylength(), new Color(198, 40, 40)); }

/* Errores */
// Número erróneo
0 {Numero}+ { /* Ignorar */ }
// Identificador sin $
{Identificador} { /* Ignorar */ }
// Caracter desconocido
. { /* Ignorar */ }