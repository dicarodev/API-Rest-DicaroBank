package com.dicaro.dicarobank.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

/*
 * ApiError model to return an error to the client with the status code and message.
 */
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {

    @NonNull
    private HttpStatusCode status;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime errorDate = LocalDateTime.now();

    @NonNull
    private String message;
}
