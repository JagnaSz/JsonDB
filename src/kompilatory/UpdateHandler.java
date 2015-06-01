package kompilatory;

import kompilatory.exception.SyntaxError;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by agnieszkaszczurek on 01.06.15.
 */
public class UpdateHandler extends CommandHandler {
    @Override
    public String process(String line) throws SyntaxError, SQLException {

        Scanner in = new Scanner(line);
        String table;
        String input;
        String result;
        StringBuilder items = new StringBuilder();
        StringBuilder whereItems = new StringBuilder();

        try {
            input = in.next();
            if(!input.toLowerCase().equals("update"))
                throw new SyntaxError(input);


            table = in.next();

            input = in.next();
            if(!input.toLowerCase().equals("set"))
                throw new SyntaxError(input);

            while (in.hasNext()){
                input =  in.next();
                if(!"where".equals(input.toLowerCase()))
                    items.append(input);
                else
                    break;
            }

            if(!input.toLowerCase().equals("where"))
                throw new SyntaxError(input);

            List<Map<String, String>> lista = new ArrayList<Map<String,String>>();


            String [] itemsToUpdate = items.toString().split(",");

            for(String itemToUpdate : itemsToUpdate){
                String [] values = itemToUpdate.split("=");
                Map<String,String> map = new HashMap<String,String>();
                map.put(values[0],values[1]);

                lista.add(map);

            }

            while (in.hasNext()){
                input =  in.next();
                if(!";".equals(input.toLowerCase()))
                    whereItems.append(input);
                else
                    break;
            }

           String res = whereItems.toString().substring(0,whereItems.toString().length()-1);
            String [] whereSubstring = res.split("=");

            Map<String,String> whereItem= new HashMap<String,String>();
            whereItem.put(whereSubstring[0], whereSubstring[1]);



            Boolean isUpdated = schema.update(table,lista,whereItem);

            if(isUpdated)
                result = "Updating was successful";
            else
                result = "Updating wasn't successful";



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
