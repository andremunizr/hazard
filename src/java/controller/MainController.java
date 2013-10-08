package controller;

import com.mongodb.DB;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

@Stateless
public class MainController {
    private final String DATABASE_NAME = "Hazard";
    private DB db;
    private Jongo jongo;
    
    private Jongo getConnection() throws UnknownHostException {
        if( db == null ) {
            db = new Mongo().getDB( DATABASE_NAME );
        }
        
        if( jongo == null ) {
            jongo = new Jongo( db );
        }
        
        return jongo;
    }
    
    public List<Object> getDocuments( Class type, String documentName ) throws UnknownHostException {
        List<Object> objects = new ArrayList<>();
        Jongo connection = getConnection();
        
        MongoCollection listRetrieved = connection.getCollection( documentName );
        Iterable<Object> all = listRetrieved.find().as( type );
        
        for( Object obj : all ) {
            objects.add( obj );
        }
        
        return objects;
    }
}
