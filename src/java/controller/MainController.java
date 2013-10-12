package controller;

import com.mongodb.DB;
import com.mongodb.MongoClient;
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
        
        MongoClient client = new MongoClient( "localhost" );
        
        if( db == null ) {
            db = client.getDB( DATABASE_NAME );
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
    
    public void saveDocument( Class type, Object obj ) throws UnknownHostException {
        Jongo connection = getConnection();
        
        MongoCollection collection = connection.getCollection( "" );
        
        collection.save( obj );
    }
}
