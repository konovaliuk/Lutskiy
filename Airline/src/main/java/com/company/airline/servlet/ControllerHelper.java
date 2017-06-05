package com.company.airline.servlet;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.company.airline.commands.*;

public class ControllerHelper {
	
	private static HashMap<String, ICommand> commands = new HashMap<String, ICommand>();
	private static ControllerHelper instance = new ControllerHelper();
	
	private ControllerHelper() {
		commands.put("/login_page", new CommandLoginPage());
		commands.put("/login", new CommandLogin());
		commands.put("/logout", new CommandLogout());
		commands.put("/registration_page", new CommandRegistrationPage());
		commands.put("/registration", new CommandRegistration());
		commands.put("/flights", new CommandFlightsPage());
		commands.put("/admin/new_flight", new CommandNewFlight());
		commands.put("/admin/edit_flight", new CommandEditFlight());
		commands.put("/admin/create_flight", new CommandCreateFlight());
		commands.put("/admin/delete_flight", new CommandDeleteFlight());
		commands.put("/admin/save_flight", new CommandSaveFlight());
		commands.put("/flights_interval", new CommandFlightsInterval());
		commands.put("/dispatcher/edit_crew", new CommandEditCrew());
		commands.put("/dispatcher/view_crew", new CommandViewCrew());
		commands.put("/dispatcher/save_crew", new CommandSaveCrew());
		commands.put("/admin/users", new CommandUsers());
		commands.put("/admin/change_role", new CommandChangeRole());
		commands.put("/admin/update_role", new CommandUpdateRole());
		commands.put("/user_info", new CommandUserInfo());
		commands.put("/change_lang", new CommandChangeLanguage());
	}
	
	public static ControllerHelper getInstance() {
		return instance;
	}
	
	public ICommand getCommand(HttpServletRequest request) {
		ICommand command = commands.get(request.getServletPath());			// getting command by servlet path
		if(command == null){
			command = new CommandMissing();
		}
		return command;
	}
}
