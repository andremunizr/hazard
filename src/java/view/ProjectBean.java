package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Notification;
import model.Project;
import model.Task;
import model.User;
import util.Messenger;

@Named(value = "projectBean")
@RequestScoped
public class ProjectBean {
    
    @EJB
    private MainController controller;
    private Project project;
    private List<Project> availableProjects;
    private List<Task> selectedTasks;
    private List<Task> allTasks;
    
    @Inject
    private LoggBean logBean;
    
    @Inject
    private NotificationBean notificationBean;
    
    @PostConstruct
    public void initializer() throws UnknownHostException {
        setProject( new Project() );
        setAvailableProjects( ( List<Project> ) ( List<?> ) controller.getDocuments( Project.class ) );
        
    }
    
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getAvailableProjects() throws UnknownHostException {
        
        if( availableProjects == null ) {
            setAvailableProjects( ( List<Project> ) ( List<?> ) controller.getDocuments( Project.class ) );
        }
        
        return availableProjects;
    }

    public void setAvailableProjects(List<Project> availableProjects) {
        this.availableProjects = availableProjects;
    }

    public List<Task> getSelectedTasks() {
        return selectedTasks;
    }

    public void setSelectedTasks(List<Task> selectedTasks) {
        this.selectedTasks = selectedTasks;
    }

    public List<Task> getAllTasks() {
        return allTasks;
    }

    public void setAllTasks(List<Task> allTasks) {
        this.allTasks = allTasks;
    }
    
    public void save() throws UnknownHostException {
        
        User currentUser = logBean.getSessionUser();
        Notification projectNotification = new Notification();
        
        controller.saveDocument( Project.class, project );
        
        projectNotification.setText( Messenger.NOVO_PROJETO.getMsg() );
        String link = Messenger.NOVO_PROJETO.getLink() + "?faces-redirect=true&id=" + project.getId();
        projectNotification.setLink( link );
        
        notificationBean.setNotification( projectNotification );
        notificationBean.save();
        
        currentUser.getNotifications().add( projectNotification );
        
        currentUser.getProjects().add( project );
        controller.saveDocument( User.class, currentUser );
        
        setProject( new Project() );
    }
    
}
