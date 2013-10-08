package view;

import controller.MainController;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import model.Badge;

@ManagedBean
@ViewScoped
public class BadgeBean implements Serializable {
    private List<Badge> badges;
    
    @EJB
    private MainController controller;
    
    public List<Badge> getBadges() throws UnknownHostException {
        
        if( badges == null ) {
            setBadges( ( List<Badge> ) ( List<?> ) controller.getDocuments( Badge.class, "badge" ) );
        }
        
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }
    
}
