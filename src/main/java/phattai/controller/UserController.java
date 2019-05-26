package phattai.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	// GET all user
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

	// GET user by user name
	@GetMapping
	public ResponseEntity<UserDTO> getByUsername(@RequestParam String username) {
		System.out.println("Searching by username: " + username);
		User user = userService.findById(username);
		
		if (user == null) {
			System.out.println("Username: " + username + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		UserDTO userDTO = userService.covert(user);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}

	// Check login
	@GetMapping("/login")
	public ResponseEntity<UserDTO> checkLogin(@RequestParam String username, @RequestParam String password) {

		User user = userService.findById(username);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (!user.getPassword().equals(password)) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}

			UserDTO userDTO = getByUsername(username).getBody();

			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		}
	}

	// POST user
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody User user) {
		if (userService.findById(user.getUsername()) != null) {
			return new ResponseEntity<String>("Username already exist!", HttpStatus.CONFLICT);
		}
		userService.save(user);
		return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
	}

	// DELETE user by username
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<String> deleteById(@PathVariable("username") String username) {
		User user = userService.findById(username);
		if (user == null) {
			return new ResponseEntity<String>("Not found user", HttpStatus.NOT_FOUND);
		}
		userService.delete(username);
		return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
	}

	// PUT user to update
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody User user) {
		if (userService.findById(user.getUsername()) == null) {
			return new ResponseEntity<String>("Username already exist!", HttpStatus.CONFLICT);
		}
		userService.save(user);
		return new ResponseEntity<String>("Updated!", HttpStatus.OK);
	}

}
