package project.accountservice.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import project.accountservice.logger.EventLogService;
import project.accountservice.user.User;
import project.accountservice.util.RolesUtil;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    EventLogService eventLogService;

    @GetMapping("/api/admin/user")
    public List<User> getUsers() {
       return adminService.getUsers();
    }

    @DeleteMapping("/api/admin/user/{email}")
    public Map<String, String> deleteUser(@AuthenticationPrincipal UserDetails user, @PathVariable String email, ServletWebRequest request) {
        adminService.deleteUser(email);
        eventLogService.logUserDeletionEvent(user.getUsername(), email, request.getRequest().getRequestURI());
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
        if (!RolesUtil.roleStringExist(roleRequest.getRoleString())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        }
        return adminService.changeUserRole(roleRequest);
    }

    @PutMapping("/api/admin/user/access")
    public Map<String, String> changeUserAccess(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody AccessRequest accessRequest,
            ServletWebRequest request) {
        adminService.changeUserAccess(accessRequest);
        return getAccessChangedResponse(accessRequest, user, request);
    }

    private Map<String, String> getAccessChangedResponse(
            AccessRequest accessRequest,
            UserDetails user,
            ServletWebRequest request) {
        Map<String, String> body = new HashMap<>();
        if ("LOCK".equals(accessRequest.getOperation())) {
            body.put("status", "User " + accessRequest.getUser() + " locked!");
            eventLogService.logUserLockingEvent(user.getUsername(), accessRequest.getUser(), request.getRequest().getRequestURI());
        } else {
            body.put("status", "User " + accessRequest.getUser() + " unlocked!");
            eventLogService.logUserUnlockingEvent(user.getUsername(), accessRequest.getUser(), request.getRequest().getRequestURI());
        }
        return body;
    }
}
