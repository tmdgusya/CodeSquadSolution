public class CustomError extends Exception{
    
    private String message;
    
    public CustomError(String message){
        super(message);
        this.message = message;
    }
}
