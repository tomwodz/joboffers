package pl.tomwodz.joboffers.infrastructure.offer.controller.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.tomwodz.joboffers.domain.offer.OfferNotFoundException;
import pl.tomwodz.joboffers.infrastructure.offer.controller.OfferRestController;

import java.util.Collections;

@ControllerAdvice(assignableTypes = OfferRestController.class)
@Log4j2
public class ResultAnnouncerControllerErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorOfferResponseDto offerNotFound(OfferNotFoundException exception){
        log.error(exception.getMessage());
        return new ErrorOfferResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorPostOfferResponseDto offerDuplication(DuplicateKeyException exception){
        String message = String.format("Offer url already exists");
        log.error(message);
        return new ErrorPostOfferResponseDto(Collections.singletonList(message), HttpStatus.CONFLICT);
    }

}
