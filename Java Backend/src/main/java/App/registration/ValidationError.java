package App.registration;

/**
 * Created by micha on 12/13/2016.
 */
public class ValidationError {

    private String fieldName;
    private String errorMsg;

    public ValidationError(String fieldName, String msg) {
        this.fieldName = fieldName;
        this.errorMsg = msg;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
