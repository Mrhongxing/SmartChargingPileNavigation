package cyou.tianshu.charging.dto;

import lombok.Data;

@Data
public class UpdateRequest {
    private Long id;
    private String name;
    private String email;
    private String phone;
}