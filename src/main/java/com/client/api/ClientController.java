package com.client.api;



import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.client.entity.Client;
import com.client.repo.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.client.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@Tag(name="Client API")
public class ClientController {
	
    @Autowired
    ClientRepository clientRepository;

    @PostMapping("/add_client")
    @Operation(summary="Add Client")
    public ResponseEntity<?> addUser(@RequestBody Client user) throws SQLException{
    	try {
    		clientRepository.saveUser(user);
    		return new ResponseEntity<>("Client added",HttpStatus.CREATED);
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		throw new Conflict(e.getMessage());
    	}

    }

    @PutMapping("/client_edit")
    @Operation(summary="Edit Client details")
    public ResponseEntity<?> updateUser(@RequestBody Client user)throws SQLException{
    	try {
    		clientRepository.updateUser(user);
    	return new ResponseEntity<>("Client details updated",HttpStatus.ACCEPTED);
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    		throw new InternalServerException(e.getMessage());
    	}
    }

    @GetMapping("/client/{id}")
    @Operation(summary="get Client by Id")
    public ResponseEntity<?> getUser(@PathVariable int id){
    	try {
    		Client user=clientRepository.getById(id);
    		JSONObject r=new JSONObject();
    		r.put("Status", HttpStatus.OK);
    		r.put("Status code", 200);
    		r.put("message", "Success");
    		r.put("Data", user.toString());
    		return new ResponseEntity<>(r.toString(),HttpStatus.OK);
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    		throw new UserNotFoundException(e.getMessage());
	}
    }

    @GetMapping("/all_clients")
    @Operation(summary="get all Clients")
    public ResponseEntity<?> getUsers() throws JSONException{
    	try {
    		JSONObject r=new JSONObject();
    		List<Client> user = clientRepository.allUsers();
    		r.put("Status", HttpStatus.OK);
    		r.put("Status code", 200);
    		r.put("message", "Success");
    		r.put("Data", user);
    		return new ResponseEntity<>(r.toString(),HttpStatus.OK);
    	}
        catch(Exception e) {
        	System.out.println(e.getMessage());
        	throw new InternalServerException(e.getMessage());
        }
        
    }

    @DeleteMapping("/client_remove/{id}")
    @Operation(summary="Delete Client by Id")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
    	try {
    		String s=clientRepository.deleteById(id);
    		return new ResponseEntity<>(s,HttpStatus.OK);
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    		throw new UserNotFoundException(e.getMessage());
	}
    }
    
}






//@RequestMapping("/*")
//public ResponseEntity<?> badrequest(){
//	throw new handleExceptionCustom("wrong url");
//}