package project.accountservice.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.user.User;
import project.accountservice.util.RolesUtil;

import javax.validation.Valid;
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

    @PutMapping("/api/admin/user/role")
    public User changeUserRoles(@RequestBody @Valid RoleRequest roleRequest) {
        if (!RolesUtil.authorityExist(roleRequest.getRoleString())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        }
        return adminService.changeUserRole(roleRequest);
    }
}
