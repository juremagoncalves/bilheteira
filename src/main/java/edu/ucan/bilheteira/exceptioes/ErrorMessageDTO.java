package edu.ucan.bilheteira.exceptioes;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class ErrorMessageDTO {
    private String message;
    private String field;


    public ErrorMessageDTO(String message, String field) {
    }
}
