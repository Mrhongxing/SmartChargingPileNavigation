package cyou.tianshu.charging.dto;

import lombok.Data;

@Data
public class FavoriteRequest {
    private Long userId;
    private double latitude;
    private double longitude;
    private String address;
}
