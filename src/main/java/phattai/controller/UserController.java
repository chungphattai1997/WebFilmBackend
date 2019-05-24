package phattai.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import phattai.dto.UserDTO;
import phattai.model.User;
import phattai.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	// GET all category
	@GetMapping("/getall")
	public ResponseEntity<List<UserDTO>> getAll() {
		List<User> listUser = (List<User>) userService.findAll();
		List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
		
		listUser.forEach(user -> {
			UserDTO userDTO = userService.covert(user);
			listUserDTO.add(userDTO);
		});
		
		return new ResponseEntity<List<UserDTO>>(listUserDTO, HttpStatus.OK);
	}
	
	@GetMapping("/login")
	public String getFoos(@RequestParam(name = "user") String username, @RequestParam String id) {
	    return "Username: " + username + " - ID: " + id;
	}

}
