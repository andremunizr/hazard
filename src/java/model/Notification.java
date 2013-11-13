
package model;

import javax.xml.bind.annotation.XmlRootElement;
import org.jongo.marshall.jackson.oid.ObjectId;

@XmlRootElement
@JongoCollection( "notification" )
public class Notification{
    
    @ObjectId
    private String _id;
    private String text;
    private String link;
    private String picture;
    private boolean read = false;
    
    public Notification(){}

    public String getId() {
        return _id;
    }

    public String getIdString(){
        return _id.toString();
    }
    
    public void setId(String _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (_id != null ? _id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this._id == null && other._id != null) || (this._id != null && !this._id.equals(other._id))) {
            return false;
        }
        return true;
    }
}