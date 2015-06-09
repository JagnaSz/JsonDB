package kompilatory;

import kompilatory.exception.SyntaxError;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by agnieszkaszczurek on 29.05.15.
 */

public class DropHandler extends CommandHandler {
    @Override
    public String process(String line) throws SyntaxError, SQLException {

        Scanner in = new Scanner(line);
        String table;
        String input;
        String result;

        try {
            input = in.next();
            if(!input.toLowerCase().equals("drop"))
                throw new SyntaxError(input);

            input = in.next();
            if(!input.toLowerCase().equals("table"))
                throw new SyntaxError(input);

            table = in.next();
            if(table.contains(";"))
                table = table.substring(0,table.length()-1);

            Boolean isDropped = schema.drop(table);
            if(isDropped)
                result = "Dropped table " + table;
            else
                result = "Error when dropping table " + table;

        }
        catch (SyntaxError e) {
            throw e;
        }
        catch (SQLException e) {
                   throw e;
        }
        finally {
            in.close();
        }
        return result;
    }
}
