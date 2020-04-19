package com;

import model.Patient;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Patients")

public class PatientService {
	
	Patient itemObj = new Patient();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return itemObj.readPatient();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(@FormParam("name") String name,
			@FormParam("address") String address,
			@FormParam("phone") String phone,
			@FormParam("email") String email)
		{
			String output = itemObj.insertPatient(name, address, phone, email);
			return output;
	} 

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String userData)
	{
		// Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(userData).getAsJsonObject();
		
		// Read the values from the JSON object 
		String pid = itemObject.get("pid").getAsString(); 
		String name = itemObject.get("name").getAsString();
		String address = itemObject.get("address").getAsString();
		String email = itemObject.get("phone").getAsString();
		String phone =itemObject.get("email").getAsString();
		
		String output = itemObj.updatePatient(pid, name, address, phone, email);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePatient(String userData)
	{  
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(userData, "", Parser.xmlParser());
		
		//Read the value from the element <itemID>
		String pid = doc.select("pid").text();  
		
		 String output = itemObj.deletePatient(pid);  
		 
		 return output; 
		 } 

}
