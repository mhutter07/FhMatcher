package swenga.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "answers")

public class AnswersModel implements java.io.Serializable {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 60)
	private String answerText;
	
	@OneToMany(mappedBy= "answers", fetch=FetchType.EAGER)
	private Set<ProfilesModel> profiles;
	
	@OneToMany(mappedBy= "answers", fetch=FetchType.EAGER)
	private Set<QuestionModel> questions;
	
	
	@Version
	long version;
	
	public AnswersModel() {
		// TODO Auto-generated constructor stub
	}

	public AnswersModel(String answerText) {
		super();
		this.answerText = answerText;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	
	public Set<ProfilesModel> getProfiles() {
		return profiles;
	}
 
	public void setProfiles(Set<ProfilesModel> profiles) {
		this.profiles = profiles;
	}
	
	public void addProfile(ProfilesModel profile) {
		if (profiles==null) {
			profiles= new HashSet<ProfilesModel>();
		}
		profiles.add(profile);
	}
	
	public Set<QuestionModel> getQuestions() {
		return questions;
	}
 
	public void setQuestions(Set<QuestionModel> questions) {
		this.questions = questions;
	}


}
