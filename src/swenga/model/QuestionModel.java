package swenga.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "questions")

public class QuestionModel implements java.io.Serializable {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 60)
	private String description;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private AnswersModel answers;
	
	@Version
	long version;
	
	public QuestionModel() {
		// TODO Auto-generated constructor stub
	}

	public QuestionModel(String description) {
		super();
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public AnswersModel getAnswers() {
		return answers;
	}

	public void setAnswers(AnswersModel answers) {
		this.answers = answers;
	}
	
	

}
