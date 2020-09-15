package net.ukr.dreamsicle.usermanagementtracker.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Lombok;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private  String errorMessage;

    private  String requestedURI;
}
