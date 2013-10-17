
package view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import model.User;

@Named(value = "loggBean")
@SessionScoped
public class LoggBean implements Serializable {

    private User sessionUser;
    
    public LoggBean(){}
}
