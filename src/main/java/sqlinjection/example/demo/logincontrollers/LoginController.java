package sqlinjection.example.demo.logincontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sqlinjection.example.demo.entities.Login;
import sqlinjection.example.demo.services.LoginService;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	LoginService loginService;

	@RequestMapping
	public String loginPanel(Model model) {

		return "loginpage.html";
	}

	@GetMapping("signup")
	public String signUpPanel(Login login) {

		return "signup";
	}

	@PostMapping("securedsignin")
	public String securedSignIn(Login login) {

		if (loginService.securedLogIn(login)) {
			return "access";
		} else {
			return "accessdenied";
		}

	}

	@PostMapping("unsecuredsignin")
	public String unsecuredSignIn(Login login) {

		if (loginService.unsecuredLogIn(login)) {
			return "access";
		} else {
			return "accessdenied";
		}

	}

	@PostMapping("signup")
	public String signUp(Login login) {

		loginService.createMember(login);

		return "redirect:/";
	}

}
