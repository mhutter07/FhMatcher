package swenga.model;

import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private ProfilesModel profiles;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private QuestionModel questions;
	
	/*@OneToMany(mappedBy= "answers", fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<ProfilesModel> profiles;*/
	
	/*@OneToMany(mappedBy= "answers", fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<QuestionModel> questions;*/
	
	
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
	
	public QuestionModel getQuestions() {
		return questions;
	}

	public void setQuestions(QuestionModel questions) {
		this.questions = questions;
	}
	
	public ProfilesModel getProfiles() {
		return profiles;
	}

	public void setProfiles(ProfilesModel profiles) {
		this.profiles = profiles;
	}

	public void addProfile(ProfilesModel profile) {
		// TODO Auto-generated method stub
		
	}

	public void addQuestion(QuestionModel questionByID) {
		// TODO Auto-generated method stub
		
	}
	

}
