package kompilatory;


import kompilatory.exception.SyntaxError;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by agnieszkaszczurek on 26.05.15.
 */
public class SelectHandler  extends CommandHandler {


    @Override
    public String process(String line) throws SyntaxError, SQLException {
        Scanner in = new Scanner(line);
        String input;
        String table;
        String result = "result";
        StringBuilder items = new StringBuilder();

        try {

            input = in.next();
            if(!input.toLowerCase().equals("select"))
                throw new SyntaxError(input);

            while (in.hasNext()){
                input =  in.next();
                if(!"from".equals(input.toLowerCase()))
                    items.append(input);
                else
                    break;
            }

            String [] columns = items.toString().split(",");
            //input = in.next();
            if(!input.toLowerCase().equals("from"))
                throw new SyntaxError(input);

            table = in.next();
            if(table.contains(";"))
                table = table.substring(0,table.length()-1);


            result = schema.select(columns, table);
            return result;

        } catch (SyntaxError e) {
            throw e;
        }
//        } catch (SQLException e) {
//            throw e;}
        finally {
            in.close();
        }

    }

}
