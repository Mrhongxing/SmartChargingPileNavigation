package cyou.tianshu.charging.controller;


import cyou.tianshu.charging.dto.FavoriteRequest;
import cyou.tianshu.charging.entity.FavoriteList;
import cyou.tianshu.charging.service.FavoriteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiForChargingStation/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @GetMapping("/getFavorites")
    public ResponseEntity<List<FavoriteList>> getFavorites(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(favoriteService.getFavorites(userId));
        } catch (Exception e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : "An error occurred while fetching favorites.";
            System.err.println("Error fetching favorites: " + errorMessage);
            return ResponseEntity.status(500).body(null);
        }
    }
}
