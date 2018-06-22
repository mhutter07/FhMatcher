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
	
	@Column(nullable = false)
	private boolean answer;
	
	@OneToMany(mappedBy= "answers", fetch=FetchType.EAGER)
	private Set<ProfilesModel> profiles;
	
	@OneToMany(mappedBy= "answers", fetch=FetchType.EAGER)
	private Set<QuestionModel> questions;
	
	
	@Version
	long version;
	
	public AnswersModel() {
		// TODO Auto-generated constructor stub
	}

	public AnswersModel(boolean answer) {
		super();
		this.answer = answer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAnswer() {
		return answer;
	}
	
	public void setAnswer(boolean answer) {
		this.answer = answer;
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
	
	public void addQuestion(QuestionModel question) {
		if (questions==null) {
			questions= new HashSet<QuestionModel>();
		}
		questions.add(question);
	}

}
