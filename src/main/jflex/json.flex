package kompilatory;

import java_cup.runtime.Symbol;
import kompilatory.parser.*;
%%

%class JSONScanner
%unicode
%char
%cup
%line
%column

%{
 /**
     * Default constructor is needed as we will always call the yyreset
     */
    public JSONScanner() {
        super();
    }

    StringBuffer string = new StringBuffer();

    private Symbol symbol(int type) {
    	return new Symbol(type, yyline+1, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn, value);
    }

%}

INT = [-]?[0-9]+
DOUBLE = {INT}((\.[0-9]+)?([eE][-+]?[0-9]+)?)
WS = [ \t\r\n]
UNESCAPED_CH = [^\"\\]
FALLBACK_CH = .
eol = [\r|\n|\r\n]
otherSigns  =   (\"|\#|\@|\$|\^|\_|\&|\+|\=|\||\<|\>|\*)
word =(\"( [a-zA-Z0-9]| "(" | ")" | {otherSigns} | \. | \s)* \")

%%
<YYINITIAL>{
	"{" { return symbol(sym.LEFT_BRACKET); }
	"}" { return symbol(sym.RIGHT_BRACKET); }
	"(" { return symbol(sym.LEFT_PARENTHESIS); }
	")" { return symbol(sym.RIGHT_PARENTHESIS); }
	"[" { return symbol(sym.LEFT_SQ_BRACKET); }
	"]" { return symbol(sym.RIGHT_SQ_BRACKET); }
	":" { return symbol(sym.COLON); }
	"," { return symbol(sym.COMA); }
	"true"|"false" { return symbol(sym.BOOLEAN, Boolean.valueOf(yytext())); } 
	"null" {return symbol(sym.NULL); }
	
	{INT} { return symbol(sym.INTEGER, Integer.valueOf(yytext())); }
	{DOUBLE} { return symbol(sym.DOUBLE, Double.valueOf(yytext())); }
	{word} {return symbol(sym.WORD, yytext()); }
	{WS}+ {}
	
}

/* error fallback */
[^]                             { throw new Error("line:" + yyline + " column:" + yycolumn + "Illegal character <"+
                                   yytext() +">"); }
<<EOF>>                          { return null; }

