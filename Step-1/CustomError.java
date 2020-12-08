import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomError extends Exception{
    
    private String message;
    private long errorTime;

    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    public CustomError(String message){
        errorTime = System.currentTimeMillis();
        message += " 오류 시간 : " + timeFormat.format(new Date(errorTime));
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
