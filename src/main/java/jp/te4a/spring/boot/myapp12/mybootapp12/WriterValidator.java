package jp.te4a.spring.boot.myapp12.mybootapp12;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WriterValidator implements ConstraintValidator<WriterValid,String> {
    String ok;
    @Override
    public void initialize(WriterValid nv){ok = nv.ok();}
    @Override
    public boolean isValid(String in,ConstraintValidatorContext cxt){
        if (in == null) return true;
 
        if (in.equals(ok)) {
            return true;
        } else {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Input " + in)
                    .addConstraintViolation();
            return false;
        }
    }
    
}