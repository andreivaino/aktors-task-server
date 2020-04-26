package ee.aktors.andrei.task.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {

    private int status;
    private String message;
    private long timeStamp;

}
