import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class Grammar {
    int i = 0;
    ArrayList<Token> tokens;
    Token tokeno;
    String error = "";

    public Grammar(ArrayList<Token> token) {
        tokens = token;
        tokeno = tokens.get(i);
    }

    ///////////////////// 2 Identifier_list//////////////////////////////

    public void Identifier_list() {
        match(tokeno.Name, "Identifier");
        Identifier_list2();
    }

    public void Identifier_list2() {
        if (tokeno.Name.equals(",")) {
            match(",", "KeyWord");
            match(tokeno.Name, "Identifier");
            Identifier_list2();
        } else {
            return;
        }
    }

    ////////////////////////// 3 declarations////////////////////////////////////
    public void declarations() {
        if (tokeno.Name.equals("var")) {
            declarations2();
        }
    }

    public void declarations2() {
        match("var", "KeyWord");
        Identifier_list();
        match(":", "KeyWord");
        type();
        match(";", "KeyWord");
        System.out.println(tokeno.Name);
        if (tokeno.Name.equals("var")) {
            declarations2();
        } else {
            return;
        }
    }

    ////////////////////////// 4 type////////////////////////////////////////////
    public void type() {
        if (tokeno.Name.equals("integer") | tokeno.Name.equals("boolean")) {
            Standard_type();
        } else if (tokeno.Name.equals("array")) {
            match("array", "KeyWord");
            match("[", "KeyWord");
            match(tokeno.Name, "Number");
            match(".", "KeyWord");
            match(".", "KeyWord");
            match(tokeno.Name, "Number");
            match("]", "KeyWord");
            match("of", "KeyWord");
            Standard_type();
        }
    }

    ///////////////////////// 5 Standard-type////////////////////////////////////
    public void Standard_type() {
        if (tokeno.Name.equals("integer")) {
            match("integer", "KeyWord");
        } else if (tokeno.Name.equals("boolean")) {
            match("boolean", "KeyWord");
        }
    }

    ///////////////////////// 6 Subproram_Declarations//////////////////////////
    public void Subproram_Declarations() {
        if (tokeno.Name.equals("function") | tokeno.Name.equals("procedure")) {
            Subproram_Declarations2();
        }
    }

    public void Subproram_Declarations2() {
        Subproram_Declaration();
        match(";", "KeyWord");
        if (tokeno.Name.equals("function") | tokeno.Name.equals("procedure")) {
            Subproram_Declarations2();
        } else {
            return;
        }
    }

    //////////////////////// 7 Subproram_Declaration///////////////////////////
    public void Subproram_Declaration() {
        Subproram_Head();
        declarations();
        Compound_statement();

    }

    /////////////////////// 8 Subproram_Head///////////////////////////////////
    public void Subproram_Head() {
        if (tokeno.Name.equals("function")) {
            System.out.println(1 + tokeno.Name);
            match("function", "KeyWord");
            match(tokeno.Name, "Identifier");
            Argument();
            match(":", "KeyWord");
            Standard_type();
            match(";", "KeyWord");
        } else if (tokeno.Name.equals("procedure")) {
            System.out.println(2 + tokeno.Name);
            match("procedure", "KeyWord");
            match(tokeno.Name, "Identifier");
            Argument();
            match(";", "KeyWord");
        }
    }

    ////////////////////////// 9 Arguments////////////////////////////////////////
    public void Argument() {
        match("(", "KeyWord");
        parameter_list();
        match(")", "KeyWord");
    }

    ///////////////////////// 10 parameter_list///////////////////////////////////
    public void parameter_list() {
        Identifier_list();
        match(":", "KeyWord");
        type();
        parameter_list2();
    }

    public void parameter_list2() {
        if (tokeno.Name.equals(";")) {
            match(";", "KeyWord");
            Identifier_list();
            match(":", "KeyWord");
            type();
            parameter_list2();
        } else {
            return;
        }
    }

    //////////////////////////// 11 Compound_statement///////////////////////////
    public void Compound_statement() {
        match("begin", "KeyWord");
        Optional_Statements();
        match("end", "KeyWord");
    }

    ////////////////////////// 12 Optional_Statements////////////////////////////
    public void Optional_Statements() {
        if (!tokeno.Name.equals("end")) {
            Statement_List();
        }
    }

    ///////////////////////// 13 Statement_List/////////////////////////////////
    public void Statement_List() {
        statement();
        match(";", "KeyWord");
        Statement_List2();
    }

    public void Statement_List2() {
        statement();
        match(";", "KeyWord");
        if (tokeno.Name.equals(";")) {
            match(";", "KeyWord");
            Statement_List2();
        }
    }

    ////////////////////////// 14 Statement/////////////////////////////////////
    public void statement() {
        if (tokeno.Name.equals("begin")) {
            Compound_statement();
        } else if (tokeno.Name.equals("for")) {
            match("for", "KeyWord");
            expression();
            statement();
        } else if (tokeno.Name.equals("if")) {
            match("if", "KeyWord");
            expression();
            match("then", "KeyWord");
            statement();
            match("else", "KeyWord");
            statement();
        } else {
            Vp();

        }
    }

    ////////////////////////// 15 Variable//////////////////////////////////////
    public void Vp() {
        match(tokeno.Name, "Identifier");
        Vp2();
    }

    public void Vp2() {
        if (tokeno.Name.equals("[")) {
            match("[", "KeyWord");
            expression();
            match("]", "KeyWord");
            match(":=", "KeyWord");
            expression();
        } else if (tokeno.Name.equals("(")) {
            match("(", "KeyWord");
            Expression_list();
            match(")", "KeyWord");
        } else {
            match(":=", "KeyWord");
            expression();
        }
    }

    ////////////////////////// 16 procedure_statement//////////////////////////
    public void procedure_statement() {
        match(tokeno.Name, "Identifier");
        procedure_statement2();
    }

    public void procedure_statement2() {
        if (tokeno.Name.equals("(")) {
            match("(", "KeyWord");
            Expression_list();
            match(")", "KeyWord");
        }
    }

    ///////////////////////// 17 Expression_list////////////////////////////////
    public void Expression_list() {
        expression();
        Expression_list2();
    }

    public void Expression_list2() {
        if (tokeno.Name.equals(",")) {
            match(",", "KeyWord");
            expression();
            Expression_list2();
        }
    }

    ////////////////////////// 18 Expression///////////////////////////////////
    public void expression() {
        Sample_Expression();
        expression2();
    }

    public void expression2() {
        if (tokeno.Name.equals("=")) {
            relop();
            Sample_Expression();
        } else if (tokeno.Name.equals("<>")) {
            relop();
            Sample_Expression();
        } else if (tokeno.Name.equals("<")) {
            relop();
            Sample_Expression();
        } else if (tokeno.Name.equals(">")) {
            relop();
            Sample_Expression();
        } else if (tokeno.Name.equals(">=")) {
            relop();
            Sample_Expression();
        } else if (tokeno.Name.equals("=<")) {
            relop();
            Sample_Expression();
        }
    }

    ///////////////////////// 19 Sample_Expression//////////////////////////////
    public void Sample_Expression() {
        if (!tokeno.Name.equals("+") & !tokeno.Name.equals("-")) {
            term();
            Sample_Expression2();
        } else {
            Sing();
            term();
            Sample_Expression2();
        }
    }

    public void Sample_Expression2() {
        if (tokeno.Name.equals("+") | tokeno.Name.equals("-")) {
            Sing();
            term();
            Sample_Expression2();
        }
    }

    ///////////////////////// 20 term/////////////////////////////////////////
    public void term() {
        factor();
        term2();
    }

    public void term2() {
        if (tokeno.Name.equals("*") | tokeno.Name.equals("/")) {
            mulop();
            factor();
            term2();
        }
    }

    ///////////////////////// 21 factor////////////////////////////////////////
    public void factor() {
        if (tokeno.Type.equals("Identifier")) {
            match(tokeno.Name, "Identifier");
            factor2();
        } else if (tokeno.Type.equals("Number")) {
            match(tokeno.Name, "Number");
        } else if (tokeno.Name.equals("(")) {
            match("(", "KeyWord");
            expression();
            match(")", "KeyWord");
        } else if (tokeno.Name.equals("not")) {
            match("not", "KeyWord");
            factor();
        }
    }

    public void factor2() {
        if (tokeno.Name.equals("(")) {
            match("(", "KeyWord");
            expression();
            match(")", "KeyWord");
        }
    }

    ////////////////////////// 22Sing////////////////////////////////////////////
    public void Sing() {
        if (tokeno.Name.equals("+")) {
            match("+", "KeyWord");
        } else {
            match("-", "KeyWord");
        }
    }

    ////////////////// 23 relop//////////////////////////////////////////////
    public void relop() {
        if (tokeno.Name.equals("=")) {
            match("=", "KeyWord");
        } else if (tokeno.Name.equals("<>")) {
            match("<>", "KeyWord");
        } else if (tokeno.Name.equals(">")) {
            match(">", "KeyWord");
        } else if (tokeno.Name.equals("<")) {
            match("<", "KeyWord");
        } else if (tokeno.Name.equals(">=")) {
            match(">=", "KeyWord");
        } else if (tokeno.Name.equals("=<")) {
            match("=<", "KeyWord");
        }
    }

    //////////////////////////// 24 mulop//////////////////////////////////
    public void mulop() {
        if (tokeno.Name.equals("*")) {
            match("*", "KeyWord");
        } else {
            match("/", "KeyWord");
        }
    }

    ///////////////////////////////////// Match///////////////////////////
    public void match(String n, String t) {
        if ((t.equals("KeyWord") & t.equals(tokeno.Type)) & n.equals(tokeno.Name)) {
            NextToken();
        } else if (t.equals(tokeno.Type) & !t.equals("KeyWord")) {
            NextToken();
        } else {
            error(n, t);
            NextToken();
        }

    }

    ////////////////////////////////// Error////////////////////////////////////////
    public void error(String n, String t) {
        if (t.equals("KeyWord")) {
            System.out.println("It is expected to have a keyword  " + n + " in line  " + tokeno.LineNo);
        } else if (t.equals("Identifier")) {
            System.out.println("It is expected to have an identifier  " + n + " in line " + tokeno.LineNo);
        } else {
            System.out.println("It is expected to have a numeric constant " + n + " in line " + tokeno.LineNo);
        }
    }

    ////////////////////////////// NextToken//////////////////////////////////////
    public void NextToken() {
        i++;
        if (i < tokens.size()) {

            tokeno = tokens.get(i);
        }
    }

    public void Program() {
        match("program", "KeyWord");
        match("program", "KeyWord");
        match(tokeno.Name, "Identifier");
        match(";", "KeyWord");
        declarations();
        Subproram_Declarations();
        Compound_statement();
        match(".", "KeyWord");
    }
}