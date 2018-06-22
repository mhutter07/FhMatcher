package swenga.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Entity
@Table(name = "profiles")

public class ProfilesModel implements java.io.Serializable {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 30)
	private String firstname;
	
	@Column(nullable = false, length = 30)
	private String lastname;

	@Column(nullable = false) 
	private boolean gender;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date dayOfBirth;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private MatchesModel matches;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private AnswersModel answers;
	
	@OneToOne(cascade = CascadeType.ALL)
	private DocumentModel document;

	
	@Column(name = "userName", nullable = false, length = 30)
	private String userName;
 
	@Column(name = "password", nullable = false, length = 60)
	private String password;
 
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<UserRoleModel> userRoles;
	
	@Version
	long version;
	
	
	public ProfilesModel() {
		// TODO Auto-generated constructor stub
	}

	public ProfilesModel(String firstname, String lastname, boolean gender, Date dayOfBirth, String userName, String password, boolean enabled) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.dayOfBirth = dayOfBirth;
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Date getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(Date dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public MatchesModel getMatches() {
		return matches;
	}

	public void setMatches(MatchesModel matches) {
		this.matches = matches;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRoleModel> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoleModel> userRoles) {
		this.userRoles = userRoles;
	}

	public void encryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);		
	}
	
	public void addUserRole(UserRoleModel userRole) {
		if (userRoles==null) userRoles = new HashSet<UserRoleModel>();
		userRoles.add(userRole);
	}
	
	public AnswersModel getAnswers() {
		return answers;
	}

	public void setAnswers(AnswersModel answers) {
		this.answers = answers;
	}
	
	public DocumentModel getDocument() {
		return document;
	}
 
	public void setDocument(DocumentModel document) {
		this.document = document;
	}
}