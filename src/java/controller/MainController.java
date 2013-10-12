package controller;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.lang.annotation.Annotation;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import model.JongoCollection;
import org.bson.types.ObjectId;
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
    
    private String getDocumentName( Class type ) {
        Annotation annotation = type.getAnnotation( JongoCollection.class );
        JongoCollection jongoCollection = ( JongoCollection ) annotation;
        
        return jongoCollection.value();
    }
    
    public List<Object> getDocuments( Class type ) throws UnknownHostException {
        List<Object> objects = new ArrayList<>();
        Jongo connection = getConnection();
        String documentName = getDocumentName( type );
        
        MongoCollection listRetrieved = connection.getCollection( documentName );
        Iterable<Object> all = listRetrieved.find().as( type );
        
        for( Object obj : all ) {
            objects.add( obj );
        }
        
        return objects;
    }
    
    public Object getDocument( Class type, String objectId ) throws UnknownHostException {
        Jongo connection = getConnection();
        String documentName = getDocumentName( type );
        
        MongoCollection collection = connection.getCollection( documentName );
        
        return collection.findOne( new ObjectId( objectId ) ).as( type );
    }
    
    public void saveDocument( Class type, Object obj ) throws UnknownHostException {
        Jongo connection = getConnection();
        String documentName = getDocumentName( type );
        
        MongoCollection collection = connection.getCollection( documentName );
        
        collection.save( obj );
    }
    
    public void insertDocument( Class type, Object obj ) throws UnknownHostException {
        Jongo connection = getConnection();
        String documentName = getDocumentName( type );
        
        MongoCollection collection = connection.getCollection( documentName );
        
        collection.insert( obj );
    }
    
    public void removeDocument( Class type, Object obj ) throws UnknownHostException {
        Jongo connection = getConnection();
        String documentName = getDocumentName( type );
        
        MongoCollection collection = connection.getCollection( documentName );
        
        collection.remove( "" );
    }
}
