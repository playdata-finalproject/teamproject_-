package com.finalproject.shelter.business.validator.answer;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Answer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;


@Component
public class AnswerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Answer.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Answer b = (Answer) obj;
        if(StringUtils.isEmpty(b.getAnswerText())) {
            errors.rejectValue("AnswerText", "key", "내용을 입력하세요");
        }
    }
}
