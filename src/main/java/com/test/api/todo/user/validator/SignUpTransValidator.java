package com.test.api.todo.user.validator;

import com.test.api.todo.boot.exception.RestException;
import com.test.api.todo.user.dto.request.SignUpRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SignUpTransValidator implements Validator {
    @Override
    public boolean supports(Class<?> Clazz) {
        return SignUpRequestDto.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String crnKey = "137137135";
        String crn = (String) target;

        // 사업자 등록 번호 형식에 맞는 지 확인하기 위한 정규식
        String regex = "^\\d{3}-\\d{2}-\\d{5}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(crn);

        if(!m.matches()) {
            throw new RestException(HttpStatus.BAD_REQUEST, "사업자 등록번호 형식에 맞춰 입력해주세요.");
        }

        String newCrn = crn.replace("-", "");
        int result = 0;
        int divideValue = (((int) newCrn.charAt(8)) * 5) / 10;

        for ( int i = 0; i < 9; i ++) {
            result = ((int) newCrn.charAt(i)) * ((int) crnKey.charAt(i));
        }

        result = result + divideValue;
        result = result % 10;

        if (10 - result != 1) {
            throw new RestException(HttpStatus.BAD_REQUEST, "잘못된 사업자 등록번호입니다.");
        }

    }

    public void validate(Object target){
        validate(target,null);
    }
}
