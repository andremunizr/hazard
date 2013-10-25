
package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import model.Notification;
import model.Task;
import model.User;
import util.Messenger;

@Named(value = "taskBean")
@RequestScoped
public class TaskBean {
    
    @EJB
    private MainController controller;
    @Inject
    private NotificationBean notBean;
    @Inject
    private LoggBean logBean;
    private Task task;
    private List<Task> tasks;
    private String responsableId;
    
    public TaskBean(){}
    
    public List<Task> getTasks() throws UnknownHostException {
        
        if( tasks == null ) {
            setTasks( ( List<Task> ) ( List<?> ) controller.getDocuments( Task.class ) );
        }
        
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getTask() throws UnknownHostException {
        setTasks( ( List<Task> ) ( List<?> ) controller.getDocuments( Task.class ) );
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getResponsableId(){
        return responsableId;
    }

    public void setResponsableId(String responsableId){
        this.responsableId = responsableId;
    }

    public Task findOne(String id) throws UnknownHostException{
        return (Task) controller.findOne(Task.class, id);
    }
    
    @PostConstruct
    public void initializer() throws UnknownHostException {
        setTasks( ( List<Task> ) ( List<?> ) controller.getDocuments( Task.class ) );
        setTask( new Task() );
    }
    
    public void save() throws UnknownHostException {
        
        //Implementação básica - melhorar implementação 
        
        Notification taskNotification = new Notification();
        User responsable = findResponsable( responsableId );
        
        task.setAuthor( logBean.getSessionUser().getName() );
                                                                                 
        controller.saveDocument( Task.class, task );
        
        taskNotification.setText( Messenger.NOVA_TAREFA.getMsg() );
        String link = Messenger.NOVA_TAREFA.getLink() + "?faces-redirect=true&id=" + task.getId();
        taskNotification.setLink( link );
        
        responsable.getNotifications().add( taskNotification );
        responsable.getTasks().add( task );
        
        controller.saveDocument( User.class, responsable );
        
        notBean.setNotification( taskNotification );
        notBean.save();
                
        setTask( new Task() );
    }    
    
    public User findResponsable(String id) throws UnknownHostException{
        return (User) controller.findOne( User.class, id );
    }
    
}