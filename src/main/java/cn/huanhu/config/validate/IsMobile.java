package cn.huanhu.config.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author m
 * @className isMobile
 * @description isMobile
 * @date 2020/5/15
 */
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {

    boolean required() default true;

    String message() default "手机号码格式有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
