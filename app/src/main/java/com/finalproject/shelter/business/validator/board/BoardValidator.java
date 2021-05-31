package com.finalproject.shelter.business.validator.board;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if(StringUtils.isEmpty(b.getContents())) {
            errors.rejectValue("contents", "key", "내용을 입력하세요");
        }
        if(StringUtils.isEmpty(b.getTitle())) {
            errors.rejectValue("title", "key", "제목을 입력하세요");
        }
    }
}
