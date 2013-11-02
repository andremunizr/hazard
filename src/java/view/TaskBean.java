
package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;  
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import model.Comment;
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
    private Notification taskNotification;
    private List<Task> tasks;
    private User responsable;
    private String responsableId;
    private Comment comment;
    
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    
    public Task findOne(String id) throws UnknownHostException{
        return (Task) controller.findOne(Task.class, id);
    }
    
    @PostConstruct
    public void initializer() throws UnknownHostException {
        setTasks( ( List<Task> ) ( List<?> ) controller.getDocuments( Task.class ) );
        setTask( new Task() );
        setComment( new Comment() );
    }
    
    public void save() throws UnknownHostException {
     
        taskNotification = new Notification();
        responsable = findResponsable( responsableId );
        
        buildTask("save");                                                                                 
        controller.saveDocument( Task.class, task );
                
        buildNotification();
        
        buildResponsable();        
        controller.saveDocument( User.class, responsable );
        
        notBean.setNotification( taskNotification );
        notBean.save();
                
        setTask( new Task() );
    }    
    
    public void edit(String id) throws UnknownHostException{
        
        Task uTask = (Task) controller.findOne( Task.class, id );
        
        System.out.println("Id da task do find: " + uTask.getId());
        
        uTask.setStatus( task.getStatus() );        
        
        if( !("").equals( comment.getText() ))
            uTask.getComments().add( buildComment( comment.getText() ) );
        
        for( Comment c : uTask.getComments()){
            System.out.println("Comment do uTask: " + c.getText());
        }
        
        User user = logBean.getSessionUser();
        
        for(Task t : user.getTasks()){
                       
            if( t.getId().equals( uTask.getId() ) ){
                
                t.setStatus( task.getStatus() );
                t.getComments().add( buildComment( comment.getText() ) );
                break;
            }
        }
        System.out.println("Até aqui tudo bem...");        
        controller.saveDocument( Task.class, uTask );     
        
        try{
            controller.saveDocument( User.class, user );
        }catch( EJBException e ){
            System.out.println("Erro ao persistir objeto.");
            System.out.println(e.getMessage());
        }
    }
    
    public User findResponsable(String id) throws UnknownHostException{
        return (User) controller.findOne( User.class, id );
    }
    
    public String buildLink(String base, String id){
        return base + "?faces-redirect=true&id=" + id;
    }    
    
    private void buildTask(String type){
        task.setAuthor( logBean.getSessionUser().getName() );
        task.setAuthorId( logBean.getSessionUser().getId().toString() );
        
        if( !type.equals("save") )
            if( comment != null )
               if( !comment.getText().equals("") )
                    task.getComments().add( buildComment( comment.getText() ) );
    }
    
    private void buildNotification(){
        
        taskNotification.setText( Messenger.NOVA_TAREFA.getMsg() );
        String link = buildLink(Messenger.NOVA_TAREFA.getLink(), task.getId());
        taskNotification.setLink( link );        
    }
    
    private Comment buildComment(String text){
        
        Comment cmt = new Comment();        
        Calendar cal = Calendar.getInstance();
        
        cmt.setAuthor( logBean.getSessionUser().getName() );
        cmt.setDate( cal.getTime() );
        cmt.setText( text );
        
        System.out.println("Texto do comment: " + text);
        
        return cmt;
    }
    
    private void buildResponsable(){
        
        responsable.getNotifications().add( taskNotification );
        responsable.getTasks().add( task );        
    }
    
}