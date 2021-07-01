package com.team6.CAPSProj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="There is no record")
public class GpaRecordNotFoundException extends RuntimeException {

}
