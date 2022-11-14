package project.accountservice.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.accountservice.user.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/api/admin/user")
    public List<User> getUsers() {
       return adminService.getUsers();
    }

    @DeleteMapping("/api/admin/user/{email}")
    public Map<String, String> deleteUser(@PathVariable String email) {
        adminService.deleteUser(email);
        return getUserDeleteSuccessfullyResponse(email);
    }

    private Map<String, String> getUserDeleteSuccessfullyResponse(String email) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("user", email);
        response.put("status", "Deleted successfully!");
        return response;
    }

}
