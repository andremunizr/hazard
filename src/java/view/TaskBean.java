
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
    
    @PostConstruct
    public void initializer() throws UnknownHostException {
        setTasks( ( List<Task> ) ( List<?> ) controller.getDocuments( Task.class ) );
        setTask( new Task() );
    }
    
    public void save() throws UnknownHostException {
        
        Notification taskNotification = new Notification();
        User responsable = findResponsable( responsableId );
        
        taskNotification.setReceiver( responsable );
        taskNotification.setText( Messenger.NOVA_TAREFA.getMsg() );
        
        notBean.setNotification( taskNotification );
        notBean.save();
        
        controller.saveDocument( Task.class, task );
        
        setTask( new Task() );
    }    
    
    public User findResponsable(String id) throws UnknownHostException{
        return (User) controller.findOne( User.class, id );
    }
    
}