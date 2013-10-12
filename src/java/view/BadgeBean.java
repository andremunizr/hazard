package view;

import controller.MainController;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import model.Badge;

@Named
@ViewScoped
public class BadgeBean implements Serializable {
    private List<Badge> badges;
    private Badge badge;
    
    @EJB
    private MainController controller;
    
    public List<Badge> getBadges() throws UnknownHostException {
        
        if( badges == null ) {
            setBadges( ( List<Badge> ) ( List<?> ) controller.getDocuments( Badge.class ) );
        }
        
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }
    
    @PostConstruct
    public void initializer() {
        try {
            setBadges( ( List<Badge> ) ( List<?> ) controller.getDocuments( Badge.class ) );
            setBadge( new Badge() );
        }
        catch( UnknownHostException ex ) {
            System.out.println( ex.getMessage() );
        }
    }
    
    public void save() throws UnknownHostException {
        controller.saveDocument( Badge.class, badge );
    }
}
