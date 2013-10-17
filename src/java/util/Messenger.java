
package util;

public enum Messenger {
    
    NOVA_TAREFA("Você recebeu uma nova tarefa");
    
    private String msg;
    
    Messenger(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}